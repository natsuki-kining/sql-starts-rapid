package com.natsuki_kining.ssr.core.data.cache;

import com.alibaba.fastjson.JSON;
import com.natsuki_kining.ssr.core.beans.SSRDynamicSQL;
import com.natsuki_kining.ssr.core.config.properties.SSRProperties;
import com.natsuki_kining.ssr.core.utils.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
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
public class RedisCache extends AbstractCache implements SSRCache {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private SSRProperties ssrProperties;

    private HashOperations<String, String, String> operations;

    @Override
    protected void initCache() {
        operations = redisTemplate.opsForHash();
    }

    @Override
    public <T> T get(String code, Class<T> clazz) {
        return JSON.parseObject(operations.get(ssrProperties.getCache().getRedisKey(), code), clazz);
    }

    @Override
    public boolean save(String code, Object object) {
        try {
            operations.put(ssrProperties.getCache().getRedisKey(), code, JSON.toJSONString(object));
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean delete(String code) {
        try {
            operations.delete(ssrProperties.getCache().getRedisKey(), code);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean clean() {
        Set<String> keys = operations.keys(ssrProperties.getCache().getRedisKey());
        keys.stream().forEach(this::delete);
        return true;
    }

    @Override
    public SSRDynamicSQL getSSRDynamicSQL(String queryCode) {
        return get(queryCode, SSRDynamicSQL.class);
    }
}
