package com.natsuki_kining.ssr.data;

import com.natsuki_kining.ssr.utils.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @Author : natsuki_kining
 * @Date : 2020/5/30 15:12
 */
public abstract class AbstractSSRData implements SSRData {

    protected final String querySSRDynamicSQL = "SELECT * FROM SSR_DYNAMIC_SQL SDS WHERE SDS.QUERY_CODE = #{code}";

    protected Map<String, Object> getQuerySSRDynamicSQLParams(String code) {
        Assert.isBlank(code, "查询的code不能为空！");
        Map<String, Object> params = new HashMap<>(1);
        params.put("code", code);
        return params;
    }

}
