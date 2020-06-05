package com.natsuki_kining.ssr.core.sql.generator;

import com.natsuki_kining.ssr.core.annotation.TableName;
import com.natsuki_kining.ssr.core.beans.QueryCondition;
import com.natsuki_kining.ssr.core.beans.QueryParams;
import com.natsuki_kining.ssr.core.beans.QueryRule;
import com.natsuki_kining.ssr.core.enums.QueryCodeType;
import com.natsuki_kining.ssr.core.enums.QueryConnect;
import com.natsuki_kining.ssr.core.enums.QueryOperationalCharacter;
import com.natsuki_kining.ssr.core.exception.SSRException;
import com.natsuki_kining.ssr.core.utils.BeanUtils;
import com.natsuki_kining.ssr.core.utils.Constant;
import com.natsuki_kining.ssr.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * 生成sql
 *
 * @Author : natsuki_kining
 * @Date : 2020/5/30 14:19
 */
@Slf4j
public abstract class AbstractGeneratorSQL implements Generator {

    protected Map<String, List<QueryCondition>> queryConditionMap;

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
        //处理查询条件
        generateWhereSQL(querySql,queryRule,queryParams);
        //处理排序
        generateSortSQL(querySql,queryRule,queryParams);
        //处理分页
        generatePageSQL(querySql,queryRule,queryParams);
        return querySql.toString();
    }

    /**
     * 生成where条件
     * @param querySql sql StringBuilder
     * @param queryRule 查询规则
     * @param queryParams 查询参数
     */
    protected void generateWhereSQL(StringBuilder querySql, QueryRule queryRule, QueryParams queryParams) {
        if (queryParams.getParams() == null || queryParams.getParams().size() == 0){
            return;
        }
        querySql.append(" T1 WHERE 1=1 ");

        queryConditionHandle(queryParams);
        //如果有自定义的查询条件，则按自定义的规则来生成，没有则使用默认生成规则
        if (queryConditionMap == null){
            queryParams.getParams().keySet().stream().forEach(k->{
                querySql.append("AND T1.");
                querySql.append(StringUtils.castFieldToColumn(k));
                querySql.append("=");
                querySql.append(placeholderParam(k));
                querySql.append(" ");
            });
        }else{
            Set<Map.Entry<String, List<QueryCondition>>> entrySet = queryConditionMap.entrySet();
            entrySet.stream().forEach(m->{
                List<QueryCondition> queryConditionList = m.getValue();
                queryConditionList.stream().forEach(c->{
                    //and / or
                    querySql.append(c.getQueryConnect().value());
                    querySql.append(" T1.");
                    querySql.append(StringUtils.castFieldToColumn(c.getQueryCode()));
                    //运算符 跟参数
                    String character = conditionHandel(c.getOperationalCharacter().value()).replaceAll(Constant.QUERY_PARAMS_NAME,placeholderParam(c.getQueryCode()));
                    querySql.append(character);
                });
            });
        }
    }

    /**
     * 生成排序
     * @param querySql sql StringBuilder
     * @param queryRule 查询规则
     * @param queryParams 查询参数
     */
    protected void generateSortSQL(StringBuilder querySql, QueryRule queryRule, QueryParams queryParams) {
        if (queryParams.getSort() == null){
            return;
        }
        querySql.append("ORDER BY ");
        Set<Map.Entry<String, String>> sortSet = queryParams.getSort().entrySet();
        sortSet.stream().forEach(e->querySql.append(StringUtils.castFieldToColumn(e.getKey())+" " + e.getValue().toUpperCase() + ","));
        querySql.deleteCharAt(querySql.length()-1);
    }

    /**
     * 生成分页
     * @param querySql sql StringBuilder
     * @param queryRule 查询规则
     * @param queryParams 查询参数
     */
    protected void generatePageSQL(StringBuilder querySql, QueryRule queryRule, QueryParams queryParams) {

    }

    /**
     * 处理查询条件
     * @param queryParams
     */
    protected void queryConditionHandle(QueryParams queryParams) {
        Map<String, String> conditionMap = queryParams.getCondition();
        if (conditionMap == null || conditionMap.size() == 0){
            return;
        }
        queryConditionMap = new HashMap<>();
        conditionMap.forEach((k,v)->{
            QueryConnect connect;
            QueryOperationalCharacter operationalCharacter;
            String queryCode;
            String groupId= null;

            //code[:and]
            String[] keys = k.split(":");
            queryCode = keys[0];
            if (keys.length == 2){
                connect = BeanUtils.getQueryConnect(keys[1].toLowerCase());
            }else{
                connect = QueryConnect.AND;
            }

            //al[:groupId]
            String[] values = v.split(":");
            operationalCharacter = BeanUtils.getQueryOperationalCharacter(values[0].toLowerCase());
            if (values.length == 2){
                groupId = values[1];
            }else{
                groupId = UUID.randomUUID().toString();
            }

            QueryCondition queryCondition = new QueryCondition(connect,operationalCharacter,queryCode);
            if (queryConditionMap.get(groupId) == null){
                queryConditionMap.put(groupId,new ArrayList<>());
            }
            queryConditionMap.get(groupId).add(queryCondition);
        });
    }

    protected abstract String conditionHandel(String replacement);

    /**
     * 查询占位符
     * @param queryCode
     * @return
     */
    protected abstract String placeholderParam(String queryCode);

}
