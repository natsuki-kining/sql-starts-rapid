package com.natsuki_kining.ssr.query.orm;

import com.natsuki_kining.ssr.beans.QueryParams;
import com.natsuki_kining.ssr.beans.SSRDynamicSql;
import com.natsuki_kining.ssr.sql.template.SSRTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * sql 格式化
 *
 * @Author : natsuki_kining
 * @Date : 2020/5/24 18:41
 */
@Slf4j
public abstract class QueryFormat implements QueryORM {

    @Autowired
    private SSRTemplate template;

    @Override
    public String formatSQL(SSRDynamicSql dynamicSql, QueryParams queryParams) {
        String formatSQL = template.formatSql(dynamicSql.getSqlTemplate(), queryParams);
        log.debug("formatSQL:{}",formatSQL);
        return formatSQL;
    }
}
