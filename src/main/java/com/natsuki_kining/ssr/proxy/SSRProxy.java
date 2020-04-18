package com.natsuki_kining.ssr.proxy;

import com.natsuki_kining.ssr.query.QueryDao;

/**
 * 代理接口
 * 默认使用JDK代理
 * 如果需要自己实现其他的代理、继承此接口，重写getInstance方法，然后交给spring管理、再加上注解@Primary
 *
 * @Author : natsuki_kining
 * @Date : 2020/4/18 19:52
 */
public interface SSRProxy {

    QueryDao getInstance(QueryDao target);
}
