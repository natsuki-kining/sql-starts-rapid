package com.natsuki_kining.ssr.core.proxy;

import com.natsuki_kining.ssr.core.beans.QueryInfo;
import com.natsuki_kining.ssr.core.beans.QueryParams;
import com.natsuki_kining.ssr.core.beans.QueryRule;
import com.natsuki_kining.ssr.core.beans.SSRDynamicSQL;
import com.natsuki_kining.ssr.core.data.orm.QueryORM;
import com.natsuki_kining.ssr.core.enums.QueryCodeType;
import com.natsuki_kining.ssr.core.intercept.AbstractQueryJavaIntercept;
import com.natsuki_kining.ssr.core.intercept.AbstractQueryScriptIntercept;
import com.natsuki_kining.ssr.core.utils.Constant;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        QueryParams queryParams = (QueryParams) args[1];
        //调用拦截器的预处理方法判断是否需要往下执行
        boolean preHandle = preHandle(queryParams);
        if (!preHandle) {
            return null;
        }

        QueryRule queryRule = proxyConfig.getRule().analysis(queryParams.getQueryCode());
        if (QueryCodeType.EACH_QUERY == queryRule.getQueryCodeType()) {
            Map<String, QueryRule> queryCodeMap = queryRule.getQueryCodeMap();
            Map<String, Object> preDate = new HashMap<>(queryCodeMap.size());
            Object value = null;
            for (Map.Entry<String, QueryRule> entry : queryCodeMap.entrySet()) {
                QueryRule entryValue = entry.getValue();
                args[0] = proxyConfig.getSQL().getQuerySQL(entryValue, queryParams);
                SSRDynamicSQL dynamicSql = entryValue.getDynamicSql();
                value = invoke(method, args, preDate, dynamicSql, queryParams);
                proxyConfig.getCache().save(dynamicSql.getQueryCode(), dynamicSql);
                preDate.put(entry.getKey(), value);
            }
            return value;
        } else {
            args[0] = proxyConfig.getSQL().getQuerySQL(queryRule, queryParams);
            SSRDynamicSQL dynamicSql = queryRule.getDynamicSql();
            Object invoke = invoke(method, args, null, dynamicSql, queryParams);
            proxyConfig.getCache().save(dynamicSql.getQueryCode(), dynamicSql);
            return invoke;
        }
    }

    private Object invoke(Method method, Object[] args, Map<String, Object> preDate, SSRDynamicSQL dynamicSql, QueryParams queryParams) throws InvocationTargetException, IllegalAccessException {
        //调用拦截器的查询前方法
        QueryInfo queryInfo = new QueryInfo((String) args[0]);
        queryBefore(queryParams,queryInfo, dynamicSql, preDate);

        //查询
        args[0] = queryInfo.getQuerySQL();
        queryInfo.setQueryStartTime(System.currentTimeMillis());
        Object queryData = method.invoke(this.target, args);
        queryInfo.setQueryEndTime(System.currentTimeMillis());

        //调用拦截器的查询后方法
        queryData = queryAfter(queryParams,queryInfo, dynamicSql, preDate, queryData);

        return queryData;
    }

    private Object queryAfter(QueryParams queryParams,QueryInfo queryInfo, SSRDynamicSQL dynamicSql, Map<String, Object> preData, Object queryData) {
        //master拦截器
        AbstractQueryJavaIntercept javaMasterIntercept = proxyConfig.getJavaMasterIntercept();
        if (javaMasterIntercept != null) {
            queryData = javaMasterIntercept.queryAfter(queryParams, queryInfo, dynamicSql, preData, queryData);
        }
        AbstractQueryJavaIntercept queryCodeIntercept = proxyConfig.getJavaIntercept(dynamicSql.getQueryCode());
        if (queryCodeIntercept != null) {
            queryData = queryCodeIntercept.queryAfter(queryParams, queryInfo, dynamicSql, preData, queryData);
        }
        AbstractQueryScriptIntercept scriptIntercept = proxyConfig.getScriptIntercept();
        if (scriptIntercept != null) {
            queryData = scriptIntercept.queryAfter(queryParams, queryInfo, dynamicSql, preData, queryData);
        }
        //last拦截器
        AbstractQueryJavaIntercept lastIntercept = proxyConfig.getJavaIntercept(Constant.Intercept.SSR_LAST_INTERCEPT);
        if (lastIntercept != null) {
            queryData = lastIntercept.queryAfter(queryParams, queryInfo, dynamicSql, preData, queryData);
        }
        return queryData;
    }

    private void queryBefore(QueryParams queryParams,QueryInfo queryInfo, SSRDynamicSQL dynamicSql, Map<String, Object> preData) {
        //master拦截器
        AbstractQueryJavaIntercept javaMasterIntercept = proxyConfig.getJavaMasterIntercept();
        if (javaMasterIntercept != null) {
            javaMasterIntercept.queryBefore(queryParams, queryInfo, dynamicSql, preData);
        }
        //自定义的code拦截器
        AbstractQueryJavaIntercept queryCodeIntercept = proxyConfig.getJavaIntercept(dynamicSql.getQueryCode());
        if (queryCodeIntercept != null) {
            queryCodeIntercept.queryBefore(queryParams, queryInfo, dynamicSql, preData);
        }
        //脚本拦截器
        AbstractQueryScriptIntercept scriptIntercept = proxyConfig.getScriptIntercept();
        if (scriptIntercept != null) {
            scriptIntercept.queryBefore(queryParams, queryInfo, dynamicSql, preData);
        }
        //last拦截器
        AbstractQueryJavaIntercept lastIntercept = proxyConfig.getJavaIntercept(Constant.Intercept.SSR_LAST_INTERCEPT);
        if (lastIntercept != null) {
            lastIntercept.queryBefore(queryParams, queryInfo, dynamicSql, preData);
        }
    }

    private boolean preHandle(QueryParams queryParams) {
        AbstractQueryJavaIntercept javaMasterIntercept = proxyConfig.getJavaMasterIntercept();
        if (javaMasterIntercept != null && !javaMasterIntercept.preHandle(queryParams)) {
            return false;
        }
        AbstractQueryJavaIntercept queryCodeIntercept = proxyConfig.getJavaIntercept(queryParams.getQueryCode());
        if (queryCodeIntercept != null && !queryCodeIntercept.preHandle(queryParams)) {
            return false;
        }
        AbstractQueryScriptIntercept scriptIntercept = proxyConfig.getScriptIntercept();
        return scriptIntercept == null || scriptIntercept.preHandle(queryParams);
    }

}
