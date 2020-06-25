package com.natsuki_kining.ssr.core.beans;

import lombok.Data;

/**
 * TODO
 *
 * @Author : natsuki_kining
 * @Date : 2020/6/25 15:36
 */
@Data
public class QuerySQL {

    /**
     * 简单的查询SQL
     */
    private String simpleSQL;

    /**
     * 处理过的查询SQL
     */
    private String processedSQL;

    /**
     * 执行的SQL
     */
    private String executeSQL;

}
