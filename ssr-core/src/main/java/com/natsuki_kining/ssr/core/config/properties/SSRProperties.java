package com.natsuki_kining.ssr.core.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @Author : natsuki_kining
 * @Date : 2020/8/28 22:25
 */
@Data
@Component
@ConfigurationProperties(prefix = "ssr",ignoreInvalidFields = true)
public class SSRProperties {

    /**
     * 是否开启根据实体生成SQL
     */
    private boolean generateByEntityEnable = false;

    /**
     * 是否开启根据表名生成SQL
     */
    private boolean generateByTableEnable = false;

    /**
     * 缓存最大数量
     */
    private int cacheMaxLength = 500;

    /**
     * 缓存达到最大数时，一次移除不常用的数量
     */
    private int cacheMaxRarely = 500;

    /**
     * redis 缓存key
     */
    private String cacheRedisKey = "ssr-cache";

    /**
     * 动态sql表表名
     */
    protected String dynamicSqlTableName = "SSR_DYNAMIC_SQL";

    /**
     * 是否开启打印查询信息
     */
    private boolean showQueryInfoEnable = true;

}
