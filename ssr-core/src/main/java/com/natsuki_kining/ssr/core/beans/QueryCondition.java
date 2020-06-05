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

    public QueryCondition(String queryConnect, String operationalCharacter, String queryCode) {
        this.queryConnect = queryConnect;
        this.operationalCharacter = operationalCharacter;
        this.queryCode = queryCode;
    }

    /**
     * 连接符
     * and
     * or
     */
    private String queryConnect;

    /**
     * 运算符 + 参数
     * =
     * <>
     * ……
     */
    private String operationalCharacter;

    /**
     * 查询code
     */
    private String queryCode;

}


