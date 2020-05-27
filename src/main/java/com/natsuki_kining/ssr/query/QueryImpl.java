package com.natsuki_kining.ssr.query;

import com.natsuki_kining.ssr.beans.QueryParams;
import com.natsuki_kining.ssr.beans.SSRDynamicSql;
import com.natsuki_kining.ssr.data.SSRData;
import com.natsuki_kining.ssr.proxy.SSRProxy;
import com.natsuki_kining.ssr.query.orm.QueryORM;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

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
    private QueryORM orm;

    @Autowired
    private SSRData data;

    @Autowired
    private ObjectFactory<SSRProxy> proxy;

    @Override
    public List<Map> query(QueryParams queryParams) {
        return query(queryParams,Map.class);
    }

    @Override
    public <T> List<T> query(QueryParams queryParams, Class<T> clazz) {
        SSRDynamicSql ssrDynamicSql = data.get(queryParams.getCode());
        return proxy.getObject().getInstance(orm).selectList(ssrDynamicSql, queryParams,clazz);
    }


}
