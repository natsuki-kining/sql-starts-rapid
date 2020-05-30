package com.natsuki_kining.ssr.sql;

import com.natsuki_kining.ssr.beans.QueryParams;
import com.natsuki_kining.ssr.beans.SSRDynamicSQL;

/**
 * 获取执行sql的接口
 *
 * @Author : natsuki_kining
 * @Date : 2020/5/30 14:00
 */
public interface SQL {

    /**
     * 获取查询SQL
     * @param dynamicSql
     * @param queryParams
     * @return 查询SQL
     */
    String getQuerySQL(SSRDynamicSQL dynamicSql, QueryParams queryParams);
}
