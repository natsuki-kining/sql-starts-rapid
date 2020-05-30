package com.natsuki_kining.ssr.sql;

import com.natsuki_kining.ssr.beans.QueryParams;
import com.natsuki_kining.ssr.beans.QueryRule;
import com.natsuki_kining.ssr.enums.QueryCodeType;
import com.natsuki_kining.ssr.exception.SSRException;
import com.natsuki_kining.ssr.sql.generator.SQLGenerator;
import com.natsuki_kining.ssr.sql.template.SQLTemplate;
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
    private SQLGenerator sqlGenerator;

    @Autowired
    private SQLTemplate ssrTemplate;

    @Override
    public String getQuerySQL(QueryRule queryRule, QueryParams queryParams) {
        StringBuilder stringBuilder;
        if (QueryCodeType.GENERATE_QUERY == queryRule.getQueryCodeType()) {
            stringBuilder = new StringBuilder(sqlGenerator.generate(queryParams));
        } else if (QueryCodeType.SINGLE_QUERY == queryRule.getQueryCodeType()) {
            String sql = ssrTemplate.formatSQL(queryRule.getDynamicSql().getSqlTemplate(), queryParams);
            stringBuilder = new StringBuilder(sql);
        } else {
            throw new SSRException("没有找到对应的queryRule：" + queryRule);
        }
        //处理分页
        //处理排序
        return stringBuilder.toString();
    }
}
