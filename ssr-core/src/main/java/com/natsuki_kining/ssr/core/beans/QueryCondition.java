package com.natsuki_kining.ssr.core.beans;

import lombok.Data;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * 查询条件
 *
 * @Author natsuki_kining
 * @Date 2020/6/5 18:21
 **/
@Data
public class QueryCondition implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 查询条件的字段名
     */
    private String fieldName;

    /**
     * 查询条件的值
     */
    private Object value;

    /**
     * 逻辑运算符
     * AND OR
     * 不填默认AND
     */
    private String logicalOperator;

    /**
     * 关系运算符
     */
    private String relationalOperator;

    /**
     * 分组查询条件
     */
    private LinkedList<QueryCondition> condition;

    public String getLogicalOperator() {
        if (logicalOperator == null) {
            return null;
        }
        return logicalOperator.trim().toUpperCase();
    }

    public String getRelationalOperator() {
        if (relationalOperator == null) {
            return null;
        }
        return relationalOperator.trim();
    }
}


