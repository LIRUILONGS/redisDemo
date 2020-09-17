package com.liruilong.redis.demo.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author Liruilong
 * @Date 2020/9/16 14:10
 * @Description:
 */
public class Mq {
    private Jedis jedis;
    private  String queuy;

    public Mq(Jedis jedis, String queuy) {
        this.jedis = jedis;
        this.queuy = queuy;
    }

    /**
     * <per>
     * <p>消息入队</p>
     * <per/>
     *
     * @param data
     * @return void
     * @throws
     * @Description : TODO
     * @author Liruilong
     * @Date 2020/9/16 14:17
     **/
    public void queue(Object data){
        MqDO mqDO = new MqDO()
                .setId(UUID.randomUUID().toString())
                .setData(data);
        try {
            String s = new ObjectMapper().writeValueAsString(mqDO);
            System.out.println("msg publish"+ new Date()+s);
            jedis.zadd(queuy,System.currentTimeMillis() + 5000,s);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * <per>
     * <p>消息出队</p>
     * <per/>
     *
     * @param
     * @return void
     * @throws
     * @Description : TODO
     * @author Liruilong
     * @Date 2020/9/16 14:32
     **/
    public void loop(){
        while (!Thread.interrupted()){
            Set<String> zrange = jedis.zrangeByScore(queuy,0,System.currentTimeMillis(),0,1);
            if (zrange.isEmpty()){
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                }catch (InterruptedException e){
                    break;
                }
                continue;
            }
            String next = zrange.iterator().next();
            if (jedis.zrem(queuy,next)>0){
                try {
                    MqDO mqDO = new ObjectMapper().readValue(next,MqDO.class);
                    System.out.println(mqDO);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
