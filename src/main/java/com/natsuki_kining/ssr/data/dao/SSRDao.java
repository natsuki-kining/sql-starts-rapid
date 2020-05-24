package com.natsuki_kining.ssr.data.dao;

import com.natsuki_kining.ssr.beans.QueryParams;
import com.natsuki_kining.ssr.data.SSRData;

/**
 * dao 接口
 *
 * @Author : natsuki_kining
 * @Date : 2020/5/24 18:32
 */
public interface SSRDao extends SSRData {

    Object select(String sql, QueryParams queryParams);

}
