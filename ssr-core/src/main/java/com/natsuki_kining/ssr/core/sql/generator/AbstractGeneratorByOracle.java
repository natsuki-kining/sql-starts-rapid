package com.natsuki_kining.ssr.core.sql.generator;

import com.natsuki_kining.ssr.core.beans.QueryParams;
import com.natsuki_kining.ssr.core.beans.QueryRule;

/**
 * 生成oracle查询sql抽象类
 *
 * @Author : natsuki_kining
 * @Date : 2020/6/22 22:48
 */
public abstract class AbstractGeneratorByOracle extends AbstractGeneratorSQL {

    @Override
    public void generatePageSQL(StringBuilder querySql, QueryRule queryRule, QueryParams queryParams) {
        if (queryParams.getPageSize() == -1) {
            return;
        }
        if (!queryParams.isGeneratePage()) {
            return;
        }
        String sql = querySql.toString();
        querySql = new StringBuilder();
        querySql.append("SELECT * ");
        querySql.append("FROM (SELECT t.*, ROWNUM AS rowno ");
        querySql.append("FROM (");
        querySql.append(sql);
        querySql.append(") t ");
        querySql.append("WHERE ROWNUM <= ");
        querySql.append(queryParams.getPageEnd());
        querySql.append(") t2 ");
        querySql.append("WHERE t2.rowno > ");
        querySql.append(queryParams.getPageStart());
    }


}
