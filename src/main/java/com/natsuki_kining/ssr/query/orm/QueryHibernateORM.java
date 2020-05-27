package com.natsuki_kining.ssr.query.orm;

import com.natsuki_kining.ssr.beans.QueryParams;
import com.natsuki_kining.ssr.beans.SSRDynamicSql;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * hibernate查询实现
 *
 * @Author : natsuki_kining
 * @Date : 2020/4/17 18:00
 */
@Component
@ConditionalOnProperty(prefix = "ssr", name = "orm.type", havingValue = "hibernate")
public class QueryHibernateORM extends QueryFormat implements QueryORM {


    @Override
    public List<Map> selectList(SSRDynamicSql dynamicSql, QueryParams queryParams) {
        return selectList(dynamicSql,queryParams,Map.class);
    }

    @Override
    public <E> List<E> selectList(SSRDynamicSql dynamicSql, QueryParams queryParams, Class<E> returnType) {
        return null;
    }
}
