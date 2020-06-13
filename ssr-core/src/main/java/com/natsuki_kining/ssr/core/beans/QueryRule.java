package com.natsuki_kining.ssr.core.beans;


import com.natsuki_kining.ssr.core.enums.QueryCodeType;

import java.io.Serializable;
import java.util.Map;

/**
 * 查询规则bean
 *
 * @Author : natsuki_kining
 * @Date : 2020/5/30 17:30
 */
public class QueryRule implements Serializable {

    private static final long serialVersionUID = 1L;

    private String queryCode;

    private SSRDynamicSQL dynamicSql;

    private QueryCodeType queryCodeType;

    private Map<String, QueryRule> queryCodeMap;

    public QueryRule(String queryCode, SSRDynamicSQL dynamicSql, QueryCodeType queryCodeType, Map<String, QueryRule> queryCodeMap) {
        this.queryCode = queryCode;
        this.dynamicSql = dynamicSql;
        this.queryCodeType = queryCodeType;
        this.queryCodeMap = queryCodeMap;
    }

    public String getQueryCode() {
        return queryCode;
    }

    public SSRDynamicSQL getDynamicSql() {
        return dynamicSql;
    }

    public QueryCodeType getQueryCodeType() {
        return queryCodeType;
    }

    public Map<String, QueryRule> getQueryCodeMap() {
        return queryCodeMap;
    }
}
