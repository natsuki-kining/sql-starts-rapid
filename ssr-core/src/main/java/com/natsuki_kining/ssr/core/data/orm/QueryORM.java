package com.natsuki_kining.ssr.core.data.orm;

import com.natsuki_kining.ssr.core.beans.QueryParams;
import com.natsuki_kining.ssr.core.beans.QueryResult;
import com.natsuki_kining.ssr.core.beans.QuerySQL;
import com.natsuki_kining.ssr.core.data.SSRData;

import java.util.List;

/**
 * dao 接口
 *
 * @Author : natsuki_kining
 * @Date : 2020/5/24 18:32
 */
public interface QueryORM extends SSRData {

    /**
     * 使用ORM查询数据库返回结果集
     *
     * @param querySQL         查询的sql
     * @param queryParams 查询的参数
     * @param returnType  返回的类型
     * @param <E>         泛型、返回的结果集里的数据类型
     * @return 查询的结果集
     */
    <E> List<E> selectList(QuerySQL querySQL, QueryParams queryParams, Class<E> returnType);

    <T> QueryResult queryResult(QuerySQL querySQL, QueryParams queryParams, Class<T> returnType);
}
