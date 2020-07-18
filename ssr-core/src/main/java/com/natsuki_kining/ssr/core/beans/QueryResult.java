package com.natsuki_kining.ssr.core.beans;

import com.natsuki_kining.ssr.core.enums.QueryStatus;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询结果
 *
 * @Author natsuki_kining
 * @Date 2020/4/16 20:02
 **/
@Data
public class QueryResult implements Serializable {

    private static final long serialVersionUID = 1L;

    public QueryResult() {
    }

    public QueryResult(QueryStatus queryStatus) {
        this.code = queryStatus.value();
        this.message = queryStatus.getReasonPhrase();
    }

    public QueryResult(QueryStatus queryStatus,String message) {
        this.code = queryStatus.value();
        this.message = message;
    }

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

    public QueryResult setCode(QueryStatus queryStatus) {
        this.code = queryStatus.value();
        return this;
    }

    public QueryResult setMessage(String message) {
        this.message = message;
        return this;
    }
}
