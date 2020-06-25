package com.natsuki_kining.ssr.core.beans;

import com.natsuki_kining.ssr.core.enums.QueryResultCode;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 查询结果
 *
 * @Author natsuki_kining
 * @Date 2020/4/16 20:02
 **/
@Data
public class QueryResult implements Serializable {

    private static final long serialVersionUID = 1L;

    private Object data;

    private Integer code;

    private String message;

    public QueryResult setData(Object data) {
        this.data = data;
        return this;
    }

    public QueryResult setCode(Integer code) {
        this.code = code;
        return this;
    }

    public QueryResult setCode(QueryResultCode queryResultCode) {
        this.code = queryResultCode.getCode();
        return this;
    }

    public QueryResult setMessage(String message) {
        this.message = message;
        return this;
    }
}
