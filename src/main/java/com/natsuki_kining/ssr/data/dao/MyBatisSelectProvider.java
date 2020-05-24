package com.natsuki_kining.ssr.data.dao;

import com.natsuki_kining.ssr.beans.QueryParams;

/**
 * 返回自定义查询sql
 *
 * @Author natsuki_kining
 * @Date 2020/4/29 20:16
 **/
public class MyBatisSelectProvider {

    public String getSql(String sql, QueryParams queryParams){
        return sql;
    }

}
