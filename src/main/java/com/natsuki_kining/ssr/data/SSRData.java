package com.natsuki_kining.ssr.data;

import com.natsuki_kining.ssr.beans.SSRDynamicSql;

/**
 * 数据接口
 *
 * @Author : natsuki_kining
 * @Date : 2020/4/27 20:49
 */
public interface SSRData {

    SSRDynamicSql save(SSRDynamicSql dynamicSql);

    SSRDynamicSql get(String code);
}
