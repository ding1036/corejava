package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class NioServer {

    private ServerSocketChannel serverSocketChannel;

    private ByteBuffer sendBuffer = ByteBuffer.allocate(1024);
    private ByteBuffer recieveBuffer = ByteBuffer.allocate(1024);

    private Selector selector;

    private int port = 8080;
    private static Map<SelectionKey,String> sessionMsgs = new HashMap<>();

    public NioServer (int port) throws IOException {
        this.port = port;
        serverSocketChannel =ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);
        selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("init successful, listen port is: "+port);
    }

    public void listen() throws IOException {
        while(true){
            int eventSize = selector.select();
            if (eventSize ==0){
                continue;
            }else{
                Set<SelectionKey> event = selector.selectedKeys();
                Iterator<SelectionKey> iterator =  event.iterator();
                while(iterator.hasNext()){
                    SelectionKey eventKey = iterator.next();
                    handleKey(eventKey);
                    iterator.remove();
                }
            }
        }
    }

    private void handleKey(SelectionKey eventKey) throws IOException {
        if(eventKey.isAcceptable()){
            SocketChannel client = serverSocketChannel.accept();
            client.configureBlocking(false);
            client.register(selector,SelectionKey.OP_READ);
        }else if(eventKey.isReadable()){
            SocketChannel client = (SocketChannel)eventKey.channel();
            int len = client.read(recieveBuffer);
            if(len>0){
                recieveBuffer.flip();
                String msg = new String(recieveBuffer.array(),0,len);
                sessionMsgs.put(eventKey,msg);
                client.register(selector,SelectionKey.OP_WRITE);
            }
        }else if(eventKey.isWritable()){
            if(!sessionMsgs.containsKey(eventKey)){
                return;
            }
            SocketChannel clientChannel = (SocketChannel)eventKey.channel();
            sendBuffer.clear();
            sendBuffer.put(new String(sessionMsgs.get(eventKey)+"handle success").getBytes());

            sendBuffer.flip();
            clientChannel.write(sendBuffer);
            clientChannel.register(selector,SelectionKey.OP_READ);
        }
    }

    public static void main(String args[]){
        try {
            new NioServer(9999).listen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
