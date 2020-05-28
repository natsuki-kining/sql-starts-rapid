package com.natsuki_kining.ssr.test.project.intercept;

import com.natsuki_kining.ssr.annotation.QueryCode;
import com.natsuki_kining.ssr.beans.QueryParams;
import com.natsuki_kining.ssr.beans.SSRDynamicSql;
import com.natsuki_kining.ssr.intercept.QueryJavaIntercept;
import org.springframework.stereotype.Component;

/**
 * query-user 拦截器
 * @Author natsuki_kining
 **/
@Component
@QueryCode("query-user")
public class QueryUserIntercept extends QueryJavaIntercept {
    @Override
    public boolean preHandle(QueryParams queryParams, SSRDynamicSql dynamicSql) {
        //do something
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
