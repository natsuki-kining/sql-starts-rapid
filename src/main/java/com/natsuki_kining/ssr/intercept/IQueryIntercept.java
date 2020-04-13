package com.natsuki_kining.ssr.intercept;

import com.natsuki_kining.ssr.beans.QueryParams;

import java.util.Map;

/**
 * 查询拦截器
 *
 * @Author : natsuki_kining
 * @Date : 2020/4/13 23:39
 */
public interface IQueryIntercept extends ISSRIntercept {

    /**
     * 预处理回调方法，实现处理器的预处理（如检查参数）
     * @param queryParams 查询参数
     * @return true表示继续流程（如调用下一个拦截器或处理器）；false表示流程中断(比如检查提交的参数不符合要求)
     */
    default boolean preHandle(QueryParams queryParams){
        return true;
    }

    /**
     * 查询之前对参数和sql的一些处理
     * @param queryParams 查询参数
     * @param sql 查询sql
     */
    default void queryBefore(QueryParams queryParams,StringBuilder sql){

    }

    /**
     * 查询之后
     * @param queryParams 查询之后
     * @param sql 执行的sql
     * @param queryData 查询得到的结果
     * @param preData 上一步执行的结果
     * @return 查询处理后的数据
     */
    default Object queryAfter(QueryParams queryParams,String sql,Object queryData,Object preData){
        return queryData;
    }
}
