package com.natsuki_kining.ssr.hibernate.sql.generator;


import com.natsuki_kining.ssr.core.beans.QueryParams;
import com.natsuki_kining.ssr.core.beans.QueryRule;
import com.natsuki_kining.ssr.core.sql.generator.AbstractGeneratorSQL;
import com.natsuki_kining.ssr.core.sql.generator.Generator;

/**
 * TODO
 *
 * @Author : natsuki_kining
 * @Date : 2020/5/31 21:36
 */
public class GenerateByHibernate extends AbstractGeneratorSQL implements Generator {

    @Override
    public void generateWhereSQL(StringBuilder querySql, QueryRule queryRule, QueryParams queryParams) {
        super.generateWhereSQL(querySql, queryRule, queryParams);
    }

}
