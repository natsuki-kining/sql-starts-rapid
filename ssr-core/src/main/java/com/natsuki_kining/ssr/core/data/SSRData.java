package com.natsuki_kining.ssr.core.data;


import com.natsuki_kining.ssr.core.beans.SSRDynamicSQL;

/**
 * 数据接口
 *
 * @Author : natsuki_kining
 * @Date : 2020/4/27 20:49
 */
public interface SSRData {

    /**
     * 查询SSR_DYNAMIC_SQL表，并返回SSRDynamicSQL
     *
     * @param code 查询的code
     * @return SSRDynamicSQL对象
     */
    SSRDynamicSQL getSSRDynamicSQL(String code);

}
