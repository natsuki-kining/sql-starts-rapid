package com.natsuki_kining.ssr.core.proxy;

import com.natsuki_kining.ssr.core.annotation.QueryCode;
import com.natsuki_kining.ssr.core.data.cache.SSRCache;
import com.natsuki_kining.ssr.core.enums.ORMType;
import com.natsuki_kining.ssr.core.intercept.AbstractQueryJavaIntercept;
import com.natsuki_kining.ssr.core.intercept.AbstractQueryScriptIntercept;
import com.natsuki_kining.ssr.core.rule.Rule;
import com.natsuki_kining.ssr.core.sql.SQL;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 代理类配置
 *
 * @Author natsuki_kining
 **/
@Component
@Slf4j
class ProxyConfig {

    private Map<String, AbstractQueryJavaIntercept> queryJavaInterceptMap = new ConcurrentHashMap<>();

    private AbstractQueryJavaIntercept javaMasterIntercept = null;

    AbstractQueryScriptIntercept getScriptIntercept() {
        return scriptIntercept;
    }

    AbstractQueryJavaIntercept getJavaMasterIntercept() {
        return javaMasterIntercept;
    }

    AbstractQueryJavaIntercept getJavaIntercept(String code) {
        return queryJavaInterceptMap.get(code);
    }

    SQL getSQL() {
        return this.sql;
    }

    Rule getRule() {
        return this.rule;
    }

    SSRCache getCache() {
        return this.cache;
    }

    @Autowired
    private SQL sql;

    @Autowired
    private Rule rule;

    @Autowired
    private SSRCache cache;

    @Autowired(required = false)
    private AbstractQueryScriptIntercept scriptIntercept;

    @Autowired(required = false)
    private List<AbstractQueryJavaIntercept> queryJavaIntercepts;

    @PostConstruct
    private void init() {
        if (queryJavaIntercepts != null && queryJavaIntercepts.size() > 0) {
            for (AbstractQueryJavaIntercept queryJavaIntercept : queryJavaIntercepts) {
                if (queryJavaIntercept.getClass().isAnnotationPresent(QueryCode.class)) {
                    QueryCode annotation = queryJavaIntercept.getClass().getAnnotation(QueryCode.class);
                    queryJavaInterceptMap.put(annotation.value(), queryJavaIntercept);
                } else {
                    if (javaMasterIntercept == null) {
                        javaMasterIntercept = queryJavaIntercept;
                    } else {
                        log.warn("已经存在一个master的java拦截器");
                    }
                }
            }
        }
    }
}
