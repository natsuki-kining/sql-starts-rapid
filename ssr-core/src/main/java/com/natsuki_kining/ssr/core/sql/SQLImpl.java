package com.natsuki_kining.ssr.core.sql;

import com.natsuki_kining.ssr.core.beans.QueryParams;
import com.natsuki_kining.ssr.core.beans.QueryRule;
import com.natsuki_kining.ssr.core.enums.QueryCodeType;
import com.natsuki_kining.ssr.core.sql.generator.Generator;
import com.natsuki_kining.ssr.core.sql.template.SQLTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * 获取sql的实现类
 *
 * @Author : natsuki_kining
 * @Date : 2020/5/30 14:09
 */
@Component
@Slf4j
public class SQLImpl implements SQL {

    @Autowired
    private SQLTemplate ssrTemplate;

    @Resource(name = "${ssr.orm.type}-${spring.datasource.driver-class-name}")
    private Generator generator;

    @Override
    public String getQuerySQL(QueryRule queryRule, QueryParams queryParams) {
        StringBuilder sql;
        if (QueryCodeType.SINGLE_QUERY == queryRule.getQueryCodeType()) {
            String formatSQL = ssrTemplate.formatSQL(queryRule.getDynamicSql().getSqlTemplate(), queryParams);
            sql = new StringBuilder(formatSQL);
        }else{
            sql = new StringBuilder(generator.generateQuerySQL(queryRule,queryParams));
        }
        return sql.toString();
    }

}
