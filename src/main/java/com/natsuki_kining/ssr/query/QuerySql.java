package com.natsuki_kining.ssr.query;

import com.natsuki_kining.ssr.beans.QueryParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @Author natsuki_kining
 * @Date 2020/4/16 20:02
 **/
@Component
public class QuerySql implements Query {

    @Autowired
    private Dao dao;

    public Object query(QueryParams queryParams, String sql) {
        return dao.query(queryParams,sql);
    }
}
