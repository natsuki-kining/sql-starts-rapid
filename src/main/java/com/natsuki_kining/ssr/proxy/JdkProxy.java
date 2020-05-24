package com.natsuki_kining.ssr.proxy;

import com.natsuki_kining.ssr.beans.QueryParams;
import com.natsuki_kining.ssr.beans.SSRDynamicSql;
import com.natsuki_kining.ssr.intercept.QueryJavaIntercept;
import com.natsuki_kining.ssr.intercept.QueryScriptIntercept;
import com.natsuki_kining.ssr.query.orm.QueryORM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * jdk代理
 *
 * 处理查询前后的方法
 *
 * @Author natsuki_kining
 * @Date 2020/4/16 20:02
 **/
@Component
public class JdkProxy implements InvocationHandler,SSRProxy {

    @Autowired
    private QueryJavaIntercept intercept;

    @Autowired
    private QueryScriptIntercept script;

    //上一次的查询结果，目前只存在一个上一次的查询结果，后面如果改造成多线程再修改。
    private static Object preData;

    private QueryORM target;

    @Override
    public QueryORM getInstance(QueryORM target){
        this.target = target;

        Class<?> clazz = target.getClass();

        return (QueryORM) Proxy.newProxyInstance(clazz.getClassLoader(),clazz.getInterfaces(),this);
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        SSRDynamicSql dynamicSql = (SSRDynamicSql) args[0];
        QueryParams queryParams = (QueryParams) args[1];

        //调用拦截器的预处理方法判断是否需要往下执行
        boolean preHandle = intercept.preHandle(queryParams,dynamicSql);
        if (!preHandle){
            return null;
        }

        boolean scriptPreHandle = script.preHandle(queryParams,dynamicSql);
        if (!scriptPreHandle){
            return null;
        }

        //调用拦截器的查询前方法
        intercept.queryBefore(queryParams,dynamicSql);
        script.queryBefore(queryParams,dynamicSql);

        //查询
        Object queryData = method.invoke(this.target, args);

        //调用拦截器的查询后方法
        queryData = intercept.queryAfter(queryParams, dynamicSql, queryData, preData);
        queryData = script.queryAfter(queryParams, dynamicSql, queryData, preData);

        //保存处理结果
        preData = queryData;

        return queryData;
    }

}
