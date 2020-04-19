package com.natsuki_kining.ssr.intercept.script;

import com.natsuki_kining.ssr.beans.QueryParams;
import com.natsuki_kining.ssr.beans.SSRDynamicSql;
import com.natsuki_kining.ssr.exception.SSRException;
import com.natsuki_kining.ssr.intercept.QueryScriptIntercept;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.script.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 执行JavaScript脚本
 *
 * @Author : natsuki_kining
 * @Date : 2020/4/18 20:28
 */
@ConditionalOnProperty(prefix = "ssr", name = "script.type", havingValue = "javaScript")
@Component
public class JavaScriptIntercept implements QueryScriptIntercept {

    @Override
    public boolean preHandle(QueryParams queryParams, SSRDynamicSql dynamicSql) {
        String preScript = dynamicSql.getPreScript();
        //空的则不处理
        if (StringUtils.isBlank(preScript)){
            return true;
        }
        return executeScript(preScript,queryParams);
    }

    @Override
    public void queryBefore(QueryParams queryParams, SSRDynamicSql dynamicSql) {
        String beforeScript = dynamicSql.getPreScript();
        if (StringUtils.isNotBlank(beforeScript)){
            queryParams = executeScript(beforeScript,queryParams);
        }
    }

    @Override
    public Object queryAfter(QueryParams queryParams, SSRDynamicSql dynamicSql, Object queryData, Object preData) {
        String beforeScript = dynamicSql.getPreScript();
        if (StringUtils.isBlank(beforeScript)){
            return queryData;
        }
        Map<String,Object> scriptParam = new HashMap<>();
        scriptParam.put("queryParams",queryParams);
        scriptParam.put("queryData",queryData);
        return executeScript(beforeScript,scriptParam);
    }

    @Override
    public <T> T executeScript(String script,Object ssrParams) {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        engine.put(paramsName, ssrParams);
        engine.getBindings(ScriptContext.ENGINE_SCOPE);
        T result = null;
        try {
            engine.eval(script);
            result = (T) engine.get(resultName);
        } catch (ScriptException e) {
            e.printStackTrace();
            throw new SSRException(e.getMessage(),e);
        }
        return result;
    }

}
