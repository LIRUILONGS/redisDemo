package com.liruilong.redis;

import com.liruilong.redis.demo.Redis;
import com.liruilong.redis.demo.mq.Mq;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.concurrent.TimeUnit;

@SpringBootTest
class RedisApplicationTests {

    @Test
    void contextLoads() {
        Jedis jedis = new Jedis("10.0.16.83");
        //jedis.auth(""); // 密码认证
        System.out.println("连接是否成功："+jedis.ping());
    }
    @Test
    void jedisPool() {
        JedisPool jedisPool = new JedisPool("10.0.16.83", 6379);
        Jedis jedis = jedisPool.getResource();
        System.out.println("连接是否成功："+jedis.ping());
        jedis.close();
    }
    @Test
    void MqTest(){
        Redis redis = Redis.builder();
        redis.execute( o ->{
            Mq mq = new Mq(o,"liruilong");
            Thread producer = new Thread(() -> {
                for (int i = 0; i < 5; i++) {
                    mq.queue("加油呀**"+i);
                }
            });
            Thread consumer = new Thread(() -> {
                mq.loop();
            });
            producer.start();
            consumer.start();
            try {
                TimeUnit.MILLISECONDS.sleep(7);
                consumer.interrupt();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
