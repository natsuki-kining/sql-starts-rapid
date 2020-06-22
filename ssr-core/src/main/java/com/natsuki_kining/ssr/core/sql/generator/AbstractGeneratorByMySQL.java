package com.natsuki_kining.ssr.core.sql.generator;

import com.natsuki_kining.ssr.core.beans.QueryParams;
import com.natsuki_kining.ssr.core.beans.QueryRule;

/**
 * TODO
 *
 * @Author : natsuki_kining
 * @Date : 2020/6/22 22:48
 */
public abstract class AbstractGeneratorByMySQL extends AbstractGeneratorSQL {

    @Override
    public void generatePageSQL(StringBuilder querySql, QueryRule queryRule, QueryParams queryParams) {
        if (queryParams.getPageSize() == -1){
            return;
        }
        if (!queryParams.isGeneratePage()){
            return;
        }
        querySql.append(" LIMIT ");
        querySql.append(queryParams.getPageStart());
        querySql.append(",");
        querySql.append(queryParams.getPageSize());
    }

}
