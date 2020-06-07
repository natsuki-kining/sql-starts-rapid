package com.natsuki_kining.ssr.core.beans;

import lombok.Data;

/**
 * TODO
 *
 * @Author natsuki_kining
 * @Date 2020/6/6 17:45
 **/
@Data
public class QueryInfo {

    public QueryInfo(String querySQL) {
        this.querySQL = querySQL;
    }

    private String querySQL;

    private Long queryStartTime;

    private Long queryEndTime;
}
