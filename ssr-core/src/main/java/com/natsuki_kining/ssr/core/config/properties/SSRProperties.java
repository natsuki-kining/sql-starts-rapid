package com.natsuki_kining.ssr.core.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 配置参数类
 *
 * @Author : natsuki_kining
 * @Date : 2020/8/28 22:25
 */
@Data
@Component
@ConfigurationProperties(prefix = "ssr",ignoreInvalidFields = true)
public class SSRProperties {

    /**
     * 开关配置
     */
    private EnableProperties enable;

    /**
     * 脚本相关配置
     */
    private ScriptProperties script;

    /**
     * 缓存相关配置
     */
    private CacheProperties cache;

    /**
     * 动态sql表表名
     */
    private String dynamicSqlTableName = "SSR_DYNAMIC_SQL";

    /**
     * 多数据源属性
     */
    private Map<String,DruidProperties> multiDataSource;


}
