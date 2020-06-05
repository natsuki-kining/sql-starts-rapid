package com.natsuki_kining.ssr.core.enums;

/**
 * TODO
 *
 * @Author natsuki_kining
 * @Date 2020/6/5 18:45
 **/
public enum QueryConnect {

    AND("AND"),
    OR("OR"),
    ;

    private String connect;
    QueryConnect(String connect){
        this.connect = connect;
    }

    public String value(){
        return connect;
    }
}
