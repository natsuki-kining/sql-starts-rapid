package com.natsuki_kining.ssr.core.intercept;

import com.natsuki_kining.ssr.core.beans.QueryInfo;
import com.natsuki_kining.ssr.core.beans.QueryParams;
import com.natsuki_kining.ssr.core.beans.SSRDynamicSQL;

import java.util.Map;

/**
 * java代码拦截器实现
 *
 * @Author : natsuki_kining
 * @Date : 2020/4/18 20:55
 */
public abstract class AbstractQueryJavaIntercept implements QueryIntercept {

    @Override
    public boolean preHandle(QueryParams queryParams) {
        return true;
    }

    @Override
    public void queryBefore(QueryParams queryParams, QueryInfo queryInfo, SSRDynamicSQL dynamicSql, Map<String, Object> preData) {

    }

    @Override
    public Object queryAfter(QueryParams queryParams, QueryInfo queryInfo, SSRDynamicSQL dynamicSql, Map<String, Object> preData, Object queryData) {
        return queryData;
    }
}
