package com.natsuki_kining.ssr.mybatis.config.multisource;

import com.natsuki_kining.ssr.core.config.multisource.AbstractDynamicSqlSession;
import com.natsuki_kining.ssr.core.config.multisource.DataSourceContextHolder;
import com.natsuki_kining.ssr.mybatis.beans.MyBatisSelectInfo;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @Author : natsuki_kining
 * @Date : 2020/9/4 23:26
 */
@Component
public class DynamicSqlSession extends AbstractDynamicSqlSession {

    @Autowired
    private ApplicationContext appContext;

    @Override
    public SqlSession getSqlSession(){
        String sessionFactoryName = DataSourceContextHolder.getDataSourceType();
        SqlSessionFactory bean = appContext.getBean(sessionFactoryName, SqlSessionFactory.class);
        return bean.openSession();
    }

    public MyBatisSelectInfo getMyBatisSelectInfo(){
        String sessionFactoryName = DataSourceContextHolder.getDataSourceType();
        SqlSessionFactory bean = appContext.getBean(sessionFactoryName, SqlSessionFactory.class);
        SqlSession sqlSession = bean.openSession();
        Configuration configuration = sqlSession.getConfiguration();
        LanguageDriver languageDriver = configuration.getDefaultScriptingLanguageInstance();
        MyBatisSelectInfo myBatisSelectInfo = new MyBatisSelectInfo(sqlSession,configuration,languageDriver);
        return myBatisSelectInfo;
    }

}
