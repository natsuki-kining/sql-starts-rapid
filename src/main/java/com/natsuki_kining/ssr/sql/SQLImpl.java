package com.natsuki_kining.ssr.sql;

import com.natsuki_kining.ssr.beans.QueryParams;
import com.natsuki_kining.ssr.beans.SSRDynamicSQL;
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
public class SQLImpl implements SQL{

    @Autowired
    private SQLGenerator sqlGenerator;

    @Autowired
    private SQLTemplate ssrTemplate;


    @Override
    public String getQuerySQL(SSRDynamicSQL dynamicSql, QueryParams queryParams) {
        StringBuilder stringBuilder;
        if (dynamicSql == null){
            stringBuilder = new StringBuilder(sqlGenerator.generate(queryParams));
        }else{
            String sql = ssrTemplate.formatSQL(dynamicSql.getSqlTemplate(), queryParams);
            stringBuilder = new StringBuilder(sql);
        }
        //处理分页
        //处理排序
        return stringBuilder.toString();
    }
}
