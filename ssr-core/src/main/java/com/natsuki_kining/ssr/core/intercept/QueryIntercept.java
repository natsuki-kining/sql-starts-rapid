package com.natsuki_kining.ssr.core.intercept;


import com.natsuki_kining.ssr.core.beans.QueryInfo;
import com.natsuki_kining.ssr.core.beans.QueryParams;
import com.natsuki_kining.ssr.core.beans.SSRDynamicSQL;

import java.util.Map;

/**
 * 查询拦截器
 *
 * @Author : natsuki_kining
 * @Date : 2020/4/13 23:39
 */
public interface QueryIntercept {

    /**
     * 预处理回调方法，实现处理器的预处理（如检查参数）
     *
     * @param queryParams 查询参数
     * @return true表示继续流程（如调用下一个拦截器或处理器）；false表示流程中断(比如检查提交的参数不符合要求)
     */
    boolean preHandle(QueryParams queryParams);

    /**
     * 查询之前对参数和sql的一些处理
     *
     * @param queryParams 查询参数
     * @param queryInfo   查询的相关信息
     * @param dynamicSql  查询sql、脚本等
     * @param preData     上一次执行的结果集
     */
    void queryBefore(QueryParams queryParams, QueryInfo queryInfo, SSRDynamicSQL dynamicSql, Map<String, Object> preData);

    /**
     * 查询之后
     * @param queryParams 查询之后
     * @param queryInfo 查询的相关信息->sql，查询耗时等
     * @param dynamicSql 查询sql模板、脚本等
     * @param preData 上一次执行的结果集
     * @param queryData 查询得到的结果
     * @return 查询处理后的数据
     */
    Object queryAfter(QueryParams queryParams, QueryInfo queryInfo, SSRDynamicSQL dynamicSql, Map<String, Object> preData, Object queryData);
}
