package com.natsuki_kining.ssr.core.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.natsuki_kining.ssr.core.config.multi.DSEnum;
import com.natsuki_kining.ssr.core.config.multi.DynamicDataSource;
import com.natsuki_kining.ssr.core.config.properties.DruidProperties;
import com.natsuki_kining.ssr.core.config.properties.MutiDataSourceProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * TODO
 *
 * @Author : natsuki_kining
 * @Date : 2020/9/4 22:25
 */
@Configuration
public class MultiSourceConfig {

    @Autowired
    DruidProperties druidProperties;

    @Autowired
    MutiDataSourceProperties mutiDataSourceProperties;

    /**
     * 核心数据源
     */
    private DruidDataSource coreDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        druidProperties.config(dataSource);
        return dataSource;
    }

    /**
     * 另一个数据源
     */
    private DruidDataSource bizDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        druidProperties.config(dataSource);
        mutiDataSourceProperties.config(dataSource);
        return dataSource;
    }

    /**
     * 单数据源连接池配置
     */
//    @Bean
//    @ConditionalOnProperty(prefix = "xncoding", name = "muti-datasource-open", havingValue = "false")
//    public DruidDataSource singleDatasource() {
//        return coreDataSource();
//    }

    /**
     * 多数据源连接池配置
     */
    @Bean
//    @ConditionalOnProperty(prefix = "xncoding", name = "muti-datasource-open", havingValue = "true")
    public DynamicDataSource multiDataSource() {

        DruidDataSource coreDataSource = coreDataSource();
        DruidDataSource bizDataSource = bizDataSource();

        try {
            coreDataSource.init();
            bizDataSource.init();
        } catch (SQLException sql) {
            sql.printStackTrace();
        }

        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put(DSEnum.DATA_SOURCE_CORE, coreDataSource);
        hashMap.put(DSEnum.DATA_SOURCE_BIZ, bizDataSource);
        dynamicDataSource.setTargetDataSources(hashMap);
        dynamicDataSource.setDefaultTargetDataSource(coreDataSource);
        return dynamicDataSource;
    }
}
