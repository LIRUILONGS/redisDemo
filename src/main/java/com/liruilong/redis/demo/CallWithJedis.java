package com.liruilong.redis.demo;

import redis.clients.jedis.Jedis;

/**
 * @author Liruilong
 * @Date 2020/9/5 17:56
 * @Description: 函数接口,
 * 传递一个Redis对象，类似于 Runnable -> Thread
 * 类似策略模式，CallWithJedis可以看着为策略模板，
 */
@FunctionalInterface
public interface CallWithJedis {
    void call(Jedis jedis);

}
