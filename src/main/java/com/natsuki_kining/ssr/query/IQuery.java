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
public interface IQuery<T> {

    /**
     * 查询单条记录
     * @return
     */
    T query();

    /**
     * 查询多条记录
     * @return
     */
    List<T> queryList();

    /**
     * 分页查询
     * @param queryParams
     * @return
     */
    QueryResult<T> queryPage(QueryParams queryParams);

}
