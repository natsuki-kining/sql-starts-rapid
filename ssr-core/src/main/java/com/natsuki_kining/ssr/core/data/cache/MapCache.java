package com.natsuki_kining.ssr.core.data.cache;

import com.natsuki_kining.ssr.core.beans.SSRDynamicSQL;
import com.natsuki_kining.ssr.core.config.properties.SSRProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认缓存
 *
 * @Author natsuki_kining
 * @Date 2020/6/11 19:17
 **/
@Slf4j
@Component
@ConditionalOnProperty(prefix = "ssr", name = "cache.type", havingValue = "mapcache")
public class MapCache implements SSRCache {

    @Autowired
    private SSRProperties ssrProperties;

    /**
     * 缓存达到最大数时，一次移除不常用的数量
     */
    @Value("${ssr.cache.default.rarely:50}")
    private int rarely;

    private Map<String, Object> cache;
    private List<QueryIndex> queryCountList;

    @PostConstruct
    private void init() {
        cache = new ConcurrentHashMap<>(ssrProperties.getCache().getMaxLength());
        queryCountList = new ArrayList<>();
    }

    @Override
    public <T> T get(String code, Class<T> clazz) {
        reSetQueryCount(code);
        return (T) cache.get(code);
    }

    @Override
    public boolean save(String code, Object object) {
        try {
            //移除不常用
            if (cache.size() == ssrProperties.getCache().getMaxLength()) {
                Iterator<QueryIndex> iterator = queryCountList.iterator();
                int removeCount = 0;
                while (iterator.hasNext()) {
                    removeCount++;
                    QueryIndex q = iterator.next();
                    cache.remove(q.getQueryCode());
                    iterator.remove();
                    if (removeCount == rarely) {
                        break;
                    }
                }
            }
            cache.put(code, object);
            reSetQueryCount(code);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean delete(String queryCode) {
        deleteQueryCountList(queryCode);
        cache.remove(queryCode);
        return true;
    }

    @Override
    public SSRDynamicSQL getSSRDynamicSQL(String queryCode) {
        return get(queryCode, SSRDynamicSQL.class);
    }

    @Override
    public boolean clean() {
        cache.clear();
        return true;
    }

    @Data
    private class QueryIndex {
        public QueryIndex(Integer count, String queryCode) {
            this.count = count;
            this.queryCode = queryCode;
        }

        private Integer count;
        private String queryCode;
    }

    private void reSetQueryCount(String queryCode) {
        Integer count = deleteQueryCountList(queryCode);
        Integer index = count == 0 ? 0 : queryCountList.size();
        queryCountList.add(index, new QueryIndex(count, queryCode));
    }

    private Integer deleteQueryCountList(String queryCode) {
        QueryIndex queryIndex = null;
        Integer count = -1;
        for (QueryIndex q : queryCountList) {
            if (q != null && queryCode.equals(q.getQueryCode())) {
                queryIndex = q;
                count = q.getCount();
                break;
            }
        }
        if (queryIndex != null) {
            queryCountList.remove(queryIndex);
        }
        return ++count;
    }


}
