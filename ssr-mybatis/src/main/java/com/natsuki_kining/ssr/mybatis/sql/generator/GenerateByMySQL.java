package com.natsuki_kining.ssr.mybatis.sql.generator;

import com.natsuki_kining.ssr.core.beans.QueryParams;
import com.natsuki_kining.ssr.core.beans.QueryRule;
import com.natsuki_kining.ssr.core.sql.generator.AbstractGeneratorSQL;
import com.natsuki_kining.ssr.core.sql.generator.Generator;
import com.natsuki_kining.ssr.core.utils.Constant;

/**
 * TODO
 *
 * @Author : natsuki_kining
 * @Date : 2020/5/31 21:36
 */
public class GenerateByMySQL extends AbstractGeneratorSQL implements Generator {

    @Override
    protected String likeConditionHandel(String replacement,String placeholderParam) {
        if (Constant.Condition.ALL_LIKE.equals(replacement)) {
            return "LIKE concat(concat('%',"+placeholderParam+"),'%') ";
        }else if (Constant.Condition.LEFT_LIKE.equals(replacement)) {
            return "LIKE concat('%',"+placeholderParam+") ";
        }else{
            return "LIKE concat("+placeholderParam+",'%') ";
        }
    }

    @Override
    protected String placeholderParam(String queryCode) {
        return "#{"+queryCode+"} ";
    }

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
