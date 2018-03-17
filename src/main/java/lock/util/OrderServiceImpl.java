package lock.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderServiceImpl extends  AbstractOrderService implements OrderService{
    static int num =0;
    @Override
    public synchronized  String getOrderNo() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMMDDHHMMSS");
        return simpleDateFormat.format(new Date())+num++;

    }
}
