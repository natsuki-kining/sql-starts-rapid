package com.natsuki_kining.ssr.mybatis.data;

import com.natsuki_kining.ssr.core.annotation.FieldName;
import com.natsuki_kining.ssr.core.beans.SSRDynamicSQL;
import com.natsuki_kining.ssr.core.data.orm.AbstractQueryORM;
import com.natsuki_kining.ssr.core.data.orm.QueryORM;
import com.natsuki_kining.ssr.core.utils.StringUtils;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
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
public class MyBatisQueryORM extends AbstractQueryORM implements QueryORM {

    @Autowired
    private SqlSession sqlSession;

    private Configuration configuration;
    private LanguageDriver languageDriver;

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
    @Override
    public <E> List<E> selectList(String sql, Map<String, Object> params, Class<E> resultType) {
        String mapperId = sql;
        if (!configuration.hasStatement(mapperId, false)) {
            //TODO 这里的写法需要优化下，解决第一次加载并发的问题
            synchronized (MyBatisQueryORM.class){
                if (!configuration.hasStatement(mapperId, false)) {
                    List<ResultMap> resultMaps = new ArrayList<>();
                    if (Map.class == resultType) {
                        resultMaps.add(new ResultMap.Builder(configuration, "id", resultType, new ArrayList<>(0)).build());
                    } else {
                        addResultMapper(resultMaps, resultType,configuration);
                    }
                    SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, Map.class);
                    MappedStatement mappedStatement = new MappedStatement.Builder(configuration, mapperId, sqlSource, SqlCommandType.SELECT)
                            .resultMaps(resultMaps)
                            .build();
                    configuration.addMappedStatement(mappedStatement);
                }
            }
        }
        List<E> objects = sqlSession.selectList(mapperId, params);
        return objects;
    }

    private void addResultMapper(List<ResultMap> resultMaps, Class<?> resultType,Configuration configuration) {
        Field[] fields = resultType.getDeclaredFields();
        List<ResultMapping> resultMappingList = new ArrayList<ResultMapping>(fields.length);
        ResultMapping resultMapping;
        for (Field field : fields) {
            String property = field.getName();
            Class<?> fieldType = field.getType();
            String column;
            if (field.isAnnotationPresent(FieldName.class)) {
                FieldName fieldName = field.getAnnotation(FieldName.class);
                column = fieldName.value();
            } else {
                column = StringUtils.castFieldToColumn(property);
            }
            resultMapping = new ResultMapping.Builder(configuration, property, column, fieldType).build();
            resultMappingList.add(resultMapping);
        }
        resultMaps.add(new ResultMap.Builder(configuration, "id", resultType, resultMappingList).build());
    }

    @Override
    public String getQuerySSRDynamicSQL(boolean selectList) {
        String listSQL = "SELECT QUERY_CODE,DATA_SOURCE_NAME,SQL_TEMPLATE,BEFORE_SCRIPT,AFTER_SCRIPT FROM "+ssrProperties.getDynamicSqlTableName();
        if (selectList){
            return listSQL;
        }
        return listSQL+" SDS WHERE SDS.QUERY_CODE = #{code} limit 1";
    }

}
