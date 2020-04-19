package com.natsuki_kining.ssr.intercept.script;

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
public class PythonScriptIntercept {
}
