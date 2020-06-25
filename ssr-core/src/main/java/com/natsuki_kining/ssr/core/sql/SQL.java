package com.natsuki_kining.ssr.core.sql;


import com.natsuki_kining.ssr.core.beans.QueryParams;
import com.natsuki_kining.ssr.core.beans.QueryRule;
import com.natsuki_kining.ssr.core.beans.QuerySQL;

/**
 * 获取执行sql的接口
 *
 * @Author : natsuki_kining
 * @Date : 2020/5/30 14:00
 */
public interface SQL {

    /**
     * 获取查询SQL
     *
     * @param queryRule 查询规则
     * @param queryParams 查询参数
     * @return QuerySQL
     */
    QuerySQL getQuerySQL(QueryRule queryRule, QueryParams queryParams);
}
