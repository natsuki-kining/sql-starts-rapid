package com.natsuki_kining.ssr.core.config.multisource;

import com.alibaba.druid.pool.DruidDataSource;
import com.natsuki_kining.ssr.core.config.properties.SSRDruidProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

/**
 * 动态添加数据源
 *
 * 可以在系统运行的时候添加数据源
 *
 * @Author : natsuki_kining
 * @Date : 2020/10/6 21:43
 */
@Slf4j
@Component
public class DynamicDataSourceHandle {

    @Autowired
    private MultiSourceConfig multiSourceConfig;

    @Autowired
    private ApplicationContext applicationContext;

    public boolean addDatasource(SSRDruidProperties druidProperties) {
        if (druidProperties == null || !druidProperties.check()) {
            return false;
        }
        DynamicDataSource dynamicDatasource = applicationContext.getBean(DynamicDataSource.class);

        DruidDataSource druidDataSource = new DruidDataSource();
        druidProperties.config(druidDataSource);
        try {
            druidDataSource.init();
        } catch (SQLException e) {
            log.error(e.getMessage(),e);
            return false;
        }
        multiSourceConfig.multiDruidDataSourceMap.put(druidProperties.getDataSourceName(), druidDataSource);
        multiSourceConfig.dbTypeMap.put(druidProperties.getDataSourceName(), druidDataSource.getDbType());
        dynamicDatasource.setTargetDataSources(multiSourceConfig.multiDruidDataSourceMap);
        dynamicDatasource.afterPropertiesSet();
        return true;
    }
}
