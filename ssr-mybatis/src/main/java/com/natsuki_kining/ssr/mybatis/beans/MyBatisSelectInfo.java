package com.natsuki_kining.ssr.mybatis.beans;

import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;

/**
 * TODO
 *
 * @Author : natsuki_kining
 * @Date : 2020/9/5 22:40
 */
public class MyBatisSelectInfo {

    public MyBatisSelectInfo(SqlSession sqlSession,
                             Configuration configuration,
                             LanguageDriver languageDriver){
        this.sqlSession = sqlSession;
        this.configuration = configuration;
        this.languageDriver = languageDriver;
    }

    private SqlSession sqlSession;
    private Configuration configuration;
    private LanguageDriver languageDriver;

    public SqlSession getSqlSession() {
        return sqlSession;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public LanguageDriver getLanguageDriver() {
        return languageDriver;
    }
}
