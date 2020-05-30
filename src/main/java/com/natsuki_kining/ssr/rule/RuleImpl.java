package com.natsuki_kining.ssr.rule;

import com.natsuki_kining.ssr.beans.QueryRule;
import com.natsuki_kining.ssr.beans.SSRDynamicSQL;
import com.natsuki_kining.ssr.data.dao.QueryORM;
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

    /**
     *     private String queryCode;
     *
     *     private SSRDynamicSQL dynamicSql;
     *
     *     private QueryCodeType queryCodeType;
     *
     *     private Map<String,QueryRule> queryCodeMap;
     *
     */

    @Autowired
    private QueryORM orm;

    @Override
    public QueryRule analysis(String queryCode) {
        if (queryCode.contains(",")){
            Map<String,QueryRule> queryCodeMap = new HashMap<>();
            String regular  = "([^,]+)";
            Pattern pattern = Pattern.compile(regular);
            Matcher matcher = pattern.matcher(queryCode);
            while (matcher.find()){
                String code = matcher.group();
                QueryRule queryRule = getQueryRule(code);
                queryCodeMap.put(queryRule.getQueryCode(),queryRule);
            }
            return new QueryRule(queryCode,null,QueryCodeType.EACH_QUERY,queryCodeMap);
        }else{
            return getQueryRule(queryCode);
        }
    }

    private QueryRule getQueryRule(String queryCode){
        QueryCodeType queryCodeType = null;
        int index = queryCode.indexOf(":");
        if (index == -1){
            queryCodeType = QueryCodeType.SINGLE_QUERY;
            SSRDynamicSQL dynamicSql = orm.getSSRDynamicSQL(queryCode);
            return new QueryRule(queryCode,dynamicSql,queryCodeType,null);
        }else{
            String substring = queryCode.substring(index+1);
            if (Constant.QueryCodeType.GENERATE.equals(substring)){
                queryCodeType = QueryCodeType.GENERATE_QUERY;
            }else if (Constant.QueryCodeType.SINGLE.equals(substring)){
                queryCodeType = QueryCodeType.SINGLE_QUERY;
            }else{
                throw new SSRException(queryCode+"没有指定的查询类型："+substring);
            }
            queryCode = queryCode.substring(0,index);
        }
        SSRDynamicSQL dynamicSql = orm.getSSRDynamicSQL(queryCode);
        return new QueryRule(queryCode,dynamicSql,queryCodeType,null);
    }

    public static void main(String[] args) {
        String a = "com.xxx.entity.UserRole";
        int index = a.indexOf(":");
        System.out.println(a.substring(index+1));
        System.out.println(a.substring(0,index));
    }

}
