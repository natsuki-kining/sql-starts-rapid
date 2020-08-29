package com.natsuki_kining.ssr.core.beans;

import lombok.Data;

import java.io.Serializable;

/**
 * 查询相关信息实体
 *
 * @Author natsuki_kining
 * @Date 2020/6/6 17:45
 **/
@Data
public class QueryInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    public QueryInfo(QuerySQL querySQL) {
        this.querySQL = querySQL;
    }

    private QuerySQL querySQL;

    private Long queryStartTime;

    private Long queryEndTime;
}
