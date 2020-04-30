package com.natsuki_kining.ssr.data.cache;

import com.natsuki_kining.ssr.beans.SSRDynamicSql;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * redis缓存
 *
 * @Author : natsuki_kining
 * @Date : 2020/4/27 20:59
 */
@Component
@Slf4j
public class RedisCache extends SSRCache {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public SSRDynamicSql get(String code) {
        return StringUtils.isBlank(code) ? null : (SSRDynamicSql) redisTemplate.opsForValue().get(code);
    }

    @Override
    public boolean save(SSRDynamicSql dynamicSql) {
        if (dynamicSql == null){
            return false;
        }
        try{
            redisTemplate.opsForValue().set(dynamicSql.getQueryCode(),dynamicSql);
            return true;
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return false;
        }
    }
}
