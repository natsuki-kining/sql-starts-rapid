package com.natsuki_kining.ssr.enums;

/**
 * 查询code类型枚举类
 * <p>
 * 查询的queryCode规则：code：module
 *
 * @Author : natsuki_kining
 * @Date : 2020/5/30 22:49
 */
public enum QueryCodeType {

    /**
     * 不需要写sql，会根据条件自动生成查询sql
     * 例如：自动查询用户表
     * queryCode=类全路径:generate
     * queryCode=com.xxx.entity.User:generate
     */
    GENERATE_QUERY,

    /**
     * 默认会根据queryCode去查询SSRDynamicSQL，然后执行里面的sql
     * 例如：查询用户
     * queryCode=SSRDynamicSQL里的QUEYR_CODE字段
     * queryCode=query-user
     * 也可以写成：queryCode=query-user:single
     */
    SINGLE_QUERY,

    /**
     * 遍历里面的queryCode，并把查询的值传给下一次查询
     * 例如：queryCode=SSRDynamicSQL里的QUEYR_CODE字段的集合，用英文逗号分隔
     * queryCode=query-user-role,query-role,query-user
     * 也可以写成：queryCode=com.xxx.entity.UserRole:generate,com.xxx.entity.Role:generate,com.xxx.entity.User:generate
     */
    EACH_QUERY,
    ;
}
