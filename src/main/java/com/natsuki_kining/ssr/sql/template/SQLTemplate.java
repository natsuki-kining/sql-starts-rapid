package com.natsuki_kining.ssr.sql.template;

import com.natsuki_kining.ssr.beans.QueryParams;

/**
 * 模板接口
 *
 * @Author natsuki_kining
 * @Date 2020/4/16 20:02
 **/
public interface SQLTemplate {

    /**
     * 格式化sql
     *
     * @param templateSql sql模板
     * @param queryParams 查询参数
     * @return orm能解析的sql
     */
    String formatSQL(String templateSql, QueryParams queryParams);

}
