package com.natsuki_kining.ssr.query.orm;

import com.natsuki_kining.ssr.beans.QueryParams;
import com.natsuki_kining.ssr.beans.SSRDynamicSql;
import com.natsuki_kining.ssr.data.dao.MyBatisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

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
    public List<Map> selectList(SSRDynamicSql dynamicSql, QueryParams queryParams) {
        return myBatisDao.selectList(formatSQL(dynamicSql,queryParams), queryParams);
    }

    @Override
    public <E> List<E> selectList(SSRDynamicSql dynamicSql, QueryParams queryParams, Class<E> returnType) {
        return myBatisDao.selectList(formatSQL(dynamicSql,queryParams), queryParams,returnType);
    }
}
