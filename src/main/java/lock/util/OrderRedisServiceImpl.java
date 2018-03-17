package lock.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderRedisServiceImpl extends  AbstractOrderService implements OrderService{
    static JedisPool jedisPool;

    static{
        jedisPool = new JedisPool(new JedisPoolConfig(),"localhost",6379,1000);
    }
    @Override
    public synchronized  String getOrderNo() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMMDDHHMMSS");
        return simpleDateFormat.format(new Date())+jedisPool.getResource().incr("order_keys");

    }
}
