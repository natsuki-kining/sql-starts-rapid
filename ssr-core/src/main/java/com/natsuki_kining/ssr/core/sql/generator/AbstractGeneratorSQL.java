package com.natsuki_kining.ssr.core.sql.generator;

import com.natsuki_kining.ssr.core.annotation.TableName;
import com.natsuki_kining.ssr.core.beans.QueryParams;
import com.natsuki_kining.ssr.core.beans.QueryRule;
import com.natsuki_kining.ssr.core.enums.QueryCodeType;
import com.natsuki_kining.ssr.core.exception.SSRException;
import com.natsuki_kining.ssr.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;
import java.util.Set;

/**
 * 生成sql
 *
 * @Author : natsuki_kining
 * @Date : 2020/5/30 14:19
 */
@Slf4j
public abstract class AbstractGeneratorSQL implements Generator {

    @Value("${ssr.orm.type}")
    private String ormType;

    @Override
    public String generateQuerySQL(QueryRule queryRule, QueryParams queryParams) {
        StringBuilder querySql = new StringBuilder("SELECT * FROM ");
        if (queryRule.getQueryCodeType() == QueryCodeType.GENERATE_QUERY_BY_ENTITY){
            Class<?> clazz;
            try {
                clazz = Class.forName(queryRule.getQueryCode());
            } catch (ClassNotFoundException e) {
                throw new SSRException(e.getMessage(),e);
            }
            String tableName;
            if (clazz.isAnnotationPresent(TableName.class)){
                TableName tableNameAnnotation = clazz.getAnnotation(TableName.class);
                tableName = tableNameAnnotation.value();
            }else{
                tableName = clazz.getSimpleName();
            }
            querySql.append(tableName);
        }else if (queryRule.getQueryCodeType() == QueryCodeType.GENERATE_QUERY_BY_TABLE){
            querySql.append(queryRule.getQueryCode());
        }
        return querySql.toString();
    }

    @Override
    public void generateWhereSQL(StringBuilder querySql, QueryRule queryRule, QueryParams queryParams) {
        if (queryParams.getParams() != null && queryParams.getParams().size() > 0){
            querySql.append(" T1 WHERE 1=1 ");
            boolean isUseHibernateORM = "hibernate".equals(ormType);
            String conditionSign = " = ";
            queryParams.getParams().keySet().forEach(k->{
                querySql.append("AND T1.");
                querySql.append(StringUtils.castFieldToColumn(k));
                querySql.append(conditionSign);
                if (isUseHibernateORM){
                    querySql.append(":");
                    querySql.append(k);
                    querySql.append(" ");
                }else{
                    querySql.append("#{");
                    querySql.append(k);
                    querySql.append("} ");
                }
            });
        }
    }

    @Override
    public void generateSortSQL(StringBuilder querySql, QueryRule queryRule, QueryParams queryParams) {
        if (queryParams.getSort() == null){
            return;
        }
        querySql.append("ORDER BY ");
        Set<Map.Entry<String, String>> sortSet = queryParams.getSort().entrySet();
        sortSet.forEach(e->querySql.append(StringUtils.castFieldToColumn(e.getKey())+" " + e.getValue() + ""));
    }

    @Override
    public void generatePageSQL(StringBuilder stringBuilder, QueryRule queryRule, QueryParams queryParams) {

    }

}
