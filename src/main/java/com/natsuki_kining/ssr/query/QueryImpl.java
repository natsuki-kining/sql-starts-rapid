package com.natsuki_kining.ssr.query;

import com.natsuki_kining.ssr.beans.QueryParams;
import com.natsuki_kining.ssr.beans.SSRDynamicSql;
import com.natsuki_kining.ssr.proxy.SSRProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 查询的实现类
 *
 * 使用代理调用拦截器里的方法
 *
 * @Author natsuki_kining
 * @Date 2020/4/16 20:02
 **/
@Component
public class QueryImpl implements Query {

    @Autowired
    private QueryDao dao;

    @Autowired
    private SSRProxy proxy;

    public Object query(QueryParams queryParams, SSRDynamicSql dynamicSql) {
        Object queryData = proxy.getInstance(dao).query(queryParams, dynamicSql);
        return queryData;
    }
}
