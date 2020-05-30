package com.natsuki_kining.ssr.test.project.intercept;

import com.natsuki_kining.ssr.annotation.QueryCode;
import com.natsuki_kining.ssr.beans.QueryParams;
import com.natsuki_kining.ssr.beans.SSRDynamicSQL;
import com.natsuki_kining.ssr.intercept.AbstractQueryJavaIntercept;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * query-user 拦截器
 *
 * @Author natsuki_kining
 **/
@Component
@QueryCode("query-user")
public class QueryUserIntercept extends AbstractQueryJavaIntercept {

    @Override
    public boolean preHandle(QueryParams queryParams, SSRDynamicSQL ssrDynamicSQL, Map<String, Object> map) {
        return false;
    }

    @Override
    public void queryBefore(QueryParams queryParams, SSRDynamicSQL ssrDynamicSQL, Map<String, Object> map) {

    }

    @Override
    public Object queryAfter(QueryParams queryParams, SSRDynamicSQL ssrDynamicSQL, Map<String, Object> map, Object o) {
        return null;
    }
}
