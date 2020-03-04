package com.dcits.scott.interfaceprovider.gatewayapi.serviceImpl;

import com.dcits.scott.other.redis.RedisService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;


import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
@Service
public class RedisServiceImpl implements RedisService {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void setAdd(String key, Object value) {
       redisTemplate.opsForSet().add(key,value);
    }

    @Override
    public Set<Object> members(String key) {
        Set<Object> sets =  redisTemplate.opsForSet().members(key);
        return sets;
    }

    @Override
    public long setRemove(String key, Object value) {
        long count = redisTemplate.opsForSet().remove(key,value);
        return count;
    }

    @Override
    public void mapPut(String key, Object hashKey, Object value) {
        redisTemplate.opsForHash().put(key,hashKey,value);
        redisTemplate.persist(key);
    }

    @Override
    public Object mapGet(String key, Object hashKey) {

            return redisTemplate.opsForHash().get(key,hashKey);
    }

    @Override
    public List<Object> mapGetValues(String key) {
        List<Object> lists = redisTemplate.opsForHash().values(key);
        return lists;
    }

    @Override
    public void removeMap(String key, String hashKey) {
        redisTemplate.opsForHash().delete(key,hashKey);
    }

    @Override
    public Set<Object> mapGetKeys(String key) {
        return redisTemplate.opsForHash().keys(key);
    }

    @Override
    public boolean set(final String key, final String value) {
        boolean result = redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            connection.set(serializer.serialize(key), serializer.serialize(value));
            return true;
        });
        return result;
    }

    @Override
    public String get(final String key) {
        String result = redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] value = connection.get(serializer.serialize(key));
                return serializer.deserialize(value);
            }
        });
        return result;
    }

    @Override
    public boolean expire(final String key, long expire) {
        return redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    @Override
    public boolean remove(final String key) {
        boolean result = redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            connection.del(key.getBytes());
            return true;
        });
        return result;
    }



    //

}
