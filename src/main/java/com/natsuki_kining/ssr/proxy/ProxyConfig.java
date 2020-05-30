package com.natsuki_kining.ssr.proxy;

import com.natsuki_kining.ssr.annotation.QueryCode;
import com.natsuki_kining.ssr.enums.ORMType;
import com.natsuki_kining.ssr.intercept.AbstractQueryJavaIntercept;
import com.natsuki_kining.ssr.intercept.AbstractQueryScriptIntercept;
import com.natsuki_kining.ssr.rule.Rule;
import com.natsuki_kining.ssr.sql.SQL;
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

    /**
     * 获取orm类型
     *
     * @return ORMType
     */
    ORMType getORMType() {
        if (ORMType.HIBERNATE.getType().equals(ormType)) {
            return ORMType.HIBERNATE;
        } else if (ORMType.MYBATIS.getType().equals(ormType)) {
            return ORMType.MYBATIS;
        } else {
            return ORMType.USER_DEFINED;
        }
    }

    @Value("${ssr.orm.type}")
    private String ormType;

    @Autowired
    private SQL sql;

    @Autowired
    private Rule rule;

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
