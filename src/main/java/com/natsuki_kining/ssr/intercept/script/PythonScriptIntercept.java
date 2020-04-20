package com.natsuki_kining.ssr.intercept.script;

import com.natsuki_kining.ssr.intercept.QueryScriptIntercept;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * python脚本
 *
 * @Author : natsuki_kining
 * @Date : 2020/4/18 20:31
 */
@ConditionalOnProperty(prefix = "ssr", name = "script.type", havingValue = "pythonScript")
@Component
public class PythonScriptIntercept implements QueryScriptIntercept {

    @Override
    public <T> T executeScript(String script, Object ssrParams) {
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.set(paramsName,ssrParams);
        interpreter.exec(script);
        PyObject pyObject = interpreter.get(resultName);
        return (T) pyObject;
    }

}
