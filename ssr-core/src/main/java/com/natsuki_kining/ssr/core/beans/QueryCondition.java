package com.natsuki_kining.ssr.core.beans;

import lombok.Data;

import java.io.Serializable;

/**
 * 查询条件
 *
 * @Author natsuki_kining
 * @Date 2020/6/5 18:21
 **/
@Data
public class QueryCondition implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 查询code
     */
    private String queryCode;

    private Object value;

    private String connect;

    private String operational;

    private String groupId;

    private String groupConnect;

    public String getConnect() {
        if (connect == null){
            return null;
        }
        return connect.trim();
    }

    public String getOperational() {
        if (operational == null){
            return null;
        }
        return operational.trim();
    }
}


