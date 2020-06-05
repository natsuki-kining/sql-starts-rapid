package com.natsuki_kining.ssr.core.enums;

/**
 * 查询运算符
 *
 * @Author natsuki_kining
 * @Date 2020/6/5 18:31
 **/
public enum QueryOperationalCharacter {


    EQ("= :name"),
    NEQ("<> :name"),
    LT("< :name"),
    LE("<= :name"),
    GT("> :name"),
    GE(">= :name"),

    L_LIKE("LIKE \"%\"||:name"),
    R_LIKE("LIKE :name||\"%\""),
    A_LIKE("LIKE \"%\"||:name||\"%\"")

    ;

    private String operator;

    QueryOperationalCharacter(String operator){
        this.operator = operator;
    }

    public String value(){
        return operator;
    }
}
