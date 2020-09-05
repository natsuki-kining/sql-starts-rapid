package com.natsuki_kining.ssr.core.config.multisource;

import com.alibaba.druid.pool.DruidDataSource;
import com.natsuki_kining.ssr.core.config.properties.DruidProperties;
import com.natsuki_kining.ssr.core.config.properties.MultiDataSourceProperties;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @Author : natsuki_kining
 * @Date : 2020/9/4 22:25 multisource
 */
//@Component
//@ConfigurationProperties(prefix = "ssr.datasource")
public class MultiSourceConfig {

    private Map<String,DruidProperties> multiSource;

//    @Autowired
    DruidProperties druidProperties;

//    @Autowired
    MultiDataSourceProperties multiDataSourceProperties;

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
//        multiDataSourceProperties.config(dataSource);
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
//    @Bean
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
        dynamicDataSource.setTargetDataSources(hashMap);
        dynamicDataSource.setDefaultTargetDataSource(coreDataSource);
        return dynamicDataSource;
    }



}
