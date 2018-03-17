package lock.task;

import lock.util.OrderService;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import java.util.concurrent.CountDownLatch;

public class OrderTask2 implements Runnable{
    private CountDownLatch countDownLatch;
    private OrderService orderService;
    InterProcessMutex lock;
    public OrderTask2(CountDownLatch countDownLatch, OrderService orderService,InterProcessMutex lock) {
        this.countDownLatch=countDownLatch;
        this.orderService=orderService;
        this.lock=lock;
    }

    @Override
    public void run() {
        try {
                countDownLatch.await();
                lock.acquire();
            }catch (Exception e){
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+":"+ orderService.getOrderNo());
        }


}
