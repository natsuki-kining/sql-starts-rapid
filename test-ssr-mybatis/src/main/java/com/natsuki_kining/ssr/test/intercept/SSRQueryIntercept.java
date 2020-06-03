package com.natsuki_kining.ssr.test.intercept;

import com.natsuki_kining.ssr.core.beans.QueryParams;
import com.natsuki_kining.ssr.core.beans.SSRDynamicSQL;
import com.natsuki_kining.ssr.core.intercept.AbstractQueryJavaIntercept;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 查询拦截器
 *
 * @Author natsuki_kining
 **/
@Component
public class SSRQueryIntercept extends AbstractQueryJavaIntercept {

    @Override
    public boolean preHandle(QueryParams queryParams, SSRDynamicSQL ssrDynamicSQL, Map<String, Object> map) {
        return true;
    }

    @Override
    public void queryBefore(QueryParams queryParams, SSRDynamicSQL ssrDynamicSQL, Map<String, Object> map) {

    }

    @Override
    public Object queryAfter(QueryParams queryParams, SSRDynamicSQL ssrDynamicSQL, Map<String, Object> map, Object data) {
        return data;
    }
}
