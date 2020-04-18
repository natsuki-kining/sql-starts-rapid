package com.natsuki_kining.ssr.proxy;

import com.natsuki_kining.ssr.beans.QueryParams;
import com.natsuki_kining.ssr.intercept.QueryIntercept;
import com.natsuki_kining.ssr.query.QueryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
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
@Scope("prototype")
public class JdkProxy implements InvocationHandler,SSRProxy {

    @Autowired(required = false)
    private QueryIntercept intercept;

    //上一次的查询结果，目前只存在一个上一次的查询结果，后面如果改造成多线程再修改。
    private static Object preData;

    private QueryDao target;

    @Override
    public QueryDao getInstance(QueryDao target){
        this.target = target;

        Class<?> clazz = target.getClass();

        return (QueryDao) Proxy.newProxyInstance(clazz.getClassLoader(),clazz.getInterfaces(),this);
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        QueryParams queryParams = (QueryParams) args[0];
        String sql = (String) args[0];

        //调用拦截器的预处理方法判断是否需要往下执行
        boolean preHandle = intercept.preHandle(queryParams);
        if (!preHandle){
            return null;
        }

        //调用拦截器的查询前方法
        intercept.queryBefore(queryParams,sql);

        //查询
        Object queryData = method.invoke(this.target, args);

        //调用拦截器的查询后方法
        queryData = intercept.queryAfter(queryParams, sql, queryData, preData);

        //保存处理结果
        preData = queryData;

        return queryData;
    }

}
