package com.natsuki_kining.ssr.core.sql.generator;

/**
 * TODO
 *
 * @Author : natsuki_kining
 * @Date : 2020/5/31 21:36
 */
public class GenerateByMySQLHibernate extends AbstractGeneratorSQL implements Generator {

    @Override
    protected String conditionHandel(String replacement) {
        if (replacement.startsWith("LIKE")){
            return replacement.replaceAll("\\|","");
        }
        return replacement;
    }

    @Override
    protected String placeholderParam(String queryCode) {
        return ":"+queryCode+" ";
    }
}
