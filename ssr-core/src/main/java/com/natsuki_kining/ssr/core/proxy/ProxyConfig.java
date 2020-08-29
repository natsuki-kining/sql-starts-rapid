package com.natsuki_kining.ssr.core.proxy;

import com.natsuki_kining.ssr.core.annotation.QueryCode;
import com.natsuki_kining.ssr.core.data.cache.SSRCache;
import com.natsuki_kining.ssr.core.intercept.AbstractQueryJavaIntercept;
import com.natsuki_kining.ssr.core.intercept.AbstractQueryScriptIntercept;
import com.natsuki_kining.ssr.core.rule.Rule;
import com.natsuki_kining.ssr.core.sql.SQL;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

/**
 * 代理类配置
 *
 * @Author natsuki_kining
 **/
@Component
@Slf4j
public class ProxyConfig {

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




}
