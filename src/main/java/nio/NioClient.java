package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class NioClient {

    private ByteBuffer sendBuffer = ByteBuffer.allocate(1024);
    private ByteBuffer recieveBuffer = ByteBuffer.allocate(1024);

    private Selector selector;

    private InetSocketAddress socketAddress  = new InetSocketAddress("localhost",9999);
    private SocketChannel client;
    public NioClient() throws IOException {
        client = SocketChannel.open();
        client.configureBlocking(false);
        client.connect(socketAddress);
        selector = Selector.open();
        client.register(selector, SelectionKey.OP_CONNECT);
    }
    public void clientServer() throws IOException {
        if(client.isConnectionPending()){
            client.finishConnect();
            System.out.println("connect to server, register your information");
            client.register(selector,SelectionKey.OP_WRITE);
        }
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()){
            String msg = scanner.nextLine();
            if("".equals(msg.trim())){
                continue;
            }else if("exit".equals(msg.trim())){
                System.exit(0);
            }
            hander(msg);
        }
    }

    private void hander(String msg) throws IOException {
        boolean isWait =true;
        while (true){
            int eventSize = selector.select();
            if(eventSize ==0){
                continue;
            }else{
                Set<SelectionKey> event = selector.selectedKeys();
                Iterator<SelectionKey> iterator =  event.iterator();
                while(iterator.hasNext()){
                    SelectionKey eventKey = iterator.next();
                    if(eventKey.isWritable()){
                        sendBuffer.clear();
                        sendBuffer.put(msg.getBytes());
                        sendBuffer.flip();

                        client.write(sendBuffer);
                        client.register(selector,SelectionKey.OP_READ);
                    }else if(eventKey.isReadable()){
                        recieveBuffer.clear();
                        int len = client.read(recieveBuffer);
                        if(len>0){
                            System.out.println("callback message is" + new String(recieveBuffer.array(),0,len));
                        }
                        client.register(selector,SelectionKey.OP_WRITE);
                        isWait =false;
                    }
                    iterator.remove();
                }

            }
        }
    }

    public static void main(String args[]){
        try {
            new NioClient().clientServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
