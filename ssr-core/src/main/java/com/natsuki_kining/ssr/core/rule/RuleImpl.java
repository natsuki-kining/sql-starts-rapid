package com.natsuki_kining.ssr.core.rule;

import com.natsuki_kining.ssr.core.beans.QueryRule;
import com.natsuki_kining.ssr.core.beans.SSRDynamicSQL;
import com.natsuki_kining.ssr.core.data.SSRData;
import com.natsuki_kining.ssr.core.data.orm.QueryORM;
import com.natsuki_kining.ssr.core.enums.QueryCodeType;
import com.natsuki_kining.ssr.core.exception.SSRException;
import com.natsuki_kining.ssr.core.utils.Assert;
import com.natsuki_kining.ssr.core.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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
    @Qualifier("SSRDataImpl")
    private SSRData data;

    @Value("${ssr.generate-by-entity.enable:false}")
    private boolean generateByEntityEnable;

    @Value("${ssr.generate-by-table.enable:false}")
    private boolean generateByTableEnable;

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
            SSRDynamicSQL dynamicSql = data.getSSRDynamicSQL(queryCode);
            return new QueryRule(queryCode, dynamicSql, queryCodeType, null);
        } else {
            String queryType = queryCode.substring(index + 1);
            if (Constant.QueryCodeType.GENERATE_BY_ENTITY.equals(queryType)) {
                queryCodeType = QueryCodeType.GENERATE_QUERY_BY_ENTITY;
                Assert.isTrue(generateByEntityEnable,"如果需要使用通过实体来生成sql，请设置ssr.generate-by-entity.enable=true");
            }else if (Constant.QueryCodeType.GENERATE_BY_TABLE.equals(queryType)) {
                queryCodeType = QueryCodeType.GENERATE_QUERY_BY_TABLE;
                Assert.isTrue(generateByTableEnable,"如果需要使用通过实体来生成sql，请设置ssr.generate-by-table.enable=true");
            } else if (Constant.QueryCodeType.SINGLE.equals(queryType)) {
                queryCodeType = QueryCodeType.SINGLE_QUERY;
            } else {
                throw new SSRException(queryCode + "没有指定的查询类型：" + queryType);
            }
            queryCode = queryCode.substring(0, index);
        }
        SSRDynamicSQL dynamicSql = new SSRDynamicSQL(queryCode);
        return new QueryRule(queryCode, dynamicSql, queryCodeType, null);
    }

}
