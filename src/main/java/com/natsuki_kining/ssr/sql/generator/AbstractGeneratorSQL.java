package com.natsuki_kining.ssr.sql.generator;

import com.natsuki_kining.ssr.annotation.TableName;
import com.natsuki_kining.ssr.beans.QueryParams;
import com.natsuki_kining.ssr.beans.QueryRule;
import com.natsuki_kining.ssr.enums.QueryCodeType;
import com.natsuki_kining.ssr.exception.SSRException;
import com.natsuki_kining.ssr.utils.Constant;
import com.natsuki_kining.ssr.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.python.tests.InterfaceCombination;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
        if (queryParams.getParams() != null && queryParams.getParams().size() > 0){
            querySql.append(" T1 WHERE 1=1 ");
            if ("hibernate".equals(ormType)){
                queryParams.getParams().forEach((k,v)->{
                    querySql.append("AND ");
                    querySql.append(StringUtils.castFieldToColumn(k));
                    querySql.append(" = :");
                    querySql.append(k);
                    querySql.append(" ");
                });
            }else{
                queryParams.getParams().forEach((k,v)->{
                    querySql.append("AND ");
                    querySql.append(StringUtils.castFieldToColumn(k));
                    querySql.append(" = #{");
                    querySql.append(k);
                    querySql.append("} ");
                });
            }
        }
        return querySql.toString();
    }

    @Override
    public void generateSortSQL(StringBuilder stringBuilder, QueryRule queryRule, QueryParams queryParams) {
        if (queryParams.getSort() == null || queryParams.getSorts() == null){
            return;
        }
    }


}
