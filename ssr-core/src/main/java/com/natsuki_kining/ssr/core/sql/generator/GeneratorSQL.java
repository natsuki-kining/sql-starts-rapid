package com.natsuki_kining.ssr.core.sql.generator;

import com.natsuki_kining.ssr.core.beans.QueryParams;
import com.natsuki_kining.ssr.core.beans.QueryRule;
import com.natsuki_kining.ssr.core.config.multisource.DataSourceContextHolder;
import com.natsuki_kining.ssr.core.config.multisource.MultiSourceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @Author natsuki_kining
 * @Date 2020/9/9 19:22
 **/
@Component
public class GeneratorSQL implements Generator {

    @Autowired
    private MultiSourceConfig multiSourceConfig;
    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public String generateQuerySQL(QueryRule queryRule, QueryParams queryParams) {
        return generator().generateQuerySQL(queryRule, queryParams);
    }

    @Override
    public void generateSortSQL(StringBuilder querySql, QueryRule queryRule, QueryParams queryParams) {
        generator().generateSortSQL(querySql, queryRule, queryParams);
    }

    @Override
    public void generatePageSQL(StringBuilder querySql, QueryRule queryRule, QueryParams queryParams) {
        generator().generatePageSQL(querySql, queryRule, queryParams);
    }

    private Generator generator(){
        Generator generator = DataSourceContextHolder.getGeneratorType();
        if (generator==null){
            generator = (Generator) applicationContext.getBean("generator-"+multiSourceConfig.getCurrentThreadDbType());
            DataSourceContextHolder.setGeneratorType(generator);
        }
        return generator;
    }
}
