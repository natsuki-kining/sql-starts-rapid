package com.natsuki_kining.ssr.test.project.intercept;

import com.natsuki_kining.ssr.beans.QueryParams;
import com.natsuki_kining.ssr.beans.SSRDynamicSql;
import com.natsuki_kining.ssr.intercept.QueryJavaIntercept;
import org.springframework.stereotype.Component;

/**
 * 查询拦截器
 * @Author natsuki_kining
 **/
@Component
public class SSRQueryIntercept extends QueryJavaIntercept {

    @Override
    public boolean preHandle(QueryParams queryParams, SSRDynamicSql dynamicSql) {
        return true;
    }

    @Override
    public void queryBefore(QueryParams queryParams, SSRDynamicSql dynamicSql) {
        System.out.println(queryParams);
    }

    @Override
    public Object queryAfter(QueryParams queryParams, SSRDynamicSql dynamicSql, Object queryData, Object preData) {
        return queryData;
    }
}
