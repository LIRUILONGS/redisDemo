package com.liruilong.redis.demo;


import redis.clients.jedis.params.SetParams;

import java.util.Objects;

/**
 * @author Liruilong
 * @Date 2020/9/5 14:57
 * @Description:
 */
public class Myredis {

    public static void main(String[] args) {

        Redis redis = Redis.builder();
        redis.execute(red -> {
           String set= red.set("Key","V1",new SetParams().nx().ex(5));
            // 没有人占位
            if (Objects.nonNull(set) && "OK".equals(set)) {
                red.set("name", "liruilong");
                System.out.println(red.get("name"));
                red.del("Key");
                System.out.println("没人占位");
            } else {
                // 有人占位， 停止、暂缓 操作
                System.out.println("有人占位， 停止、暂缓 操作");
            }
        });
    }
}
