package com.dcits.scott.other.redis;

import java.util.List;
import java.util.Set;

public interface RedisService {
    void setAdd(String key,Object value);


    Set<Object> members(String key);


    long setRemove(String key,Object value);

    void mapPut(String key,Object hashKey,Object value);

    Object mapGet(String key,Object hashKey);

    List<Object> mapGetValues(String key);

    void removeMap(String key,String hashKey);

    Set<Object> mapGetKeys(String key);
    /**
     * set存数据
     * @param key
     * @param value
     * @return
     */
    boolean set(String key, String value);

    /**
     * get获取数据
     * @param key
     * @return
     */
    String get(String key);

    /**
     * 设置有效天数
     * @param key
     * @param expire
     * @return
     */
    boolean expire(String key, long expire);

    /**
     * 移除数据
     * @param key
     * @return
     */
    boolean remove(String key);

}
