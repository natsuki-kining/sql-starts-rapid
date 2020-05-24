package com.natsuki_kining.ssr.data.cache;

import com.natsuki_kining.ssr.beans.SSRDynamicSql;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
    public SSRDynamicSql get(String code) {
        return StringUtils.isBlank(code) ? null : (SSRDynamicSql) redisTemplate.opsForValue().get(code);
    }

}
