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
     * @param templateSql
     * @param queryParams
     * @return
     */
    String formatSQL(String templateSql,QueryParams queryParams);

}
