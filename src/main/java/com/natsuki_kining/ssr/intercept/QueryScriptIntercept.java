package com.natsuki_kining.ssr.intercept;

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
        return convert(executeScript(preScript,queryParams));
    }

    @Override
    default QueryParams queryBefore(QueryParams queryParams, SSRDynamicSql dynamicSql) {
        String beforeScript = dynamicSql.getPreScript();
        if (StringUtils.isBlank(beforeScript)){
            return queryParams;
        }
        return convert(executeScript(beforeScript,queryParams));
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

    default <T> T convert(Object obj){
        return (T) obj;
    }

    /**
     * 执行脚本
     * @param script 执行的脚本
     * @param ssrParams 传入脚本的参数
     * @return 执行的结果
     */
    Object executeScript(String script,Object ssrParams);
}
