package com.natsuki_kining.ssr.mybatis.config.multisource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.natsuki_kining.ssr.core.config.properties.DruidProperties;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * TODO
 *
 * @Author : natsuki_kining
 * @Date : 2020/9/4 23:35
 */
@Configuration
public class MultiDataSourceConfig {

    @Autowired
    private DruidProperties druidProperties;

    @Bean(name = "primaryDatasource")
    @Qualifier("primaryDatasource")
    public DruidDataSource primaryDatasource(){
        DruidDataSource druidDataSource = new DruidDataSource();
        druidProperties.config(druidDataSource);
        return druidDataSource;
    }

    @Bean(name = "primarySqlSessionFactory")
    public SqlSessionFactory primarySqlSessionFactory(@Qualifier("primaryDatasource")
                                                              DruidDataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath*:mybatis/mapper/**/*Mapper.xml");
        bean.setMapperLocations(resources);
        return bean.getObject();
    }

    @Bean(name = "backDatasource")
    @Qualifier("backDatasource")
    @ConfigurationProperties(prefix = "ssr.datasource")
    public DruidDataSource backDatasource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "backSqlSessionFactory")
    public SqlSessionFactory backSqlSessionFactory(@Qualifier("backDatasource")
                                                           DruidDataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        Resource[] resources = new     PathMatchingResourcePatternResolver().getResources("classpath*:mybatis/mapper/**/*Mapper.xml");
        bean.setMapperLocations(resources);
        return bean.getObject();
    }


}
