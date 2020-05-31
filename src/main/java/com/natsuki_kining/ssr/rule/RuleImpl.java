package com.natsuki_kining.ssr.rule;

import com.natsuki_kining.ssr.beans.QueryRule;
import com.natsuki_kining.ssr.beans.SSRDynamicSQL;
import com.natsuki_kining.ssr.data.orm.QueryORM;
import com.natsuki_kining.ssr.enums.QueryCodeType;
import com.natsuki_kining.ssr.exception.SSRException;
import com.natsuki_kining.ssr.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * rule 实现类
 *
 * @Author : natsuki_kining
 * @Date : 2020/5/30 17:35
 */
@Component
public class RuleImpl implements Rule {

    @Autowired
    private QueryORM orm;

    @Override
    public QueryRule analysis(String queryCode) {
        String eachQueryFlag = ",";
        if (queryCode.contains(eachQueryFlag)) {
            String regular = "([^,]+)";
            Pattern pattern = Pattern.compile(regular);
            Matcher matcher = pattern.matcher(queryCode);
            Map<String, QueryRule> queryCodeMap = new HashMap<>(6);
            while (matcher.find()) {
                String code = matcher.group();
                QueryRule queryRule = getQueryRule(code);
                queryCodeMap.put(queryRule.getQueryCode(), queryRule);
            }
            return new QueryRule(queryCode, null, QueryCodeType.EACH_QUERY, queryCodeMap);
        } else {
            return getQueryRule(queryCode);
        }
    }

    private QueryRule getQueryRule(String queryCode) {
        QueryCodeType queryCodeType;
        int index = queryCode.indexOf(":");
        if (index == -1) {
            queryCodeType = QueryCodeType.SINGLE_QUERY;
            SSRDynamicSQL dynamicSql = orm.getSSRDynamicSQL(queryCode);
            return new QueryRule(queryCode, dynamicSql, queryCodeType, null);
        } else {
            String queryType = queryCode.substring(index + 1);
            if (Constant.QueryCodeType.GENERATE_BY_ENTITY.equals(queryType)) {
                queryCodeType = QueryCodeType.GENERATE_QUERY_BY_ENTITY;
            }else if (Constant.QueryCodeType.GENERATE_BY_TABLE.equals(queryType)) {
                queryCodeType = QueryCodeType.GENERATE_QUERY_BY_TABLE;
            } else if (Constant.QueryCodeType.SINGLE.equals(queryType)) {
                queryCodeType = QueryCodeType.SINGLE_QUERY;
            } else {
                throw new SSRException(queryCode + "没有指定的查询类型：" + queryType);
            }
            queryCode = queryCode.substring(0, index);
        }
        SSRDynamicSQL dynamicSql = orm.getSSRDynamicSQL(queryCode);
        return new QueryRule(queryCode, dynamicSql, queryCodeType, null);
    }

}
