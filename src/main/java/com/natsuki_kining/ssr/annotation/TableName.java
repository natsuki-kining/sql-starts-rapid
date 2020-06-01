package com.natsuki_kining.ssr.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 对应数据库表名字
 *
 * @Author natsuki_kining
 **/
@Target({TYPE})
@Retention(RUNTIME)
@Documented
public @interface TableName {

    /**
     * 对应数据库表名字
     * 用于数据库字段名字跟类属性名字不符合转换规则的情况
     * 不加注解默认的规则转换 User -> USER，UserRole -> USER_ROLE
     * 加注解，则按注解的值来转换
     *
     * @return
     */
    String value();
}
