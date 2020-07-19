package com.natsuki_kining.ssr.core.data.cache;

import com.alibaba.fastjson.JSON;
import com.natsuki_kining.ssr.core.beans.SSRDynamicSQL;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * redis缓存
 *
 * @Author : natsuki_kining
 * @Date : 2020/4/27 20:59
 */
@Slf4j
@Primary
@Component
@ConditionalOnProperty(prefix = "ssr", name = "cache.type", havingValue = "redis")
public class RedisCache implements SSRCache {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Value("${ssr.cache.redis.key:ssr-cache}")
    private String cacheKey;

    private HashOperations<String, String, String> operations;

    @PostConstruct
    private void init() {
        operations = redisTemplate.opsForHash();
    }

    @Override
    public <T> T get(String code, Class<T> clazz) {
        return JSON.parseObject(operations.get(cacheKey, code), clazz);
    }

    @Override
    public boolean save(String code, Object object) {
        try {
            operations.put(cacheKey, code, JSON.toJSONString(object));
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean delete(String code) {
        try {
            operations.delete(cacheKey, code);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean clean() {
        Set<String> keys = operations.keys(cacheKey);
        keys.stream().forEach(this::delete);
        return true;
    }

    @Override
    public SSRDynamicSQL getSSRDynamicSQL(String queryCode) {
        return get(queryCode, SSRDynamicSQL.class);
    }
}
