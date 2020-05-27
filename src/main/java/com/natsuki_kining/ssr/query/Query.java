package com.natsuki_kining.ssr.query;

import com.natsuki_kining.ssr.beans.QueryParams;

import java.util.List;
import java.util.Map;

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
    List<Map> query(QueryParams queryParams);

    <E> List<E> query(QueryParams queryParams, Class<E> clazz);

}
