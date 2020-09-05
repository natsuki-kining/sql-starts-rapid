package com.natsuki_kining.ssr.core.config.properties;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 多数据源的配置
 *
 * @author xiongneng
 * @since 2017/6/23 23:05
 */
@Component
@ConfigurationProperties(prefix = "ssr.datasource")
public class MultiDataSourceProperties {

    private Map<String,DruidProperties> multiSource;
}
