package com.natsuki_kining.ssr.core.proxy;


import com.natsuki_kining.ssr.core.data.orm.QueryORM;

/**
 * 代理接口
 * 默认使用JDK代理
 * 如果需要自己实现其他的代理、继承此接口，重写getInstance方法，然后交给spring管理、再加上注解@Primary
 *
 * @Author : natsuki_kining
 * @Date : 2020/4/18 19:52
 */
public interface SSRProxy {

    /**
     * 获取代理示例
     *
     * @param target 代理的目标类
     * @return QueryORM 代理后的对象
     */
    QueryORM getInstance(QueryORM target);
}
