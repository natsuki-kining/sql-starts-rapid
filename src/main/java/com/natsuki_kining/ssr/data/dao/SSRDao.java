package com.natsuki_kining.ssr.data.dao;

import com.natsuki_kining.ssr.beans.QueryParams;
import com.natsuki_kining.ssr.data.SSRData;

import java.util.List;
import java.util.Map;

/**
 * dao 接口
 *
 * @Author : natsuki_kining
 * @Date : 2020/5/24 18:32
 */
public interface SSRDao extends SSRData {

    List<Map> selectList(String sql, QueryParams queryParams);

    <E> List<E> selectList(String sql, QueryParams queryParams, Class<E> returnType);

}
