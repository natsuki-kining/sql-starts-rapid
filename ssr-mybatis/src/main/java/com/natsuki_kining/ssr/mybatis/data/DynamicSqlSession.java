package com.natsuki_kining.ssr.mybatis.data;

import com.natsuki_kining.ssr.core.config.multi.AbstractDynamicSqlSession;
import com.natsuki_kining.ssr.core.utils.StringUtils;
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
    public SqlSession getSqlSession(String name){
        if(StringUtils.isBlank(name)){
            throw new RuntimeException("name can't be empty");
        }
        SqlSessionFactory bean = appContext.getBean(name, SqlSessionFactory.class);
        return bean.openSession();
    }

}
