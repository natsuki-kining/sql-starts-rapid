package com.natsuki_kining.ssr.sql;

import com.natsuki_kining.ssr.beans.QueryParams;
import com.natsuki_kining.ssr.beans.QueryRule;
import com.natsuki_kining.ssr.enums.QueryCodeType;
import com.natsuki_kining.ssr.sql.generator.Generator;
import com.natsuki_kining.ssr.sql.template.SQLTemplate;
import com.natsuki_kining.ssr.utils.Assert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;

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

    @Resource(name = "${spring.datasource.driver-class-name}")
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
        //处理排序
        generator.generateSortSQL(sql,queryRule,queryParams);
        //处理分页
        generator.generatePageSQL(sql,queryRule,queryParams);
        return sql.toString();
    }

    @PostConstruct
    private void init(){
        System.out.println(1);
    }
}
