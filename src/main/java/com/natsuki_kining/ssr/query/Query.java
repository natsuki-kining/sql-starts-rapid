package com.natsuki_kining.ssr.query;

import com.natsuki_kining.ssr.beans.QueryParams;
import com.natsuki_kining.ssr.beans.QueryResult;

import java.util.List;

/**
 * TODO
 *
 * @Author natsuki_kining
 * @Date 2020/4/16 20:02
 **/
public class Query<T> implements IQuery<T> {

    @Override
    public T query() {
        return null;
    }

    @Override
    public List<T> queryList() {
        return null;
    }

    @Override
    public QueryResult<T> queryPage(QueryParams queryParams) {
        return null;
    }
}
