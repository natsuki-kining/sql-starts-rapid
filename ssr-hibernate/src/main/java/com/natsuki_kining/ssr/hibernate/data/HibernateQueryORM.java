package com.natsuki_kining.ssr.hibernate.data;

import com.alibaba.fastjson.JSON;
import com.natsuki_kining.ssr.core.beans.QueryParams;
import com.natsuki_kining.ssr.core.beans.QuerySQL;
import com.natsuki_kining.ssr.core.data.orm.AbstractQueryORM;
import com.natsuki_kining.ssr.core.data.orm.QueryORM;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Hibernate sql 查询类
 *
 * @Author natsuki_kining
 **/
@Component
public class HibernateQueryORM extends AbstractQueryORM implements QueryORM {

    protected String querySSRDynamicSQL = "SELECT QUERY_CODE \"queryCode\",SQL_TEMPLATE \"sqlTemplate\",BEFORE_SCRIPT \"beforeScript\",AFTER_SCRIPT \"afterScript\" FROM SSR_DYNAMIC_SQL SDS WHERE SDS.QUERY_CODE = :code";

    private String aliasRegex = "^select\\s+[`?\\w+`?\\s+as?\\s+'?\"?\\w+'?\"?\\s{0,n},?]+\\s+from.*$";

    private Map<String, Boolean> aliasMap = new HashMap<>();

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public <E> List<E> selectList(String sql, Map<String, Object> params, Class<E> returnType) {
        Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
        NativeQuery sqlQuery = session.createSQLQuery(sql);
        NativeQuery nativeQuery;
        if (Map.class == returnType) {
            nativeQuery = sqlQuery.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        } else {
            //使用hibernate注解
            if (returnType.isAnnotationPresent(Entity.class)) {
                nativeQuery = sqlQuery.addEntity(returnType);
            } else {
                //判断是否有使用别名，如果使用别名就不转换
                Boolean useAlias = aliasMap.get(sql);
                if (useAlias == null) {
                    useAlias = Pattern.compile(aliasRegex, Pattern.CASE_INSENSITIVE).matcher(sql).matches();
                    aliasMap.put(sql, useAlias);
                }
                if (useAlias) {
                    nativeQuery = sqlQuery.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(returnType));
                } else {
                    //没有使用别名需要实现转换
                    nativeQuery = sqlQuery.unwrap(NativeQueryImpl.class).setResultTransformer(new ResultTransformer() {
                        @Override
                        public Object transformTuple(Object[] values, String[] columns) {
                            Map<String, Object> map = new HashMap<>();
                            int i = 0;
                            for (String column : columns) {
                                map.put(column, values[i++]);
                            }
                            return map;
                        }

                        @Override
                        public List<E> transformList(List list) {
                            List<E> es = JSON.parseArray(JSON.toJSONString(list), returnType);
                            return es;
                        }
                    });
                }
            }
        }
        if (params != null && params.size() > 0) {
            params.forEach((k, v) -> {
                if (sql.contains(":" + k)) {
                    nativeQuery.setParameter(k, v);
                }
            });
        }
        List<E> list = nativeQuery.list();
        return list;
    }



    @Override
    protected String getQuerySSRDynamicSQL() {
        return querySSRDynamicSQL;
    }
}
