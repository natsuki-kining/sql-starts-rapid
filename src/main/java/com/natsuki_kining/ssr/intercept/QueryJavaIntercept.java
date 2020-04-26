package com.natsuki_kining.ssr.intercept;

import com.natsuki_kining.ssr.beans.QueryParams;
import com.natsuki_kining.ssr.beans.SSRDynamicSql;

/**
 * java代码拦截器实现
 *
 * 使用接口里的默认实现
 *
 * 如果需要自定义、则新建个类实现QueryJavaIntercept，加上注解@Component和@Primary
 *
 * @Author : natsuki_kining
 * @Date : 2020/4/18 20:55
 */
public abstract class QueryJavaIntercept implements QueryIntercept{

    @Override
    public boolean preHandle(QueryParams queryParams, SSRDynamicSql dynamicSql) {
        return true;
    }

    @Override
    public void queryBefore(QueryParams queryParams, SSRDynamicSql dynamicSql) {
    }

    @Override
    public Object queryAfter(QueryParams queryParams, SSRDynamicSql dynamicSql, Object queryData, Object preData) {
        return queryData;
    }

}
