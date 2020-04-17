package com.natsuki_kining.ssr.query;

import com.natsuki_kining.ssr.beans.QueryParams;

/**
 * TODO
 *
 * @Author natsuki_kining
 * @Date 2020/4/16 20:02
 **/
public interface Query {

    /**
     * 查询
     * @return
     */
    Object query(QueryParams queryParams,String sql);

}
