package com.natsuki_kining.ssr.enums;

/**
 * TODO
 *
 * @Author : natsuki_kining
 * @Date : 2020/5/30 15:52
 */
public enum ORMType {

    /**
     * 使用hibernate ORM框架
     */
    HIBERNATE("hibernate"),
    /**
     * 使用mybatis ORM框架
     */
    MYBATIS("mybatis"),
    /**
     * 用户自定义ORM查询实现
     */
    USER_DEFINED("user-defined");
    private String type;

    ORMType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

}
