package com.natsuki_kining.ssr.core.query;

import com.natsuki_kining.ssr.core.beans.QueryParams;
import com.natsuki_kining.ssr.core.beans.QueryResult;
import com.natsuki_kining.ssr.core.beans.QuerySQL;
import com.natsuki_kining.ssr.core.data.orm.QueryORM;
import com.natsuki_kining.ssr.core.proxy.SSRProxy;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 查询的实现类
 * <p>
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
    private ObjectFactory<SSRProxy> proxy;

    private QuerySQL proxySQL = null;

    @Override
    public List<Map> query(QueryParams queryParams) {
        return query(queryParams, Map.class);
    }

    @Override
    public <E> List<E> query(QueryParams queryParams, Class<E> clazz) {
        return proxy.getObject().getInstance(orm).selectList(proxySQL, queryParams, clazz);
    }

    @Override
    public QueryResult queryResult(QueryParams queryParams) {
        return queryResult(queryParams, Map.class);
    }

    @Override
    public <T> QueryResult queryResult(QueryParams queryParams, Class<T> clazz) {
        return proxy.getObject().getInstance(orm).queryResult(proxySQL, queryParams, clazz);
    }
}
