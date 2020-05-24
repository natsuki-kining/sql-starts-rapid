package com.natsuki_kining.ssr.query.orm;

import com.natsuki_kining.ssr.beans.QueryParams;
import com.natsuki_kining.ssr.beans.SSRDynamicSql;
import com.natsuki_kining.ssr.data.dao.MyBatisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * mybatis查询实现
 *
 * @Author : natsuki_kining
 * @Date : 2020/4/17 18:00
 */
@Component
@ConditionalOnProperty(prefix = "ssr", name = "orm.type", havingValue = "mybatis")
public class QueryMybatisORM extends QueryFormat implements QueryORM {

    @Autowired
    private MyBatisDao myBatisDao;

    @Override
    public <T> T query(SSRDynamicSql dynamicSql, QueryParams queryParams) {
        return (T) myBatisDao.select(formatSQL(dynamicSql,queryParams), queryParams);
    }


}
