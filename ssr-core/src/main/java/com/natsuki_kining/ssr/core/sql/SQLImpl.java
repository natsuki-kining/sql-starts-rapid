package com.natsuki_kining.ssr.core.sql;

import com.natsuki_kining.ssr.core.beans.QueryParams;
import com.natsuki_kining.ssr.core.beans.QueryRule;
import com.natsuki_kining.ssr.core.beans.QuerySQL;
import com.natsuki_kining.ssr.core.config.multisource.MultiSourceConfig;
import com.natsuki_kining.ssr.core.enums.QueryCodeType;
import com.natsuki_kining.ssr.core.sql.generator.GeneratorSQL;
import com.natsuki_kining.ssr.core.sql.template.SQLTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    @Autowired
    private GeneratorSQL generatorSQL;

    @Override
    public QuerySQL getQuerySQL(QueryRule queryRule, QueryParams queryParams) {
        QuerySQL querySQL = new QuerySQL();
        StringBuilder sql;
        if (QueryCodeType.SINGLE_QUERY == queryRule.getQueryCodeType()) {
            String formatSQL = ssrTemplate.formatSQL(queryRule.getDynamicSql().getSqlTemplate(), queryParams);
            sql = new StringBuilder(formatSQL);
        } else {
            sql = new StringBuilder(generatorSQL.generateQuerySQL(queryRule, queryParams));
        }
        querySQL.setSimpleSQL(sql.toString());
        generatorSQL.generateSortSQL(sql, queryRule, queryParams);
        generatorSQL.generatePageSQL(sql, queryRule, queryParams);
        querySQL.setProcessedSQL(sql.toString());
        return querySQL;
    }

}
