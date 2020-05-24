package com.natsuki_kining.ssr.test.project;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * TODO
 *
 * @Author : natsuki_kining
 * @Date : 2020/5/23 23:36
 */
@SpringBootApplication
@ComponentScan({"com.natsuki_kining"})
@MapperScan(basePackages={"com.natsuki_kining.ssr.test.project","com.natsuki_kining.ssr.data.dao"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
