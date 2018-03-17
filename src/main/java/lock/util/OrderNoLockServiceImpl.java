package lock.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderNoLockServiceImpl implements OrderService{
    static int num =0;
    @Override
    public   String getOrderNo() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMMDDHHMMSS");
        return simpleDateFormat.format(new Date())+num++;

    }
}
