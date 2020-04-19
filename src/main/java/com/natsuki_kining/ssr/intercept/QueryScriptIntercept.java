package com.natsuki_kining.ssr.intercept;

/**
 * 脚本查询拦截器
 *
 * @Author : natsuki_kining
 * @Date : 2020/4/13 23:39
 */
public interface QueryScriptIntercept extends QueryIntercept {

    String paramsName = "ssrParams";
    String resultName = "ssrResult";

    /**
     * 执行脚本
     * @param script 执行的脚本
     * @param ssrParams 传入脚本的参数
     * @return 执行的结果
     */
    <T> T executeScript(String script,Object ssrParams);
}
