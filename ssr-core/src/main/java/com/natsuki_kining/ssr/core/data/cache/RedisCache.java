package com.natsuki_kining.ssr.core.data.cache;

import com.natsuki_kining.ssr.core.beans.SSRDynamicSQL;
import com.natsuki_kining.ssr.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

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
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public SSRDynamicSQL getSSRDynamicSQL(String code) {
        return StringUtils.isBlank(code) ? null : (SSRDynamicSQL) redisTemplate.opsForValue().get(code);
    }

    @Override
    public boolean save(SSRDynamicSQL dynamicSQL) {
        try{
            redisTemplate.opsForValue().set(dynamicSQL.getQueryCode(),dynamicSQL);
            return true;
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return false;
        }
    }

    @Override
    public boolean delete(String queryCode) {
        redisTemplate.delete(queryCode);
        return false;
    }
}
