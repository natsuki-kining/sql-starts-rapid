package com.natsuki_kining.ssr.proxy;

import com.natsuki_kining.ssr.annotation.QueryCode;
import com.natsuki_kining.ssr.intercept.QueryJavaIntercept;
import com.natsuki_kining.ssr.intercept.QueryScriptIntercept;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ProxyConfig {

    private Map<String, QueryJavaIntercept> queryJavaInterceptMap = new ConcurrentHashMap<>();

    private QueryJavaIntercept javaMasterIntercept = null;

    QueryScriptIntercept getScriptIntercept(){
        return scriptIntercept;
    }

    QueryJavaIntercept getJavaMasterIntercept(){
        return javaMasterIntercept;
    }

    QueryJavaIntercept getJavaIntercept(String code){
        return queryJavaInterceptMap.get(code);
    }

    @Autowired(required = false)
    private QueryScriptIntercept scriptIntercept;

    @Autowired(required = false)
    private List<QueryJavaIntercept> queryJavaIntercepts;

    @PostConstruct
    private void init(){
        if (queryJavaIntercepts != null && queryJavaIntercepts.size() >0){
            for (QueryJavaIntercept queryJavaIntercept : queryJavaIntercepts) {
                if (queryJavaIntercept.getClass().isAnnotationPresent(QueryCode.class)){
                    QueryCode annotation = queryJavaIntercept.getClass().getAnnotation(QueryCode.class);
                    queryJavaInterceptMap.put(annotation.value(),queryJavaIntercept);
                }else{
                    if (javaMasterIntercept == null){
                        javaMasterIntercept = queryJavaIntercept;
                    }else{
                        log.warn("已经存在一个master的java拦截器");
                    }
                }
            }
        }
    }
}
