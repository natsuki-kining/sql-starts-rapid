package com.natsuki_kining.ssr.core.intercept.script;

import com.natsuki_kining.ssr.core.exception.SSRException;
import com.natsuki_kining.ssr.core.intercept.AbstractQueryScriptIntercept;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * 执行JavaScript脚本
 *
 * @Author : natsuki_kining
 * @Date : 2020/4/18 20:28
 */
@Component
@ConditionalOnProperty(prefix = "ssr", name = "script.type", havingValue = "javaScript")
public class JavaScriptIntercept extends AbstractQueryScriptIntercept {

    @Override
    public Object executeScript(String script, Object ssrParams) {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        engine.put(paramsName, ssrParams);
        engine.getBindings(ScriptContext.ENGINE_SCOPE);
        Object result = null;
        try {
            engine.eval(script);
            result = engine.get(resultName);
        } catch (ScriptException e) {
            throw new SSRException(e.getMessage(), e);
        }
        return result;
    }

}
