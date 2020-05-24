package com.natsuki_kining.ssr.query.orm;

import com.natsuki_kining.ssr.beans.QueryParams;
import com.natsuki_kining.ssr.beans.SSRDynamicSql;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

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
    public <T> T query(SSRDynamicSql dynamicSql, QueryParams queryParams) {
        return null;
    }

}
