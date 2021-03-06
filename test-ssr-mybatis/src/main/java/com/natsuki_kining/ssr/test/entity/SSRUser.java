package com.natsuki_kining.ssr.test.entity;

import com.natsuki_kining.ssr.core.annotation.TableName;

/**
 * 测试实体
 *
 * @Author : natsuki_kining
 * @Date : 2020/5/23 23:37
 */
@TableName("ssr_user")
public class SSRUser {

    private Integer id;
    private String name;
    private String userName;
    private String code;
    private String password;

    public SSRUser() {
    }

    public SSRUser(String name, String userName, String code, String password) {
        this.name = name;
        this.userName = userName;
        this.code = code;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
