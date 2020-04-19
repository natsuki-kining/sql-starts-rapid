package com.natsuki_kining.ssr.intercept.java;

import com.natsuki_kining.ssr.intercept.QueryJavaIntercept;
import org.springframework.stereotype.Component;

/**
 * java代码拦截器实现
 *
 * 使用接口里的默认实现
 *
 * 如果需要自定义、则新建个类实现QueryJavaIntercept，加上注解@Component和@Primary
 *
 * @Author : natsuki_kining
 * @Date : 2020/4/18 20:55
 */
@Component
public class DefaultJavaIntercept implements QueryJavaIntercept {
}
