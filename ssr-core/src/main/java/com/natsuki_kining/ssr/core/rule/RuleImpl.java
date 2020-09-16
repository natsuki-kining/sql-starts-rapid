package com.natsuki_kining.ssr.core.rule;

import com.natsuki_kining.ssr.core.beans.QueryRule;
import com.natsuki_kining.ssr.core.beans.SSRDynamicSQL;
import com.natsuki_kining.ssr.core.config.properties.SSRProperties;
import com.natsuki_kining.ssr.core.data.SSRData;
import com.natsuki_kining.ssr.core.enums.QueryCodeType;
import com.natsuki_kining.ssr.core.exception.SSRException;
import com.natsuki_kining.ssr.core.utils.Assert;
import com.natsuki_kining.ssr.core.utils.Constant;
import com.natsuki_kining.ssr.core.utils.StringUtils;
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

    @Autowired
    private SSRProperties ssrProperties;

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
        String indexFlag = ":";
        QueryCodeType queryCodeType;
        String dataSourceName = null;
        int index = queryCode.indexOf(indexFlag);
        if (index == -1) {
            queryCodeType = QueryCodeType.SINGLE_QUERY;
            SSRDynamicSQL dynamicSql = data.getSSRDynamicSQL(queryCode);
            return new QueryRule(queryCode, dynamicSql, queryCodeType, null);
        } else {
            String[] split = queryCode.split(indexFlag);
            String queryType = null;
            if (split.length>1){
                queryType = split[1];
            }
            if(StringUtils.isBlank(queryType)){
                if (split[0].contains(".")){
                    queryType = Constant.QueryCodeType.GENERATE_BY_ENTITY;
                }else{
                    queryType = Constant.QueryCodeType.GENERATE_BY_TABLE;
                }
            }
            if (Constant.QueryCodeType.GENERATE_BY_ENTITY.equals(queryType)) {
                queryCodeType = QueryCodeType.GENERATE_QUERY_BY_ENTITY;
                Assert.isFalse(ssrProperties.getEnable().isGenerateByEntity(), "如果需要使用通过实体来生成sql，请设置ssr.enable.generate-by-entity=true");
            } else if (Constant.QueryCodeType.GENERATE_BY_TABLE.equals(queryType)) {
                queryCodeType = QueryCodeType.GENERATE_QUERY_BY_TABLE;
                Assert.isFalse(ssrProperties.getEnable().isGenerateByTable(), "如果需要使用通过表名来生成sql，请设置ssr.enable.generate-by-table=true");
            } else if (Constant.QueryCodeType.SINGLE.equals(queryType)) {
                queryCodeType = QueryCodeType.SINGLE_QUERY;
            } else {
                throw new SSRException(queryCode + "没有指定的查询类型：" + queryType);
            }
            queryCode = queryCode.substring(0, index);
            if (split.length >= 3){
                dataSourceName = split[2];
            }
        }
        SSRDynamicSQL dynamicSql = new SSRDynamicSQL(queryCode,dataSourceName);
        return new QueryRule(queryCode, dynamicSql, queryCodeType, null);
    }

}
