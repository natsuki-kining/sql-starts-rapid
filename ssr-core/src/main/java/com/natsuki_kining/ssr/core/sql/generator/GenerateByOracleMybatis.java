package com.natsuki_kining.ssr.core.sql.generator;


/**
 * TODO
 *
 * @Author : natsuki_kining
 * @Date : 2020/5/31 21:36
 */
public class GenerateByOracleMybatis extends AbstractGeneratorSQL implements Generator {
    @Override
    protected String conditionHandel(String replacement) {
        return replacement;
    }

    @Override
    protected String placeholderParam(String queryCode) {
        return "#{"+queryCode+"} ";
    }
}
