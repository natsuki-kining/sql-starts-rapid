package com.natsuki_kining.ssr.query;

import com.natsuki_kining.ssr.beans.QueryParams;
import com.natsuki_kining.ssr.beans.SSRDynamicSql;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * mybatis查询实现
 *
 * @Author : natsuki_kining
 * @Date : 2020/4/17 18:00
 */
@ConditionalOnProperty(prefix = "ssr", name = "orm.type", havingValue = "mybatis")
@Component
public class QueryMybatisDao implements QueryDao {
    public Object query(QueryParams queryParams, SSRDynamicSql dynamicSql) {
        return null;
    }
}
