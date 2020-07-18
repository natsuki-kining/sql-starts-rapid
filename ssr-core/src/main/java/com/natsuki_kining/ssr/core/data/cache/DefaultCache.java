package com.natsuki_kining.ssr.core.data.cache;

import com.natsuki_kining.ssr.core.beans.SSRDynamicSQL;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认缓存
 *
 * @Author natsuki_kining
 * @Date 2020/6/11 19:17
 **/
@Slf4j
@Component
public class DefaultCache implements SSRCache {

    /**
     * 缓存最大数量
     */
    @Value("${ssr.cache.default.length:500}")
    private int maxLength;
    /**
     * 缓存达到最大数时，一次移除不常用的数量
     */
    @Value("${ssr.cache.default.rarely:50}")
    private int rarely;

    private Map<String, Object> cache;
    private List<QueryIndex> queryCountList;

    @PostConstruct
    private void init() {
        cache = new ConcurrentHashMap<>(maxLength);
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
            if (cache.size() == maxLength) {
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
            if (queryCode.equals(q.getQueryCode())) {
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
