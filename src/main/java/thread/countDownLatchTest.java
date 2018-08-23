package thread;

import java.sql.Time;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class countDownLatchTest {
    private static ExecutorService executorService = Executors.newFixedThreadPool(10);
    private static CountDownLatch countDownLatch=new CountDownLatch(5);
    public static void main(String args[]){
        Long startTime =System.currentTimeMillis();
        System.out.println("start time："+startTime);
        for (int i=0;i<5;i++){
            System.out.println("start submit");
            executorService.submit(new Employee(countDownLatch,i));
        }
        try {
            countDownLatch.await();
            System.out.println("all come");
            Long stopTime =System.currentTimeMillis();
            System.out.println("stop time："+stopTime);
            System.out.println("time spend："+(stopTime-startTime));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            executorService.shutdown();
        }

    }

    static class Employee implements Runnable{

        private CountDownLatch countDownLatch;
        private int number;

        public Employee(CountDownLatch countDownLatch,int number){
            this.countDownLatch=countDownLatch;
            this.number=number;
        }

        public void run() {
            try {
                System.out.println(number+"come");
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finally {
                countDownLatch.countDown();
            }
        }
    }
}
