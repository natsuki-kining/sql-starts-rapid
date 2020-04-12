package com.natsuki_kining.ssr.beans;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 *
 *
 * @Author natsuki_kining
 * @Date 2020-4-12 23:03:53
 * @Version 1.0.0
 **/
@Data
public class SSRDynamicQueryVO {

    private String queryCode;
    private Map<String, Object> queryData;
    private List<String> sortNames;
    private List<String> sortTypes;
    private int pageNo = 1;
    private int pageSize = 20;

    public String getQueryCode() {
        return queryCode;
    }

    public void setQueryCode(String queryCode) {
        this.queryCode = queryCode;
    }

    public Map<String, Object> getQueryData() {
        return queryData;
    }

    public void setQueryData(Map<String, Object> queryData) {
        this.queryData = queryData;
    }

}