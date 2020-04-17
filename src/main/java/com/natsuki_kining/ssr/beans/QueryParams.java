package com.natsuki_kining.ssr.beans;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @Author : natsuki_kining
 * @Date : 2020/4/14 0:33
 */
@Data
public class QueryParams {

    private String code;
    private Map<String, Object> params;
    private Map<String, String> sort;
    private int pageNo = 1;
    private int pageSize = 10;
    private transient int pageEnd;
    private transient int pageStart;
    private transient int initialCapacity = 0;

    public String getCode() {
        if (code == null){
            code = "";
        }
        return code;
    }

    public Map<String, Object> getParams() {
        if(params == null){
            params = new HashMap<>(initialCapacity);
        }
        return params;
    }

    public Map<String, String> getSort() {
        if(sort == null){
            sort = new HashMap<>(initialCapacity);
        }
        return sort;
    }

    public int getPageNo() {
        if(pageNo<1){
            pageNo = 1;
        }
        return pageNo;
    }

    public int getPageSize() {
        if(pageSize<1){
            pageSize = 10;
        }
        return pageSize;
    }

    public int getPageEnd() {
        pageEnd = getPageNo() * getPageSize();
        return pageEnd;
    }

    public int getPageStart() {
        pageStart = getPageEnd() - getPageSize();
        return pageStart;
    }
}
