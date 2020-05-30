package com.natsuki_kining.ssr.intercept;

import com.alibaba.fastjson.JSON;
import com.natsuki_kining.ssr.beans.QueryParams;
import com.natsuki_kining.ssr.beans.SSRDynamicSQL;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 脚本查询拦截器
 *
 * @Author : natsuki_kining
 * @Date : 2020/4/13 23:39
 */
public abstract class QueryScriptIntercept implements QueryIntercept {

    protected String paramsName = "ssrParams";
    protected String resultName = "ssrResult";

    @Override
    public boolean preHandle(QueryParams queryParams, SSRDynamicSQL dynamicSql, Map<String, Object> preData) {
        String preScript = dynamicSql.getPreScript();
        if (StringUtils.isBlank(preScript)) {
            return true;
        }
        return (boolean) executeScript(preScript, queryParams);
    }

    @Override
    public void queryBefore(QueryParams queryParams, SSRDynamicSQL dynamicSql, Map<String, Object> preData) {
        String beforeScript = dynamicSql.getPreScript();
        if (StringUtils.isNotBlank(beforeScript)) {
            executeScript(beforeScript, queryParams);
        }
    }

    @Override
    public Object queryAfter(QueryParams queryParams, SSRDynamicSQL dynamicSql, Map<String, Object> preData, Object queryData) {
        String beforeScript = dynamicSql.getPreScript();
        if (StringUtils.isBlank(beforeScript)) {
            return queryData;
        }
        Map<String, Object> scriptParam = new HashMap<>();
        scriptParam.put("queryParams", queryParams);
        scriptParam.put("queryData", queryData);
        scriptParam.put("preData", preData);
        return executeScript(beforeScript, scriptParam);
    }

    /**
     * 类型转换
     *
     * @param obj   需要转换的对象
     * @param clazz 要转换成的类型
     * @param <T>   泛型
     * @return 转换后的值
     */
    public <T> T convert(Object obj, Class<T> clazz) {
        if (baseNumberType(clazz)) {
            Object value = null;
            Number number = (Number) obj;
            if (clazz == Integer.class) {
                value = number.intValue();
            } else if (clazz == Short.class) {
                value = number.shortValue();
            } else if (clazz == Long.class) {
                value = number.longValue();
            } else if (clazz == Double.class) {
                value = number.doubleValue();
            } else if (clazz == Float.class) {
                value = number.floatValue();
            } else if (clazz == Byte.class) {
                value = number.byteValue();
            }
            return (T) value;
        } else if (baseOtherType(clazz) || equalsType(clazz, obj)) {
            return (T) obj;
        }
        T t = JSON.parseObject(JSON.toJSONString(obj), clazz);
        return t;
    }

    /**
     * 判断是否是数值类型
     *
     * @param clazz
     * @return
     */
    protected boolean baseNumberType(Class<?> clazz) {
        return clazz == Integer.class
                || clazz == Short.class
                || clazz == Long.class
                || clazz == Double.class
                || clazz == Float.class
                || clazz == Byte.class;
    }

    /**
     * 判断是否是布尔类型，字符类型，字符串类型
     *
     * @param clazz
     * @return
     */
    protected boolean baseOtherType(Class<?> clazz) {
        return clazz == Boolean.class
                || clazz == Character.class
                || clazz == String.class;
    }

    /**
     * 判断是否是对象类型
     *
     * @param clazz
     * @param object
     * @return
     */
    protected boolean equalsType(Class<?> clazz, Object object) {
        return object.getClass() == clazz;
    }

    /**
     * 判断是否是基础类型等
     *
     * @param clazz
     * @param object
     * @return
     */
    protected boolean baseType(Class<?> clazz, Object object) {
        return baseNumberType(clazz) || baseOtherType(clazz) || equalsType(clazz, object);
    }

    /**
     * 执行脚本
     *
     * @param script    执行的脚本
     * @param ssrParams 传入脚本的参数
     * @return 执行的结果
     */
    public abstract Object executeScript(String script, Object ssrParams);

    /**
     * 将执行脚本的结果数据转成成需要的对象
     *
     * @param script    执行的脚本
     * @param ssrParams 传入脚本的参数
     * @param clazz     需要转成的对象的类
     * @param <T>       转换的泛型
     * @return
     */
    public <T> T executeScript(String script, Object ssrParams, Class<T> clazz) {
        return convert(executeScript(script, ssrParams), clazz);
    }
}
