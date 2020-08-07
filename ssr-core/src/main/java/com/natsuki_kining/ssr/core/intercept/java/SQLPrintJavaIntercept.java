package com.natsuki_kining.ssr.core.intercept.java;

import com.alibaba.fastjson.JSON;
import com.natsuki_kining.ssr.core.annotation.QueryCode;
import com.natsuki_kining.ssr.core.beans.QueryInfo;
import com.natsuki_kining.ssr.core.beans.QueryParams;
import com.natsuki_kining.ssr.core.beans.SSRDynamicSQL;
import com.natsuki_kining.ssr.core.intercept.AbstractQueryJavaIntercept;
import com.natsuki_kining.ssr.core.utils.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 打印查询信息：例如查询sql，查询参数，查询耗时等
 *
 * @Author natsuki_kinig
 * @Date 2020/6/6 17:29
 **/
@Slf4j
@Component
@QueryCode(Constant.Intercept.SSR_LAST_INTERCEPT)
public class SQLPrintJavaIntercept extends AbstractQueryJavaIntercept {

    @Value("${ssr.show-query-info.enable:true}")
    private boolean showQueryInfo;

    @Override
    public void queryBefore(QueryParams queryParams, QueryInfo queryInfo, SSRDynamicSQL dynamicSql, Map<String, Object> preData) {
        if (showQueryInfo) {
            log.info("SQL:{}", queryInfo.getQuerySQL().getExecuteSQL());
            log.info("params:{}", JSON.toJSONString(queryParams.getParams()));
        }
    }

    @Override
    public Object queryAfter(QueryParams queryParams, QueryInfo queryInfo, SSRDynamicSQL dynamicSql, Map<String, Object> preData, Object queryData) {
        if (showQueryInfo) {
            log.info("查询耗时：{}", (queryInfo.getQueryEndTime() - queryInfo.getQueryStartTime()));
        }
        return queryData;
    }
}
