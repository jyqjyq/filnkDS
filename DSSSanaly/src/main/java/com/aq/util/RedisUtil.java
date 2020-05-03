package com.aq.util;

import redis.clients.jedis.Jedis;

/**
 */
public class RedisUtil {
    public static final Jedis jedis = new Jedis("111.231.99.181",6379);

    public  static String getBykey (String key){
       return jedis.get(key);
    }

    public static void main(String[] args) {
        jedis.set("test3","test33");
        String value = jedis.get("test3");
        System.out.println(value);
    }
}
