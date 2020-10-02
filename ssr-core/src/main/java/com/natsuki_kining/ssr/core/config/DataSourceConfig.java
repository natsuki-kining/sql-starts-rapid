package com.natsuki_kining.ssr.core.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.natsuki_kining.ssr.core.config.properties.SSRDruidProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

/**
 * TODO
 *
 * @Author natsuki_kining
 * @Date 2020/9/4 17:08
 **/
//@Configuration
public class DataSourceConfig {

    @Autowired
    private SSRDruidProperties SSRDruidProperties;

    @Bean
    public DataSource druidDataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();
        SSRDruidProperties.config(druidDataSource);
        return druidDataSource;
    }

}
