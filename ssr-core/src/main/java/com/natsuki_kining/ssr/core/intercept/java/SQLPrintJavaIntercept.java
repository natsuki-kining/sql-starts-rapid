package com.natsuki_kining.ssr.core.intercept.java;

import com.alibaba.fastjson.JSON;
import com.natsuki_kining.ssr.core.annotation.QueryCode;
import com.natsuki_kining.ssr.core.beans.QueryInfo;
import com.natsuki_kining.ssr.core.beans.QueryParams;
import com.natsuki_kining.ssr.core.beans.SSRDynamicSQL;
import com.natsuki_kining.ssr.core.intercept.AbstractQueryJavaIntercept;
import com.natsuki_kining.ssr.core.utils.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * TODO
 *
 * @Author natsuki_kinig
 * @Date 2020/6/6 17:29
 **/
@Slf4j
@Component
@QueryCode(Constant.Intercept.SSR_LAST_INTERCEPT)
public class SQLPrintJavaIntercept extends AbstractQueryJavaIntercept {

    @Override
    public void queryBefore(QueryParams queryParams, QueryInfo queryInfo, SSRDynamicSQL dynamicSql, Map<String, Object> preData) {
        log.info("SQL:{}",queryInfo.getQuerySQL());
        log.info("params:{}", JSON.toJSONString(queryParams.getParams()));
    }

    @Override
    public Object queryAfter(QueryParams queryParams, QueryInfo queryInfo, SSRDynamicSQL dynamicSql, Map<String, Object> preData, Object queryData) {
        log.info("查询耗时：{}",(queryInfo.getQueryEndTime() - queryInfo.getQueryStartTime()));
        return queryData;
    }
}
