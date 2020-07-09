package com.natsuki_kining.ssr.hibernate.data;

import com.natsuki_kining.ssr.core.beans.QueryParams;
import com.natsuki_kining.ssr.core.beans.QuerySQL;
import com.natsuki_kining.ssr.core.data.orm.AbstractQueryORM;
import com.natsuki_kining.ssr.core.data.orm.QueryORM;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.query.spi.NativeQueryImplementor;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Map;

/**
 * Hibernate sql 查询类
 *
 * @Author natsuki_kining
 **/
@Component
public class HibernateQueryORM extends AbstractQueryORM implements QueryORM {

    protected String querySSRDynamicSQL = "SELECT QUERY_CODE queryCode,SQL_TEMPLATE sqlTemplate,BEFORE_SCRIPT beforeScript,AFTER_SCRIPT afterScript FROM SSR_DYNAMIC_SQL SDS WHERE SDS.QUERY_CODE = :code";

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public <E> List<E> selectList(String sql, Map<String, Object> params, Class<E> returnType) {
        Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
        NativeQuery sqlQuery = session.createSQLQuery(sql);
        NativeQueryImplementor nativeQueryImplementor;
        if (Map.class == returnType){
            nativeQueryImplementor = sqlQuery.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        }else{
            nativeQueryImplementor = sqlQuery.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(returnType));
        }
        if (params != null && params.size() > 0){
            params.forEach((k,v)->{
                nativeQueryImplementor.setParameter(k,v);
            });
        }
        List<E> list = nativeQueryImplementor.list();
        return list;
    }

    @Override
    public <E> List<E> selectList(QuerySQL querySQL, QueryParams queryParams, Class<E> returnType) {
        return selectList(querySQL.getExecuteSQL(),queryParams.getParams(),returnType);
    }

}
