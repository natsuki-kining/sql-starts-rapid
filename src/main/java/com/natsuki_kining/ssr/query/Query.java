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
     * 查询返回map list
     *
     * @param queryParams 查询参数
     * @return map的集合
     */
    List<Map> query(QueryParams queryParams);

    /**
     * 查询返回泛型list
     *
     * @param queryParams 查询参数
     * @param clazz       转换的类型
     * @param <E>         泛型
     * @return 泛型的集合
     */
    <E> List<E> query(QueryParams queryParams, Class<E> clazz);

}
