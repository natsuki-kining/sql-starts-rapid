package com.natsuki_kining.ssr.core.sql.generator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 将生成sql的实现类纳入spring管理
 * 因为是通过驱动来判断名字，驱动之间又存在多种版本，也为了后续扩展，所以bean需要有多个名字
 *
 * @Author natsuki_kining
 * @Date 2020/6/1 18:33
 **/
@Configuration
public class GeneratorConfig {

    @Bean(name = {"mybatis-com.mysql.cj.jdbc.Driver","mybatis-com.mysql.jdbc.Driver"})
    public GenerateByOracleMybatis GenerateByOracleMybatis(){
        return new GenerateByOracleMybatis();
    }

    @Bean(name = {"mybatis-oracle.jdbc.driver.OracleDriver","mybatis-oracle.jdbc.OracleDriver"})
    public GenerateByMySQLMybatis GenerateByMySQLMybatis(){
        return new GenerateByMySQLMybatis();
    }

    @Bean(name = {"hibernate-com.mysql.cj.jdbc.Driver","hibernate-com.mysql.jdbc.Driver"})
    public GenerateByOracleHibernate GenerateByOracleHibernate(){
        return new GenerateByOracleHibernate();
    }

    @Bean(name = {"hibernate-oracle.jdbc.driver.OracleDriver","hibernate-oracle.jdbc.OracleDriver"})
    public GenerateByMySQLHibernate GenerateByMySQLHibernate(){
        return new GenerateByMySQLHibernate();
    }

}
