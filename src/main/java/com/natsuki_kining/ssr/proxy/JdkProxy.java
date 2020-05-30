package com.natsuki_kining.ssr.proxy;

import com.natsuki_kining.ssr.beans.QueryParams;
import com.natsuki_kining.ssr.beans.QueryRule;
import com.natsuki_kining.ssr.beans.SSRDynamicSQL;
import com.natsuki_kining.ssr.data.dao.QueryORM;
import com.natsuki_kining.ssr.intercept.QueryJavaIntercept;
import com.natsuki_kining.ssr.intercept.QueryScriptIntercept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * jdk代理
 * <p>
 * 处理查询前后的方法
 *
 * @Author natsuki_kining
 * @Date 2020/4/16 20:02
 **/
@Component
public class JdkProxy implements InvocationHandler, SSRProxy {

    @Autowired
    private ProxyConfig proxyConfig;

    private QueryORM target;

    @Override
    public QueryORM getInstance(QueryORM target) {
        this.target = target;
        Class<?> clazz = target.getClass();
        return (QueryORM) Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        QueryParams queryParams = (QueryParams) args[1];
        QueryRule queryRule = proxyConfig.getRule().analysis(queryParams.getCode());
        Map<String, String> queryList = queryRule.getQueryList();
        if (queryList == null){
            SSRDynamicSQL dynamicSql = null;
            if (queryRule.singleQuery()) {
                dynamicSql = target.getSSRDynamicSQL(queryRule.getQueryCode());
            }
            args[0] = proxyConfig.getSQL().getQuerySQL(dynamicSql,queryParams);
            return invoke(method, args, null, queryRule.getQueryCode(), null, queryParams);
        }else{
            Map<String, Object> preDate = new HashMap<>();
            Object value = null;
            for (String queryCode : queryRule.getQueryList().keySet()) {
                SSRDynamicSQL dynamicSql = target.getSSRDynamicSQL(queryRule.getQueryCode());
                String querySQL = proxyConfig.getSQL().getQuerySQL(dynamicSql, queryParams);
                args[0] = querySQL;
                value = invoke(method, args, preDate, queryCode, dynamicSql, queryParams);
            }
            return value;
        }
    }

    private Object invoke(Method method, Object[] args, Map<String, Object> preDate, String queryCode, SSRDynamicSQL dynamicSql, QueryParams queryParams) throws InvocationTargetException, IllegalAccessException {
        //调用拦截器的预处理方法判断是否需要往下执行
        boolean preHandle = preHandle(queryParams, dynamicSql, preDate);
        if (!preHandle) {
            return null;
        }

        //调用拦截器的查询前方法
        queryBefore(queryParams, dynamicSql, preDate);

        //查询
        Object queryData = method.invoke(this.target, args);

        //调用拦截器的查询后方法
        queryData = queryAfter(queryParams, dynamicSql, preDate, queryData);

        return queryData;
    }

    private Object queryAfter(QueryParams queryParams, SSRDynamicSQL dynamicSql, Map<String, Object> preData, Object queryData) {
        QueryJavaIntercept javaMasterIntercept = proxyConfig.getJavaMasterIntercept();
        if (javaMasterIntercept != null) {
            queryData = javaMasterIntercept.queryAfter(queryParams, dynamicSql, preData, queryData);
        }
        QueryJavaIntercept queryCodeIntercept = proxyConfig.getJavaIntercept(dynamicSql.getQueryCode());
        if (queryCodeIntercept != null) {
            queryData = queryCodeIntercept.queryAfter(queryParams, dynamicSql, preData, queryData);
        }
        QueryScriptIntercept scriptIntercept = proxyConfig.getScriptIntercept();
        if (scriptIntercept != null) {
            queryData = scriptIntercept.queryAfter(queryParams, dynamicSql, preData, queryData);
        }
        return queryData;
    }

    private void queryBefore(QueryParams queryParams, SSRDynamicSQL dynamicSql, Map<String, Object> preData) {
        QueryJavaIntercept javaMasterIntercept = proxyConfig.getJavaMasterIntercept();
        if (javaMasterIntercept != null) {
            javaMasterIntercept.queryBefore(queryParams, dynamicSql, preData);
        }
        QueryJavaIntercept queryCodeIntercept = proxyConfig.getJavaIntercept(dynamicSql.getQueryCode());
        if (queryCodeIntercept != null) {
            queryCodeIntercept.queryBefore(queryParams, dynamicSql, preData);
        }
        QueryScriptIntercept scriptIntercept = proxyConfig.getScriptIntercept();
        if (scriptIntercept != null) {
            scriptIntercept.queryBefore(queryParams, dynamicSql, preData);
        }
    }

    private boolean preHandle(QueryParams queryParams, SSRDynamicSQL dynamicSql, Map<String, Object> preData) {
        QueryJavaIntercept javaMasterIntercept = proxyConfig.getJavaMasterIntercept();
        if (javaMasterIntercept != null && !javaMasterIntercept.preHandle(queryParams, dynamicSql, preData)) {
            return false;
        }
        QueryJavaIntercept queryCodeIntercept = proxyConfig.getJavaIntercept(dynamicSql.getQueryCode());
        if (queryCodeIntercept != null && !queryCodeIntercept.preHandle(queryParams, dynamicSql, preData)) {
            return false;
        }
        QueryScriptIntercept scriptIntercept = proxyConfig.getScriptIntercept();
        return scriptIntercept == null || scriptIntercept.preHandle(queryParams, dynamicSql, preData);
    }

}
