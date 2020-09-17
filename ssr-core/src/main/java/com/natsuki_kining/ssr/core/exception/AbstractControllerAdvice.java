package com.natsuki_kining.ssr.core.exception;

import com.natsuki_kining.ssr.core.beans.QueryResult;
import com.natsuki_kining.ssr.core.enums.QueryStatus;
import lombok.extern.slf4j.Slf4j;

/**
 * 全局异常处理
 *
 * @Author : natsuki_kining
 * @Date : 2020/9/17 23:06
 */
@Slf4j
public abstract class AbstractControllerAdvice {

    public QueryResult exceptionHandler(Exception e){
        log.error(e.getMessage(),e);
        return new QueryResult(QueryStatus.INTERNAL_SERVER_ERROR,e.getMessage());
    }
}
