package com.natsuki_kining.ssr.core.beans;

import lombok.Data;

import java.io.Serializable;

/**
 * TODO
 *
 * @Author natsuki_kining
 * @Date 2020/6/6 17:45
 **/
@Data
public class QueryInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    public QueryInfo(String querySQL) {
        this.querySQL = querySQL;
    }

    private String querySQL;

    private Long queryStartTime;

    private Long queryEndTime;
}
