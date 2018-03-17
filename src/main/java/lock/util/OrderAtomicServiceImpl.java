package lock.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderAtomicServiceImpl extends  AbstractOrderService implements OrderService{
    static AtomicInteger num =new AtomicInteger();
    @Override
    public synchronized  String getOrderNo() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMMDDHHMMSS");
        return simpleDateFormat.format(new Date())+num.getAndIncrement();

    }
}
