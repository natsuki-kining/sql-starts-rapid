package com.natsuki_kining.ssr.core.sql.generator;

import com.natsuki_kining.ssr.core.beans.QueryParams;
import com.natsuki_kining.ssr.core.beans.QueryRule;

/**
 * TODO
 *
 * @Author : natsuki_kining
 * @Date : 2020/5/30 14:19
 */
public interface Generator {

    /**
     * 根据参数自动生成查询的sql语句
     *
     * @param queryRule   查询规则
     * @param queryParams 查询参数
     * @return 生成要查询的sql
     */
    String generateQuerySQL(QueryRule queryRule, QueryParams queryParams);

    /**
     * 生成排序排序
     *
     * @param querySql    sql StringBuilder
     * @param queryRule   查询规则
     * @param queryParams 查询参数
     */
    void generateSortSQL(StringBuilder querySql, QueryRule queryRule, QueryParams queryParams);

    /**
     * 生成分页语句
     *
     * @param querySql    sql StringBuilder
     * @param queryRule   查询规则
     * @param queryParams 查询参数
     */
    void generatePageSQL(StringBuilder querySql, QueryRule queryRule, QueryParams queryParams);
}
