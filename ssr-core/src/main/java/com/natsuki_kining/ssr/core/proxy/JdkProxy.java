package com.natsuki_kining.ssr.core.proxy;

import com.natsuki_kining.ssr.core.beans.QueryParams;
import com.natsuki_kining.ssr.core.beans.QueryRule;
import com.natsuki_kining.ssr.core.beans.SSRDynamicSQL;
import com.natsuki_kining.ssr.core.data.orm.QueryORM;
import com.natsuki_kining.ssr.core.enums.QueryCodeType;
import com.natsuki_kining.ssr.core.intercept.AbstractQueryJavaIntercept;
import com.natsuki_kining.ssr.core.intercept.AbstractQueryScriptIntercept;
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

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        QueryParams queryParams = (QueryParams) args[1];
        QueryRule queryRule = proxyConfig.getRule().analysis(queryParams.getQueryCode());
        if (QueryCodeType.EACH_QUERY == queryRule.getQueryCodeType()) {
            Map<String, QueryRule> queryCodeMap = queryRule.getQueryCodeMap();
            Map<String, Object> preDate = new HashMap<>(queryCodeMap.size());
            Object value = null;
            for (Map.Entry<String, QueryRule> entry : queryCodeMap.entrySet()) {
                QueryRule entryValue = entry.getValue();
                args[0] = proxyConfig.getSQL().getQuerySQL(entryValue, queryParams);
                value = invoke(method, args, preDate, entryValue.getDynamicSql(), queryParams);
                preDate.put(entry.getKey(), value);
            }
            return value;
        } else {
            args[0] = proxyConfig.getSQL().getQuerySQL(queryRule, queryParams);
            return invoke(method, args, null, queryRule.getDynamicSql(), queryParams);
        }
    }

    private Object invoke(Method method, Object[] args, Map<String, Object> preDate, SSRDynamicSQL dynamicSql, QueryParams queryParams) throws InvocationTargetException, IllegalAccessException {
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
        AbstractQueryJavaIntercept javaMasterIntercept = proxyConfig.getJavaMasterIntercept();
        if (javaMasterIntercept != null) {
            queryData = javaMasterIntercept.queryAfter(queryParams, dynamicSql, preData, queryData);
        }
        AbstractQueryJavaIntercept queryCodeIntercept = proxyConfig.getJavaIntercept(dynamicSql.getQueryCode());
        if (queryCodeIntercept != null) {
            queryData = queryCodeIntercept.queryAfter(queryParams, dynamicSql, preData, queryData);
        }
        AbstractQueryScriptIntercept scriptIntercept = proxyConfig.getScriptIntercept();
        if (scriptIntercept != null) {
            queryData = scriptIntercept.queryAfter(queryParams, dynamicSql, preData, queryData);
        }
        return queryData;
    }

    private void queryBefore(QueryParams queryParams, SSRDynamicSQL dynamicSql, Map<String, Object> preData) {
        AbstractQueryJavaIntercept javaMasterIntercept = proxyConfig.getJavaMasterIntercept();
        if (javaMasterIntercept != null) {
            javaMasterIntercept.queryBefore(queryParams, dynamicSql, preData);
        }
        AbstractQueryJavaIntercept queryCodeIntercept = proxyConfig.getJavaIntercept(dynamicSql.getQueryCode());
        if (queryCodeIntercept != null) {
            queryCodeIntercept.queryBefore(queryParams, dynamicSql, preData);
        }
        AbstractQueryScriptIntercept scriptIntercept = proxyConfig.getScriptIntercept();
        if (scriptIntercept != null) {
            scriptIntercept.queryBefore(queryParams, dynamicSql, preData);
        }
    }

    private boolean preHandle(QueryParams queryParams, SSRDynamicSQL dynamicSql, Map<String, Object> preData) {
        AbstractQueryJavaIntercept javaMasterIntercept = proxyConfig.getJavaMasterIntercept();
        if (javaMasterIntercept != null && !javaMasterIntercept.preHandle(queryParams, dynamicSql, preData)) {
            return false;
        }
        AbstractQueryJavaIntercept queryCodeIntercept = proxyConfig.getJavaIntercept(dynamicSql.getQueryCode());
        if (queryCodeIntercept != null && !queryCodeIntercept.preHandle(queryParams, dynamicSql, preData)) {
            return false;
        }
        AbstractQueryScriptIntercept scriptIntercept = proxyConfig.getScriptIntercept();
        return scriptIntercept == null || scriptIntercept.preHandle(queryParams, dynamicSql, preData);
    }

}
