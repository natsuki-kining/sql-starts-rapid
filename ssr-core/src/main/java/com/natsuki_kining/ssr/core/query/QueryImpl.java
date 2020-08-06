package com.natsuki_kining.ssr.core.query;

import com.natsuki_kining.ssr.core.beans.QueryParams;
import com.natsuki_kining.ssr.core.beans.QueryResult;
import com.natsuki_kining.ssr.core.beans.QuerySQL;
import com.natsuki_kining.ssr.core.data.orm.QueryORM;
import com.natsuki_kining.ssr.core.proxy.SSRProxy;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class QueryImpl implements Query {

    private final QuerySQL proxySQL = null;

    @Autowired
    private QueryORM orm;
    @Autowired
    private ObjectFactory<SSRProxy> proxy;

    @Override
    public <T> Object query(QueryParams queryParams, Class<T> clazz) {
        return proxy.getObject().getInstance(orm).select(proxySQL, queryParams, clazz);
    }

    @Override
    public Object query(QueryParams queryParams) {
        return query(queryParams, Map.class);
    }

    @Override
    public List<Map> queryList(QueryParams queryParams) {
        return queryList(queryParams, Map.class);
    }

    @Override
    public <T> List<T> queryList(QueryParams queryParams, Class<T> clazz) {
        return (List<T>) query(queryParams, clazz);
    }

    @Override
    public QueryResult queryResult(QueryParams queryParams) {
        return queryResult(queryParams, Map.class);
    }

    @Override
    public <T> QueryResult queryResult(QueryParams queryParams, Class<T> clazz) {
        return (QueryResult) query(queryParams, clazz);
    }

}
