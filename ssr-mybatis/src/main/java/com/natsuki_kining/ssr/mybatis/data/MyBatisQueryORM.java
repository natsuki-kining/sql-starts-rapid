package com.natsuki_kining.ssr.mybatis.data;

import com.natsuki_kining.ssr.core.annotation.TableFieldName;
import com.natsuki_kining.ssr.core.beans.QueryParams;
import com.natsuki_kining.ssr.core.beans.QueryResult;
import com.natsuki_kining.ssr.core.beans.QuerySQL;
import com.natsuki_kining.ssr.core.beans.SSRDynamicSQL;
import com.natsuki_kining.ssr.core.data.orm.AbstractQueryORM;
import com.natsuki_kining.ssr.core.data.orm.QueryORM;
import com.natsuki_kining.ssr.core.exception.SSRException;
import com.natsuki_kining.ssr.core.utils.Assert;
import com.natsuki_kining.ssr.core.utils.StringUtils;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * mybatis sql 查询类
 *
 * @Author natsuki_kining
 **/
@Component
@ConditionalOnProperty(prefix = "ssr", name = "orm.type", havingValue = "mybatis")
public class MyBatisQueryORM extends AbstractQueryORM implements QueryORM {

    @Autowired
    private SqlSession sqlSession;

    private Configuration configuration;
    private LanguageDriver languageDriver;

    private String querySSRDynamicSQL = "SELECT QUERY_CODE,SQL_TEMPLATE,BEFORE_SCRIPT,AFTER_SCRIPT FROM SSR_DYNAMIC_SQL SDS WHERE SDS.QUERY_CODE = #{code} limit 1";

    @Override
    public <E> List<E> selectList(QuerySQL querySQL, QueryParams queryParams, Class<E> returnType) {
        return select(querySQL.getExecuteSQL(), queryParams.getParams(), returnType);
    }

    @Override
    public SSRDynamicSQL getSSRDynamicSQL(String code) {
        List<SSRDynamicSQL> list = select(querySSRDynamicSQL, getQuerySSRDynamicSQLParams(code), SSRDynamicSQL.class);
        if (list != null && list.size() > 0) {
            SSRDynamicSQL ssrDynamicSql = list.get(0);
            Assert.isBlank(ssrDynamicSql.getSqlTemplate(), "根据" + code + "查询的sql模板为空，请检查code是否正确。");
            return ssrDynamicSql;
        }
        throw new SSRException("根据" + code + "查询的SSRDynamicSQL结果为空，请检查code是否正确。");
    }

    @PostConstruct
    private void init() {
        configuration = sqlSession.getConfiguration();
        languageDriver = configuration.getDefaultScriptingLanguageInstance();
    }

    /**
     * 查询
     *
     * @param sql        查询sql
     * @param params     查询参数
     * @param resultType 返回类型
     * @param <E>        返回类型泛型
     * @return 查询结果集
     */
    private <E> List<E> select(String sql, Map<String, Object> params, Class<E> resultType) {
        String mapperId = sql;
        if (!configuration.hasStatement(mapperId, false)) {
            List<ResultMap> resultMaps = new ArrayList<>();
            if (Map.class == resultType) {
                resultMaps.add(new ResultMap.Builder(configuration, "id", resultType, new ArrayList<>(0)).build());
            } else {
                addResultMapper(resultMaps, resultType);
            }
            SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, Map.class);
            MappedStatement mappedStatement = new MappedStatement.Builder(configuration, mapperId, sqlSource, SqlCommandType.SELECT)
                    .resultMaps(resultMaps)
                    .build();
            configuration.addMappedStatement(mappedStatement);
        }
        return sqlSession.selectList(mapperId, params);
    }

    private void addResultMapper(List<ResultMap> resultMaps, Class<?> resultType) {
        Field[] fields = resultType.getDeclaredFields();
        List<ResultMapping> resultMappingList = new ArrayList<ResultMapping>(fields.length);
        ResultMapping resultMapping;
        for (Field field : fields) {
            String property = field.getName();
            Class<?> fieldType = field.getType();
            String column;
            if (field.isAnnotationPresent(TableFieldName.class)) {
                TableFieldName tableFieldName = field.getAnnotation(TableFieldName.class);
                column = tableFieldName.value();
            } else {
                column = StringUtils.castFieldToColumn(property);
            }
            resultMapping = new ResultMapping.Builder(configuration, property, column, fieldType).build();
            resultMappingList.add(resultMapping);
        }
        resultMaps.add(new ResultMap.Builder(configuration, "id", resultType, resultMappingList).build());
    }
}
