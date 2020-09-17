package com.liruilong.redis.demo;

import redis.clients.jedis.params.SetParams;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Liruilong
 * @Date 2020/9/16 13:54
 * @Description:
 */
public class LuaTest {
    public static void main(String[] args) {
        Redis redis = Redis.builder();
        for (int i = 0; i < 2; i++) {
            redis.execute(o -> {
                String string = UUID.randomUUID().toString();
                String key = o.set("Key", string, new SetParams().nx().ex(5));
                if (Objects.nonNull(key) && "OK".equals(key)) {
                    o.set("name", "liruilong");
                    System.out.println(o.get("name"));
                    o.evalsha("", Arrays.asList("Key"), Arrays.asList(string));
                } else {
                    // 有人占位， 停止、暂缓 操作
                    System.out.println("有人占位， 停止、暂缓 操作");
                }
            });
        }
    }
    /*Lua脚本
    if redis.call("get",KEYS[1]==ARGV[1]) then
        retuen redis.call("del",KEYS[1])
    else
        return 0
    end
    cat lua/releasewherevalueequal.lua | redis-cli -a javaboy script load --pipe
     */
}
