package com.natsuki_kining.ssr.core.data;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.natsuki_kining.ssr.core.beans.SSRDynamicSQL;
import com.natsuki_kining.ssr.core.data.cache.AbstractCache;
import com.natsuki_kining.ssr.core.data.cache.SSRCache;
import com.natsuki_kining.ssr.core.data.orm.QueryORM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 获取数据的实现类
 *
 * @Author natsuki_kining
 * @Date 2020/6/11 20:05
 **/
@Component
public class SSRDataImpl implements SSRData {

    @Autowired(required=false)
    private AbstractCache cache;
    @Autowired
    private QueryORM orm;

    /**
     * 委派调用
     *
     * @param queryCode 查询的code
     * @return
     */
    @Override
    public SSRDynamicSQL getSSRDynamicSQL(String queryCode) {
        SSRDynamicSQL dynamicSQL;

        if (cache == null){
            return orm.getSSRDynamicSQL(queryCode);
        }

        dynamicSQL = cache.getSSRDynamicSQL(queryCode);

        //布隆过滤器判断
        if (dynamicSQL == null && cache.bloomFilter.mightContain(queryCode)){
            synchronized (SSRDataImpl.class){
                if (dynamicSQL == null){
                    dynamicSQL = orm.getSSRDynamicSQL(queryCode);
                }
            }
        }

        return dynamicSQL;
    }
}
