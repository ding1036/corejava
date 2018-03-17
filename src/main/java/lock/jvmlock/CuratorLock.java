package lock.jvmlock;

import lock.task.OrderTask;
import lock.task.OrderTask2;
import lock.util.OrderAtomicServiceImpl;
import lock.util.OrderNoLockServiceImpl;
import lock.util.OrderService;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//分布式锁
public class CuratorLock {
    public static CuratorFramework client = CuratorFrameworkFactory.builder().connectString("192.168.0.102:2181,192.168.0.102:2182,192.168.0.102:2183").retryPolicy(new ExponentialBackoffRetry(100,1)).build();
    public static void main (String args[]){
        client.start();
        ExecutorService executorService = Executors.newCachedThreadPool();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        InterProcessMutex lock = new InterProcessMutex(client,"/bit");
        for (int i=0;i<10;i++){
            executorService.submit(new OrderTask2(countDownLatch,new OrderNoLockServiceImpl(),lock));
        }
        countDownLatch.countDown();
        executorService.shutdown();
    }
}
