package com.natsuki_kining.ssr.core.sql.generator;


import com.natsuki_kining.ssr.core.beans.QueryParams;
import com.natsuki_kining.ssr.core.utils.Constant;

import java.util.Map;

/**
 * TODO
 *
 * @Author : natsuki_kining
 * @Date : 2020/5/31 21:36
 */
public class GenerateByOracle extends AbstractGeneratorSQL implements Generator {
    @Override
    protected String getLikeConditionSign(QueryParams queryParams, String k) {
        Map<String, String> conditionSign = queryParams.getConditionSign();
        if (conditionSign == null || conditionSign.size() == 0){
            return null;
        }
        return Constant.MYSQL_CONDITION.get(conditionSign.get(k));
    }
}
