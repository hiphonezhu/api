package com.java.api.security.impl;

import com.java.api.security.TokenManager;
import com.java.api.util.CodecUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 基于 Redis 的令牌管理器
 *
 * @author huangyong
 * @since 1.0.0
 */
public class RedisTokenManager implements TokenManager {

    private static final int DEFAULT_DATABASE = 0;
    private static final int DEFAULT_SECONDS = 0;

    private JedisPool jedisPool;
    private int database = DEFAULT_DATABASE;
    private int seconds = DEFAULT_SECONDS;

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    @Override
    public String createToken(String username) {
        String token = CodecUtil.createUUID();
        Jedis jedis = null;
        try {
        	jedis = jedisPool.getResource();
        	jedis.select(database);
            if (seconds != 0) {
                jedis.setex(token, seconds, username);
            } else {
                jedis.set(token, username);
            }
        } finally {
        	if (jedis != null)
        	{
        		jedis.close();
        	}
        }
        return token;
    }

    @Override
    public boolean checkToken(String token) {
        boolean result;
        Jedis jedis = null;
        try {
        	jedis= jedisPool.getResource();
        	jedis.select(database);
            result = jedis.exists(token);
            if (seconds != 0) {
                jedis.expire(token, seconds);
            }
        } finally {
        	if (jedis != null)
        	{
        		jedis.close();
        	}
        }
        return result;
    }
}
