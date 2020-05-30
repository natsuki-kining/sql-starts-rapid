package com.natsuki_kining.ssr.sql.generator;

import com.natsuki_kining.ssr.beans.QueryParams;

/**
 * TODO
 *
 * @Author : natsuki_kining
 * @Date : 2020/5/30 14:19
 */
public interface SQLGenerator {

    /**
     * 根据参数自动生成查询的sql语句
     *
     * @param queryParams
     * @return 生成要查询的sql
     */
    String generate(QueryParams queryParams);

}
