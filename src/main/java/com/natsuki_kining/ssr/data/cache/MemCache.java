package com.natsuki_kining.ssr.data.cache;

import com.natsuki_kining.ssr.beans.SSRDynamicSql;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * mem cache
 *
 * @Author : natsuki_kining
 * @Date : 2020/4/27 21:02
 */
@Slf4j
@Primary
@Component
@ConditionalOnProperty(prefix = "ssr", name = "cache.type", havingValue = "memCache")
public class MemCache implements SSRCache {

    @Override
    public SSRDynamicSql get(String code) {
        return null;
    }

}
