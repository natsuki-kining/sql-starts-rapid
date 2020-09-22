package com.natsuki_kining.ssr.core.sql.generator;

import com.natsuki_kining.ssr.core.annotation.TableName;
import com.natsuki_kining.ssr.core.beans.QueryCondition;
import com.natsuki_kining.ssr.core.beans.QueryParams;
import com.natsuki_kining.ssr.core.beans.QueryRule;
import com.natsuki_kining.ssr.core.enums.QueryCodeType;
import com.natsuki_kining.ssr.core.exception.SSRException;
import com.natsuki_kining.ssr.core.utils.CollectionUtils;
import com.natsuki_kining.ssr.core.utils.Constant;
import com.natsuki_kining.ssr.core.utils.MapUtils;
import com.natsuki_kining.ssr.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 生成sql
 *
 * @Author : natsuki_kining
 * @Date : 2020/5/30 14:19
 */
@Slf4j
public abstract class AbstractGeneratorSQL implements Generator {

    @Override
    public String generateQuerySQL(QueryRule queryRule, QueryParams queryParams) {
        StringBuilder querySql = new StringBuilder("SELECT ");
        querySql.append(queryParams.getSelectFields());
        querySql.append(" FROM ");
        if (queryRule.getQueryCodeType() == QueryCodeType.GENERATE_QUERY_BY_ENTITY) {
            Class<?> clazz;

            try {
                clazz = Class.forName(queryRule.getQueryCode());
            } catch (ClassNotFoundException e) {
                throw new SSRException(e.getMessage(), e);
            }
            String tableName;
            if (clazz.isAnnotationPresent(TableName.class)) {
                TableName tableNameAnnotation = clazz.getAnnotation(TableName.class);
                tableName = tableNameAnnotation.value();
            } else {
                tableName = clazz.getSimpleName();
            }
            querySql.append(tableName);
        } else if (queryRule.getQueryCodeType() == QueryCodeType.GENERATE_QUERY_BY_TABLE) {
            querySql.append(queryRule.getQueryCode());
        }
        //处理查询条件
        generateWhereSQL(querySql, queryRule, queryParams);
        return querySql.toString();
    }

    /**
     * 生成where条件
     *
     * @param querySql    sql StringBuilder
     * @param queryRule   查询规则
     * @param queryParams 查询参数
     */
    protected void generateWhereSQL(StringBuilder querySql, QueryRule queryRule, QueryParams queryParams) {
        if (MapUtils.isEmpty(queryParams.getParams()) && CollectionUtils.isEmpty(queryParams.getCondition())) {
            return;
        }
        querySql.append(" T1 WHERE 1=1 ");

        //如果有自定义的查询条件，则按自定义的规则来生成，没有则使用默认生成规则
        if (CollectionUtils.isEmpty(queryParams.getCondition())) {
            queryParams.getParams().keySet().stream().forEach(k -> {
                querySql.append("AND T1.");
                querySql.append(StringUtils.castFieldToColumn(k));
                querySql.append(" = ");
                querySql.append(placeholderParam(k));
                querySql.append(" ");
            });
        } else {
            Map<String, Object> params = new HashMap<>(queryParams.getCondition().size());
            queryParams.setParams(params);
            List<QueryCondition> conditionList = queryParams.getCondition();
            Map<String, List<QueryCondition>> queryConditionMap = new HashMap<>(conditionList.size());
            conditionList.stream().forEach(c -> {
                String groupId = "";
                if (StringUtils.isBlank(groupId)) {
                    groupId = UUID.randomUUID().toString();
                }
                if (queryConditionMap.get(groupId) == null) {
                    queryConditionMap.put(groupId, new ArrayList<>());
                }
                queryConditionMap.get(groupId).add(c);
            });


            AtomicInteger index = new AtomicInteger(0);
            queryConditionMap.forEach((k, v) -> {
                String paramName = "param";
                if (v.size() == 1) {
                    index.getAndIncrement();
                    QueryCondition c = v.get(0);
                    queryConditionHandle(querySql, c, params, paramName + index.get());
                } else {
                    querySql.append(StringUtils.getInListValue(Constant.Condition.QUERY_CONNECT_LIST, v.get(0).getLogicalOperator(), Constant.Condition.DEFAULT_CONNECT));
                    querySql.append(" ( ");
                    v.stream().forEach(c -> {
                        index.getAndIncrement();
                        queryConditionHandle(querySql, c, params, paramName + index.get());
                    });
                    querySql.append(" ) ");
                }
            });
        }
    }

    /**
     * 生成排序
     *
     * @param querySql    sql StringBuilder
     * @param queryRule   查询规则
     * @param queryParams 查询参数
     */
    public void generateSortSQL(StringBuilder querySql, QueryRule queryRule, QueryParams queryParams) {
        if (queryParams.getSort() == null) {
            return;
        }
        if (!queryParams.isGenerateSort()) {
            return;
        }
        querySql.append(" ORDER BY ");
        Set<Map.Entry<String, String>> sortSet = queryParams.getSort().entrySet();
        sortSet.stream().forEach(e -> querySql.append(StringUtils.castFieldToColumn(e.getKey()) + " " + e.getValue().toUpperCase() + ","));
        querySql.deleteCharAt(querySql.length() - 1);
    }

    private void queryConditionHandle(StringBuilder querySql, QueryCondition queryCondition, Map<String, Object> params, String paramName) {
        String fieldName = queryCondition.getFieldName();
        Object value = queryCondition.getValue();
        String logicalOperator = queryCondition.getLogicalOperator();
        String relationalOperator = queryCondition.getRelationalOperator();

        params.put(paramName, value);

        logicalOperator = StringUtils.getInListValue(Constant.Condition.QUERY_CONNECT_LIST, logicalOperator, Constant.Condition.DEFAULT_CONNECT);
        if (Constant.Condition.QUERY_LIKE_OPERATIONAL_CHARACTER_LIST.contains(relationalOperator)) {
            relationalOperator = likeConditionHandel(relationalOperator, placeholderParam(paramName));
        } else {
            relationalOperator = StringUtils.getInListValue(Constant.Condition.QUERY_OPERATIONAL_CHARACTER_LIST, relationalOperator, Constant.Condition.DEFAULT_OPERATIONAL_CHARACTER) + " " + placeholderParam(paramName) + " ";
        }
        querySql.append(logicalOperator);
        querySql.append(" T1.");
        querySql.append(StringUtils.castFieldToColumn(fieldName));
        querySql.append(" ");
        querySql.append(relationalOperator);
        querySql.append(" ");
    }

    /**
     * 处理like查询sql片段
     *
     * @param replacement      like 标识符
     * @param placeholderParam 占位符
     * @return
     */
    protected abstract String likeConditionHandel(String replacement, String placeholderParam);

    /**
     * 查询占位符
     *
     * @param queryCode
     * @return
     */
    protected abstract String placeholderParam(String queryCode);

}
