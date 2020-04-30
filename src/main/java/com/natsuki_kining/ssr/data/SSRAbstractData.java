package com.natsuki_kining.ssr.data;

import com.natsuki_kining.ssr.beans.SSRDynamicSql;

/**
 * 数据抽象类
 *
 * @Author : natsuki_kining
 * @Date : 2020/4/27 21:05
 */
public abstract class SSRAbstractData implements SSRData {

    @Override
    public boolean save(SSRDynamicSql dynamicSql) {
        return true;
    }

    @Override
    public SSRDynamicSql get(String code) {
        return null;
    }
}
