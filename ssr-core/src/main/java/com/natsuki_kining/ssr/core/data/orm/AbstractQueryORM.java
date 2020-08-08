package com.natsuki_kining.ssr.core.data.orm;

import com.natsuki_kining.ssr.core.beans.QueryParams;
import com.natsuki_kining.ssr.core.beans.QueryResult;
import com.natsuki_kining.ssr.core.beans.QuerySQL;
import com.natsuki_kining.ssr.core.beans.SSRDynamicSQL;
import com.natsuki_kining.ssr.core.enums.QueryStatus;
import com.natsuki_kining.ssr.core.exception.CodeNotFoundException;
import com.natsuki_kining.ssr.core.exception.SSRException;
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
    public <E> Object select(QuerySQL querySQL, QueryParams queryParams, Class<E> returnType) {
        Object data;
        if (queryParams.isPageQuery()) {
            data = queryPage(querySQL, queryParams, returnType);
        }else{
            data = selectList(querySQL.getExecuteSQL(), queryParams.getParams(), returnType);
        }

        //QueryResult
        if (queryParams.isQueryResultModel()){
            try {
                return new QueryResult().setResult(data).setCode(QueryStatus.OK);
            } catch (IllegalArgumentException e) {
                log.error(e.getMessage(), e);
                return new QueryResult(QueryStatus.BAD_REQUEST, e.getMessage());
            } catch (CodeNotFoundException e) {
                log.error(e.getMessage(), e);
                return new QueryResult(QueryStatus.NOT_FOUND, e.getMessage());
            } catch (SSRException e) {
                log.error(e.getMessage(), e);
                return new QueryResult(QueryStatus.NOT_IMPLEMENTED, e.getMessage());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return new QueryResult(QueryStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            }
        }else{
            return data;
        }
    }

    @Override
    public <T> Map<String, Object> queryPage(QuerySQL querySQL, QueryParams queryParams, Class<T> clazz) {
        Map<String, Object> result = new HashMap<>();
        String countSQL = "SELECT COUNT(1) AS TOTAL FROM (" + querySQL.getSimpleSQL() + " ) B";
        List<Map> maps = selectList(countSQL, queryParams.getParams(), Map.class);
        int total = Integer.parseInt(maps.get(0).get("TOTAL") + "");
        result.put("count", total);
        if (total != 0) {
            result.put("list", selectList(querySQL.getProcessedSQL(), queryParams.getParams(), clazz));
        }
        return result;
    }

}
