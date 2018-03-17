package lock.automic;

import lock.task.OrderTask;
import lock.util.OrderRedisServiceImpl;
import lock.util.OrderService;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RedisOrder {
    public static void main (String args[]){
        ExecutorService executorService = Executors.newCachedThreadPool();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        OrderService orderService = new OrderRedisServiceImpl();
        for (int i=0;i<10;i++){
            executorService.submit(new OrderTask(countDownLatch,orderService));
        }
        countDownLatch.countDown();
        executorService.shutdown();
    }
}
