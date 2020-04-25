package com.natsuki_kining.ssr.intercept.script;

import com.alibaba.fastjson.JSON;
import com.esotericsoftware.reflectasm.FieldAccess;
import com.natsuki_kining.ssr.intercept.QueryScriptIntercept;
import org.python.core.PyBoolean;
import org.python.core.PyFloat;
import org.python.core.PyInteger;
import org.python.core.PyString;
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
    public Object executeScript(String script, Object ssrParams) {
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.set(paramsName, ssrParams);
        interpreter.exec(script);
        PyObject pyObject = interpreter.get(resultName);
        return pyObject;
    }

    @Override
    public <T> T convert(Object obj, Class<T> clazz) {
        Object value = null;
        if (obj instanceof PyBoolean) {
            PyBoolean pyBoolean = (PyBoolean) obj;
            int booleanValue = pyBoolean.getValue();
            value = booleanValue != 0;
        } else if (obj instanceof PyFloat) {
            PyFloat pyFloat = (PyFloat) obj;
            value = pyFloat.getValue();
        } else if (obj instanceof PyInteger) {
            PyInteger pyInteger = (PyInteger) obj;
            value = pyInteger.getValue();
        } else if (obj instanceof PyString) {
            PyString pyString = (PyString) obj;
            value = pyString.getString();
        } else {
            FieldAccess fieldAccess = FieldAccess.get(PyObject.class);
            value = fieldAccess.get(obj, "attributes");
        }

        if (value == null) {
            return null;
        }


        //基本数据类型和字符串
        if (baseType(clazz, value)) {
            return (T) value;
        } else {
            return JSON.parseObject(JSON.toJSONString(value), clazz);
        }
    }
}
