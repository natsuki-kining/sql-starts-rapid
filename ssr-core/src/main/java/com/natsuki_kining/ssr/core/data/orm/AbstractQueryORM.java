package com.natsuki_kining.ssr.core.data.orm;

import com.natsuki_kining.ssr.core.data.SSRData;
import com.natsuki_kining.ssr.core.utils.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @Author : natsuki_kining
 * @Date : 2020/5/30 15:12
 */
public abstract class AbstractQueryORM implements QueryORM {

    protected String querySSRDynamicSQL = "SELECT QUERY_CODE,SQL_TEMPLATE,BEFORE_SCRIPT,AFTER_SCRIPT FROM SSR_DYNAMIC_SQL SDS WHERE SDS.QUERY_CODE = #{code}";

    protected Map<String, Object> getQuerySSRDynamicSQLParams(String code) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("code", code);
        return params;
    }

}
