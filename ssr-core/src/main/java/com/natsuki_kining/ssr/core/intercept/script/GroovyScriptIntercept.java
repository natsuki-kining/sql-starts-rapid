package com.natsuki_kining.ssr.core.intercept.script;

import com.natsuki_kining.ssr.core.intercept.AbstractQueryScriptIntercept;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * groovy脚本
 *
 * @Author : natsuki_kining
 * @Date : 2020/4/18 20:31
 */
@Component
@ConditionalOnProperty(prefix = "ssr", name = "script.type", havingValue = "groovy")
public class GroovyScriptIntercept extends AbstractQueryScriptIntercept {

    @Override
    public Object executeScript(String script, Object ssrParams) {
        Binding binding = new Binding();
        binding.setVariable(paramsName, ssrParams);
        GroovyShell shell = new GroovyShell(binding);
        shell.evaluate(script);
        return binding.getVariable(resultName);
    }

}
