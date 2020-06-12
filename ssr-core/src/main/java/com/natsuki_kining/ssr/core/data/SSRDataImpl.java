package com.natsuki_kining.ssr.core.data;

import com.natsuki_kining.ssr.core.beans.SSRDynamicSQL;
import com.natsuki_kining.ssr.core.data.cache.SSRCache;
import com.natsuki_kining.ssr.core.data.orm.QueryORM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 获取数据的实现类
 *
 * @Author natsuki_kining
 * @Date 2020/6/11 20:05
 **/
@Component
public class SSRDataImpl implements SSRData {

    @Autowired
    private SSRCache cache;
    @Autowired
    private QueryORM orm;

    /**
     * 委派调用
     * @param queryCode 查询的code
     * @return
     */
    @Override
    public SSRDynamicSQL getSSRDynamicSQL(String queryCode) {
        SSRDynamicSQL dynamicSQL = cache.getSSRDynamicSQL(queryCode);
        if (dynamicSQL == null){
            return orm.getSSRDynamicSQL(queryCode);
        }
        return dynamicSQL;
    }
}
