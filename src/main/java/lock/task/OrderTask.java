package lock.task;

import lock.util.OrderService;

import java.util.concurrent.CountDownLatch;

public class OrderTask implements Runnable{
    private CountDownLatch countDownLatch;
    private OrderService orderService;
    public OrderTask(CountDownLatch countDownLatch,OrderService orderService) {
        this.countDownLatch=countDownLatch;
        this.orderService=orderService;
    }

    @Override
    public void run() {
        try {
                countDownLatch.await();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+":"+ orderService.getOrderNo());
        }


}
