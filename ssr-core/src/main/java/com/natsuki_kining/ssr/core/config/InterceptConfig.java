package com.natsuki_kining.ssr.core.config;

import com.natsuki_kining.ssr.core.annotation.QueryCode;
import com.natsuki_kining.ssr.core.beans.QueryInfo;
import com.natsuki_kining.ssr.core.beans.QueryParams;
import com.natsuki_kining.ssr.core.beans.SSRDynamicSQL;
import com.natsuki_kining.ssr.core.intercept.AbstractQueryJavaIntercept;
import com.natsuki_kining.ssr.core.intercept.AbstractQueryScriptIntercept;
import com.natsuki_kining.ssr.core.intercept.QueryIntercept;
import com.natsuki_kining.ssr.core.utils.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

/**
 * 拦截器配置类
 *
 * @Author : natsuki_kining
 * @Date : 2020/8/29 20:17
 */
@Slf4j
@Component
public class InterceptConfig extends SSRConfig implements QueryIntercept {

    @Autowired(required = false)
    private AbstractQueryScriptIntercept scriptIntercept;

    @Autowired(required = false)
    private List<AbstractQueryJavaIntercept> queryJavaIntercepts;

    private Map<String, AbstractQueryJavaIntercept> queryJavaInterceptMap = new ConcurrentHashMap<>();
    private Map<String, AbstractQueryJavaIntercept> queryJavaPatternInterceptMap = new ConcurrentHashMap<>();

    private AbstractQueryJavaIntercept javaMasterIntercept = null;

    AbstractQueryJavaIntercept getJavaIntercept(String code) {
        AbstractQueryJavaIntercept javaIntercept = queryJavaInterceptMap.get(code);
        if (javaIntercept != null){
            return javaIntercept;
        }
        javaIntercept = queryJavaPatternInterceptMap.get(code);
        if (javaIntercept != null){
            return javaIntercept;
        }
        AtomicReference<AbstractQueryJavaIntercept> abstractQueryJavaIntercept = new AtomicReference<>();
        queryJavaInterceptMap.forEach((k, v)->{
            if(Pattern.matches(k, code)){
                abstractQueryJavaIntercept.set(v);
            }
        });
        javaIntercept = abstractQueryJavaIntercept.get();
        if (javaIntercept != null){
            queryJavaPatternInterceptMap.put(code,javaIntercept);
        }
        return javaIntercept;
    }

    @Override
    public boolean preHandle(QueryParams queryParams) {
        if (javaMasterIntercept != null && !javaMasterIntercept.preHandle(queryParams)) {
            return false;
        }
        AbstractQueryJavaIntercept queryCodeIntercept = getJavaIntercept(queryParams.getQueryCode());
        if (queryCodeIntercept != null && !queryCodeIntercept.preHandle(queryParams)) {
            return false;
        }
        return scriptIntercept == null || scriptIntercept.preHandle(queryParams);
    }

    @Override
    public void queryBefore(QueryParams queryParams, QueryInfo queryInfo, SSRDynamicSQL dynamicSql, Map<String, Object> preData) {
        //master拦截器
        if (javaMasterIntercept != null) {
            javaMasterIntercept.queryBefore(queryParams, queryInfo, dynamicSql, preData);
        }
        //自定义的code拦截器
        AbstractQueryJavaIntercept queryCodeIntercept = getJavaIntercept(dynamicSql.getQueryCode());
        if (queryCodeIntercept != null) {
            queryCodeIntercept.queryBefore(queryParams, queryInfo, dynamicSql, preData);
        }
        //脚本拦截器
        if (scriptIntercept != null) {
            scriptIntercept.queryBefore(queryParams, queryInfo, dynamicSql, preData);
        }
        //last拦截器
        AbstractQueryJavaIntercept lastIntercept = getJavaIntercept(Constant.Intercept.SSR_LAST_INTERCEPT);
        if (lastIntercept != null) {
            lastIntercept.queryBefore(queryParams, queryInfo, dynamicSql, preData);
        }
    }

    @Override
    public Object queryAfter(QueryParams queryParams, QueryInfo queryInfo, SSRDynamicSQL dynamicSql, Map<String, Object> preData, Object queryData) {
        //master拦截器
        if (javaMasterIntercept != null) {
            queryData = javaMasterIntercept.queryAfter(queryParams, queryInfo, dynamicSql, preData, queryData);
        }
        AbstractQueryJavaIntercept queryCodeIntercept = getJavaIntercept(dynamicSql.getQueryCode());
        if (queryCodeIntercept != null) {
            queryData = queryCodeIntercept.queryAfter(queryParams, queryInfo, dynamicSql, preData, queryData);
        }
        if (scriptIntercept != null) {
            queryData = scriptIntercept.queryAfter(queryParams, queryInfo, dynamicSql, preData, queryData);
        }
        //last拦截器
        AbstractQueryJavaIntercept lastIntercept = getJavaIntercept(Constant.Intercept.SSR_LAST_INTERCEPT);
        if (lastIntercept != null) {
            queryData = lastIntercept.queryAfter(queryParams, queryInfo, dynamicSql, preData, queryData);
        }
        return queryData;
    }

    @PostConstruct
    private void init() {
        if (queryJavaIntercepts == null || queryJavaIntercepts.size() == 0) {
            return;
        }
        for (AbstractQueryJavaIntercept queryJavaIntercept : queryJavaIntercepts) {
            if (queryJavaIntercept.getClass().isAnnotationPresent(QueryCode.class)) {
                QueryCode annotation = queryJavaIntercept.getClass().getAnnotation(QueryCode.class);
                queryJavaInterceptMap.put(annotation.value(), queryJavaIntercept);
            } else {
                if (javaMasterIntercept == null) {
                    javaMasterIntercept = queryJavaIntercept;
                } else {
                    log.warn("已经存在一个master的java拦截器,");
                }
            }
        }
    }
}
