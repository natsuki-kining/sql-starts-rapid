package com.natsuki_kining.ssr.core.beans;

import com.natsuki_kining.ssr.core.enums.QueryConnect;
import com.natsuki_kining.ssr.core.enums.QueryOperationalCharacter;
import lombok.Data;

/**
 * 查询条件
 *
 * @Author natsuki_kining
 * @Date 2020/6/5 18:21
 **/
@Data
public class QueryCondition {

    public QueryCondition(QueryConnect queryConnect, QueryOperationalCharacter operationalCharacter, String queryCode) {
        this.queryConnect = queryConnect;
        this.operationalCharacter = operationalCharacter;
        this.queryCode = queryCode;
    }

    /**
     * 连接符
     * and
     * or
     */
    private QueryConnect queryConnect;

    /**
     * 运算符
     * =
     * <>
     * ……
     */
    private QueryOperationalCharacter operationalCharacter;

    /**
     * 通过id则会使用括号包起来
     */
    private String queryCode;

}


