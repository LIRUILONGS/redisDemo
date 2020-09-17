package com.liruilong.redis.demo.mq;

/**
 * @author Liruilong
 * @Date 2020/9/16 14:07
 * @Description:
 */
public class MqDO {
    private String id;
    private Object data;

    public String getId() {
        return id;
    }

    public MqDO setId(String id) {
        this.id = id;
        return this;
    }

    public Object getData() {
        return data;
    }

    public MqDO setData(Object data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "MqDO{" +
                "id='" + id + '\'' +
                ", data=" + data +
                '}';
    }
}
