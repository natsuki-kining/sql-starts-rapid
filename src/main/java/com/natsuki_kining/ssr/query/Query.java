package com.natsuki_kining.ssr.query;

import com.natsuki_kining.ssr.beans.QueryParams;

/**
 * 查询接口
 *
 * @Author natsuki_kining
 * @Date 2020/4/16 20:02
 **/
public interface Query {

    /**
     * 查询
     * @return
     */
    Object query(QueryParams queryParams);

    <T> T query(QueryParams queryParams, Class<T> clazz);


}
