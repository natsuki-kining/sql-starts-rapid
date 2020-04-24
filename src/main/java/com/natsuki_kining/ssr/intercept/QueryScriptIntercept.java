package com.natsuki_kining.ssr.intercept;

import com.alibaba.fastjson.JSON;
import com.natsuki_kining.ssr.beans.QueryParams;
import com.natsuki_kining.ssr.beans.SSRDynamicSql;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 脚本查询拦截器
 *
 * @Author : natsuki_kining
 * @Date : 2020/4/13 23:39
 */
public interface QueryScriptIntercept extends QueryIntercept {

    String paramsName = "ssrParams";
    String resultName = "ssrResult";

    @Override
    default boolean preHandle(QueryParams queryParams, SSRDynamicSql dynamicSql) {
        String preScript = dynamicSql.getPreScript();
        if (StringUtils.isBlank(preScript)){
            return true;
        }
        return (boolean) executeScript(preScript,queryParams);
    }

    @Override
    default void queryBefore(QueryParams queryParams, SSRDynamicSql dynamicSql) {
        String beforeScript = dynamicSql.getPreScript();
        if (StringUtils.isNotBlank(beforeScript)){
            executeScript(beforeScript,queryParams);
        }
    }

    @Override
    default Object queryAfter(QueryParams queryParams, SSRDynamicSql dynamicSql, Object queryData, Object preData) {
        String beforeScript = dynamicSql.getPreScript();
        if (StringUtils.isBlank(beforeScript)){
            return queryData;
        }
        Map<String,Object> scriptParam = new HashMap<>();
        scriptParam.put("queryParams",queryParams);
        scriptParam.put("queryData",queryData);
        scriptParam.put("preData",preData);
        return executeScript(beforeScript,scriptParam);
    }

    default <T> T queryAfter(QueryParams queryParams, SSRDynamicSql dynamicSql, Object queryData, Object preData,Class<T> clazz) {
        return convert(queryAfter(queryParams,dynamicSql,queryData,preData),clazz);
    }

    default <T> T convert(Object obj,Class<T> clazz){
        T t = (T) JSON.parseObject(JSON.toJSONString(obj), clazz);
        return t;
    }

    /**
     * 执行脚本
     * @param script 执行的脚本
     * @param ssrParams 传入脚本的参数
     * @return 执行的结果
     */
    <T> T executeScript(String script,Object ssrParams);
}
