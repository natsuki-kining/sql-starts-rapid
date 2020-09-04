package com.natsuki_kining.ssr.core.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.natsuki_kining.ssr.core.config.properties.DruidProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * TODO
 *
 * @Author natsuki_kining
 * @Date 2020/9/4 17:08
 **/
@Configuration
public class DataSourceConfig {

    @Autowired
    private DruidProperties druidProperties;

    @Bean
    public DataSource druidDataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();
        druidProperties.config(druidDataSource);
        return druidDataSource;
    }

}
