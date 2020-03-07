package com.microlittleblog.common.utils.redis;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.microlittleblog.common.utils.json.JSONUtils;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 *
 * @author ...
 */
@Component
public class RedisUtils {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ValueOperations<String, String> valueOperations;
    @Autowired
    private HashOperations<String, String, Object> hashOperations;
    @Autowired
    private ListOperations<String, Object> listOperations;
    @Autowired
    private SetOperations<String, Object> setOperations;
    @Autowired
    private ZSetOperations<String, Object> zSetOperations;

    /**
     * 默认过期时长，单位：秒
     */
    private final static long DEFAULT_EXPIRE = 60 * 60 * 24;

    /**
     * 不设置过期时长
     */
    private final static long NOT_EXPIRE = -1;

    public void set(String key, Object value, long expire) {
        valueOperations.set(key, toJson(value));
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

    public void set(String key, Object value) {
        set(key, value, DEFAULT_EXPIRE);
    }

    public Long incr(String key) {
        return redisTemplate.boundValueOps(key).increment(1);
    }

    public Set<Object> getSet(String key) {
        return setOperations.members(key);
    }

    /**
     * 加锁
     *
     * @param locaName       锁的key
     * @param acquireTimeout 获取超时时间
     * @param timeout        锁的超时时间
     * @return 锁标识
     */
    public String lockWithTimeout(String locaName,
                                  long acquireTimeout, long timeout) {
        String retIdentifier = null;
        RedisConnection conn = null;
        try {
            conn = redisTemplate.getConnectionFactory().getConnection();
            // 随机生成一个value
            String identifier = UUID.randomUUID().toString();
            // 锁名，即key值
            String lockKey = "lock:" + locaName;
            // 超时时间，上锁后超过此时间则自动释放锁
            int lockExpire = (int) (timeout / 1000);
            // 获取锁的超时时间，超过这个时间则放弃获取锁
            long end = System.currentTimeMillis() + acquireTimeout;
            while (System.currentTimeMillis() < end) {
                if (conn.setNX(lockKey.getBytes(), identifier.getBytes())) {
                    conn.expire(lockKey.getBytes(), lockExpire);
                    // 返回value值，用于释放锁时间确认
                    retIdentifier = identifier;
                    return retIdentifier;
                }
                // 返回-1代表key没有设置超时时间，为key设置一个超时时间
                if (conn.ttl(lockKey.getBytes()) == -1) {
                    conn.expire(lockKey.getBytes(), lockExpire);
                }

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return retIdentifier;
    }

    /**
     * 释放锁
     *
     * @param lockName   锁的key
     * @param identifier 释放锁的标识
     * @return boolean
     */
    public boolean releaseLock(String lockName, String identifier) {
        RedisConnection conn = null;
        String lockKey = "lock:" + lockName;
        boolean retFlag = false;
        conn = Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection();
        while (true) {
            // 监视lock，准备开始事务
            conn.watch(lockKey.getBytes());
            // 通过前面返回的value值判断是不是该锁，若是该锁，则删除，释放锁
            if (identifier.equals(get(lockKey))) {
                conn.del(lockKey.getBytes());
                retFlag = true;
            }
            conn.unwatch();
            break;
        }

        return retFlag;
    }


    public <T> T get(String key, Class<T> clazz, long expire) {
        String value = valueOperations.get(key);
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value == null ? null : fromJson(value, clazz);
    }

    public <T> T get(String key, Class<T> clazz) {
        return get(key, clazz, NOT_EXPIRE);
    }

    public String get(String key, long expire) {
        String value = valueOperations.get(key);
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value;
    }

    public String get(String key) {
        return get(key, NOT_EXPIRE);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * Object转成JSON数据
     */
    private String toJson(Object object) {
        if (object instanceof Integer || object instanceof Long || object instanceof Float ||
                object instanceof Double || object instanceof Boolean || object instanceof String) {
            return String.valueOf(object);
        }
        return JSONUtils.writeValueAsString(object);
    }

    /**
     * JSON数据，转成Object
     */
    private <T> T fromJson(String json, Class<T> clazz) {
        return JSONUtils.readValue(json, clazz);
    }

}