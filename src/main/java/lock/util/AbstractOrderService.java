package lock.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract  class AbstractOrderService implements OrderService{
    static int num =0;
    public static  synchronized String getOrderNo2(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMMDDHHMMSS");
        return simpleDateFormat.format(new Date())+num++;
    }
    @Override
    public String getOrderNo() {
        return getOrderNo2();
    }
}
