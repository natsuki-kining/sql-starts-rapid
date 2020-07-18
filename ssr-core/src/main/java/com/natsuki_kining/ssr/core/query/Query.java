package com.natsuki_kining.ssr.core.query;

import com.natsuki_kining.ssr.core.beans.QueryParams;
import com.natsuki_kining.ssr.core.beans.QueryResult;

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
     * @param <T>         泛型
     * @return 泛型的集合
     */
    <T> List<T> query(QueryParams queryParams, Class<T> clazz);

    /**
     * 查询返回QueryResult封装类型
     *
     * @param queryParams
     * @param <T>
     * @return
     */
    <T> QueryResult queryResult(QueryParams queryParams);

    /**
     * 查询返回QueryResult封装类型
     *
     * @param queryParams
     * @param clazz
     * @param <T>
     * @return
     */
    <T> QueryResult queryResult(QueryParams queryParams, Class<T> clazz);
}
