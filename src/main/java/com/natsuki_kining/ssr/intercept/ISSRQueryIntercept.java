package com.natsuki_kining.ssr.intercept;

/**
 * 查询拦截器
 *
 * @Author : natsuki_kining
 * @Date : 2020/4/13 23:39
 */
public interface ISSRQueryIntercept {

    //预处理回调方法，实现处理器的预处理（如检查参数）
    //返回值：true表示继续流程（如调用下一个拦截器或处理器）；false表示流程中断（如登录检查失败），不会继续调用其他的拦截器或处理器
    default boolean preHandle(){
        return true;
    };

    //查询之前
    void queryBefore();

    //查询之后
    void queryAfter();
}
