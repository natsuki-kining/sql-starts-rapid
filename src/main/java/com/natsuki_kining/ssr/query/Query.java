package com.natsuki_kining.ssr.query;

import com.natsuki_kining.ssr.beans.QueryParams;
import com.natsuki_kining.ssr.beans.SSRDynamicSql;

/**
 * 查询接口
 *
 * @Author natsuki_kining
 * @Date 2020/4/16 20:02
 **/
public interface Query {

    /**
     * 查询
     * @return
     */
    <T> T query(QueryParams queryParams);

}
