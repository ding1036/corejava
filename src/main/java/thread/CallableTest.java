package thread;

import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.*;

public class CallableTest {
    private static ExecutorService executorService = Executors.newFixedThreadPool(5);
    public static void main(String args[]){
        for(int i=0;i<5;i++){
            FutureTask<Integer> futureTask = new FutureTask(new SendCallable(i));
            executorService.submit(futureTask);
            try {
                int number = futureTask.get();
                System.out.println(i+"'s number is :"+number);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        executorService.shutdown();
    }

    static class SendCallable implements Callable {
        private int position;
        public SendCallable(int position){
            this.position=position;
        }
        @Override
        public Object call() throws Exception {

            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");

            return secureRandom.nextInt(20);
        }
    }
}
