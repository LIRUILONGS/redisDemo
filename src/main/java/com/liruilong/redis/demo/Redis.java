package com.liruilong.redis.demo;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Objects;

/**
 * @author Liruilong
 * @Date 2020/9/5 17:49
 * @Description: 线程安全的Redis池(单例)
 */
public final class Redis {
    private  volatile JedisPool pool;
    private static   volatile Redis redis = null;
    private  Redis() {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxIdle(300);
        config.setMaxTotal(1000);
        config.setMaxWaitMillis(30000);
        config.setTestOnBorrow(true);
        this.pool = new JedisPool(config,"10.0.16.83",6379,30000);
    }
    public void execute(CallWithJedis callWithJedis){
        try (Jedis jedis = pool.getResource()){
            callWithJedis.call(jedis);
        }
    }

    public static Redis builder() {
        if (Objects.isNull(redis)) {
            synchronized (Redis.class) {
                if (Objects.isNull(redis)) {
                    return new Redis();
                }
            }
        }
            return  redis;
    }

}

