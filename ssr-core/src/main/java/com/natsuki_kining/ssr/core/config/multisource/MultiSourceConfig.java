package com.natsuki_kining.ssr.core.config.multisource;

import com.alibaba.druid.pool.DruidDataSource;
import com.natsuki_kining.ssr.core.config.properties.DruidProperties;
import com.natsuki_kining.ssr.core.config.properties.SSRProperties;
import com.natsuki_kining.ssr.core.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


/**
 * TODO
 *
 * @Author : natsuki_kining
 * @Date : 2020/9/4 22:25 multisource
 */
@Component
public class MultiSourceConfig {

    @Autowired
    private SSRProperties ssrProperties;

    @Autowired
    private DruidProperties druidProperties;

    /**
     * 主数据源
     */
    private DruidDataSource masterDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        druidProperties.config(dataSource);
        return dataSource;
    }


    /**
     * 单数据源连接池配置
     */
//    @Bean
//    @ConditionalOnProperty(prefix = "ssr.enable", name = "multi-data-source-open", havingValue = "false")
//    public DruidDataSource singleDatasource() {
//        return masterDataSource();
//    }

    /**
     * 多数据源连接池配置
     */
    @Bean
    @ConditionalOnProperty(prefix = "ssr.enable", name = "multi-data-source-open", havingValue = "true")
    public DynamicDataSource multiDataSource() {

        DruidDataSource masterDataSource = masterDataSource();
        Map<Object, Object> multiDruidDataSourceMap = new HashMap<>();
        multiDruidDataSourceMap.put(Constant.MultiDataSource.masterDataSourceName, masterDataSource);
        if (ssrProperties.getMultiDataSource() != null && ssrProperties.getMultiDataSource().size() > 0) {
            ssrProperties.getMultiDataSource().forEach((k, v) -> {
                DruidDataSource druidDataSource = new DruidDataSource();
                v.config(druidDataSource);
                multiDruidDataSourceMap.put(k, druidDataSource);
            });
        }

        multiDruidDataSourceMap.entrySet().forEach(v -> {
            try {
                ((DruidDataSource) v.getValue()).init();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setDefaultTargetDataSource(masterDataSource);
        dynamicDataSource.setTargetDataSources(multiDruidDataSourceMap);
        return dynamicDataSource;
    }


}
