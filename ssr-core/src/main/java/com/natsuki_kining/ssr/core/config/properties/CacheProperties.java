package com.natsuki_kining.ssr.core.config.properties;

import lombok.Data;

/**
 * 缓存参数配置类
 *
 * @Author : natsuki_kining
 * @Date : 2020/8/28 23:21
 */
@Data
public class CacheProperties {

    /**
     * 缓存最大数量
     */
    private int maxLength = 500;

    /**
     * 缓存达到最大数时，一次移除不常用的数量
     */
    private int maxRarely = 500;

    /**
     * redis 缓存key
     */
    private String redisKey = "ssr-cache";
}
