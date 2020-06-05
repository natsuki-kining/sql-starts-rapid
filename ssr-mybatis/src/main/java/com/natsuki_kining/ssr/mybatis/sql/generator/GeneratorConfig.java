package com.natsuki_kining.ssr.mybatis.sql.generator;

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

    @Bean(name = {"com.mysql.cj.jdbc.Driver","com.mysql.jdbc.Driver"})
    public GenerateByMySQL generateByMySQL(){
        return new GenerateByMySQL();
    }

    @Bean(name = {"oracle.jdbc.driver.OracleDriver","oracle.jdbc.OracleDriver"})
    public GenerateByOracle generateByOracle(){
        return new GenerateByOracle();
    }


}
