package lock.jvmlock;

import lock.task.OrderTask;
import lock.util.OrderService;
import lock.util.OrderServiceImpl;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JVMLock {
    public static void main (String args[]){
        ExecutorService executorService = Executors.newCachedThreadPool();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        OrderService orderService = new OrderServiceImpl();//no repeat
        for (int i=0;i<10;i++){
           // OrderService orderService = new OrderServiceImpl();
            executorService.submit(new OrderTask(countDownLatch,orderService));
        }
        countDownLatch.countDown();
        executorService.shutdown();
    }
}
