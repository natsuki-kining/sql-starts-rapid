package com.natsuki_kining.ssr.core.data.orm;

import com.natsuki_kining.ssr.core.beans.QueryParams;
import com.natsuki_kining.ssr.core.beans.QueryResult;
import com.natsuki_kining.ssr.core.beans.QuerySQL;
import com.natsuki_kining.ssr.core.beans.SSRDynamicSQL;
import com.natsuki_kining.ssr.core.enums.QueryStatus;
import com.natsuki_kining.ssr.core.exception.CodeNotFoundException;
import com.natsuki_kining.ssr.core.utils.Assert;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @Author : natsuki_kining
 * @Date : 2020/5/30 15:12
 */
@Slf4j
public abstract class AbstractQueryORM implements QueryORM {

    protected abstract String getQuerySSRDynamicSQL();

    protected Map<String, Object> getQuerySSRDynamicSQLParams(String code) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("code", code);
        return params;
    }

    @Override
    public SSRDynamicSQL getSSRDynamicSQL(String code) {
        List<SSRDynamicSQL> list = selectList(getQuerySSRDynamicSQL(), getQuerySSRDynamicSQLParams(code), SSRDynamicSQL.class);
        if (list != null && list.size() > 0) {
            SSRDynamicSQL ssrDynamicSql = list.get(0);
            Assert.isBlank(ssrDynamicSql.getSqlTemplate(), "根据" + code + "查询的sql模板为空，请检查code是否正确。");
            return ssrDynamicSql;
        }
        throw new CodeNotFoundException("根据" + code + "查询的SSRDynamicSQL结果为空，请检查code是否正确。");
    }

    @Override
    public <T> QueryResult queryResult(QuerySQL querySQL, QueryParams queryParams, Class<T> clazz) {
        QueryResult queryResult = new QueryResult();
        try {
            Object data;
            if (queryParams.isPageQuery()) {
                Map<String, Object> map = new HashMap<>();
                String countSQL = "SELECT COUNT(1) AS TOTAL FROM (" + querySQL.getSimpleSQL() + " A) B";
                querySQL.setExecuteSQL(countSQL);
                List<Map> maps = selectList(querySQL, queryParams, Map.class);
                int total = Integer.parseInt(maps.get(0).get("TOTAL") + "");
                map.put("count", total);
                if (total != 0) {
                    querySQL.setExecuteSQL(querySQL.getProcessedSQL());
                    map.put("list", selectList(querySQL, queryParams, clazz));
                }
                data = map;
            } else {
                data = selectList(querySQL, queryParams, clazz);
            }
            queryResult.setCode(200).setData(data);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            queryResult.setCode(QueryStatus.INTERNAL_SERVER_ERROR).setMessage(e.getMessage());
        }
        return queryResult;
    }

}
