package com.natsuki_kining.ssr.core.beans;

import lombok.Data;

/**
 * 查询条件
 *
 * @Author natsuki_kining
 * @Date 2020/6/5 18:21
 **/
@Data
public class QueryCondition {

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


