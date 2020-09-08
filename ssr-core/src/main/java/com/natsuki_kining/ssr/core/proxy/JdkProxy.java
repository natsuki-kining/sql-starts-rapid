package com.natsuki_kining.ssr.core.proxy;

import com.natsuki_kining.ssr.core.beans.*;
import com.natsuki_kining.ssr.core.config.InterceptConfig;
import com.natsuki_kining.ssr.core.config.multisource.DataSourceContextHolder;
import com.natsuki_kining.ssr.core.data.cache.SSRCache;
import com.natsuki_kining.ssr.core.data.orm.QueryORM;
import com.natsuki_kining.ssr.core.enums.QueryCodeType;
import com.natsuki_kining.ssr.core.rule.Rule;
import com.natsuki_kining.ssr.core.sql.SQL;
import com.natsuki_kining.ssr.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * jdk代理
 * <p>
 * 处理查询前后的方法
 *
 * @Author natsuki_kining
 * @Date 2020/4/16 20:02
 **/
@Slf4j
@Component
public class JdkProxy implements InvocationHandler, SSRProxy {

    @Autowired
    private Rule rule;

    @Autowired
    private SQL sql;

    @Autowired
    private SSRCache cache;

    @Autowired
    private InterceptConfig interceptConfig;

    private QueryORM target;

    @Override
    public QueryORM getInstance(QueryORM target) {
        this.target = target;
        Class<?> clazz = target.getClass();
        return (QueryORM) Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        QueryParams queryParams = (QueryParams) args[1];
        //调用拦截器的预处理方法判断是否需要往下执行
        boolean preHandle = interceptConfig.preHandle(queryParams);
        if (!preHandle) {
            return null;
        }

        QueryRule queryRule = rule.analysis(queryParams.getQueryCode());
        if (QueryCodeType.EACH_QUERY == queryRule.getQueryCodeType()) {
            Map<String, QueryRule> queryCodeMap = queryRule.getQueryCodeMap();
            Map<String, Object> preDate = new HashMap<>(queryCodeMap.size());
            Object value = null;
            for (Map.Entry<String, QueryRule> entry : queryCodeMap.entrySet()) {
                QueryRule entryValue = entry.getValue();
                args[0] = sql.getQuerySQL(entryValue, queryParams);
                SSRDynamicSQL dynamicSql = entryValue.getDynamicSql();
                DataSourceContextHolder.setDataSourceName(dynamicSql.getDataSourceName());
                value = invoke(method, args, preDate, dynamicSql, queryParams);
                cache.save(dynamicSql.getQueryCode(), dynamicSql);
                preDate.put(entry.getKey(), value);
                DataSourceContextHolder.clearDataSourceName();
            }
            return value;
        } else {
            args[0] = sql.getQuerySQL(queryRule, queryParams);
            SSRDynamicSQL dynamicSql = queryRule.getDynamicSql();
            DataSourceContextHolder.setDataSourceName(dynamicSql.getDataSourceName());
            Object invoke = invoke(method, args, null, dynamicSql, queryParams);
            cache.save(dynamicSql.getQueryCode(), dynamicSql);
            DataSourceContextHolder.clearDataSourceName();
            return invoke;
        }
    }

    private Object invoke(Method method, Object[] args, Map<String, Object> preDate, SSRDynamicSQL dynamicSql, QueryParams queryParams) throws InvocationTargetException, IllegalAccessException {
        //调用拦截器的查询前方法
        QueryInfo queryInfo = new QueryInfo((QuerySQL) args[0]);
        interceptConfig.queryBefore(queryParams, queryInfo, dynamicSql, preDate);

        //设置执行的SQL
        QuerySQL querySQL = queryInfo.getQuerySQL();
        if (StringUtils.isBlank(querySQL.getExecuteSQL())) {
            querySQL.setExecuteSQL(querySQL.getProcessedSQL());
        }
        args[0] = querySQL;

        //转换为指定的类型
        String resultType = dynamicSql.getResultType();
        if (StringUtils.isNotBlank(resultType)) {
            String[] resultMapper = resultType.split(",");
            for (String rm : resultMapper) {
                String[] split = rm.split(":");
                String queryCode = split[0];
                String className = split[1];

                if (dynamicSql.getQueryCode().equals(queryCode)) {
                    try {
                        Class<?> clazz = Class.forName(className);
                        args[2] = clazz;
                    } catch (Exception e) {
                        log.warn(e.getMessage());
                    }
                    break;
                }
            }
        }

        //执行
        queryInfo.setQueryStartTime(System.currentTimeMillis());
        Object queryData = method.invoke(this.target, args);
        queryInfo.setQueryEndTime(System.currentTimeMillis());

        //调用拦截器的查询后方法
        queryData = interceptConfig.queryAfter(queryParams, queryInfo, dynamicSql, preDate, queryData);

        return queryData;
    }

}
