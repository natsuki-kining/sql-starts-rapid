package com.natsuki_kining.ssr.annotation;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 根据查询code设置拦截器
 * @Author natsuki_kining
 * @Date 2020/5/28 15:12
 **/
@Target({TYPE})
@Retention(RUNTIME)
@Documented
public @interface QueryCode {

    /**
     * 拦截的code值
     * @return
     */
    String value();
}
