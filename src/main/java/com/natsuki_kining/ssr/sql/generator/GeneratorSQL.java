package com.natsuki_kining.ssr.sql.generator;

import com.natsuki_kining.ssr.beans.QueryParams;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @Author : natsuki_kining
 * @Date : 2020/5/30 14:19
 */
@Component
public class GeneratorSQL implements SQLGenerator {

    /**
     * 生成要查询的sql
     * @param queryParams
     * @return
     */
    @Override
    public String generate(QueryParams queryParams) {
        return null;
    }
}