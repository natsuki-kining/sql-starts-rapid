package com.natsuki_kining.ssr.core.data.cache;

import com.natsuki_kining.ssr.core.beans.SSRDynamicSQL;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * TODO
 *
 * @Author : natsuki_kining
 * @Date : 2020/9/8 22:20
 */
@Component
@ConditionalOnProperty(prefix = "ssr", name = "cache.type", havingValue = "ehcache")
public class Ehcache implements SSRCache {

    private CacheManager cacheManager;
    private Cache cache;

    @PostConstruct
    private void init(){
        cacheManager = CacheManager.getInstance();
        cache = cacheManager.getCache("queryCodeCache");
    }

    @Override
    public <T> T get(String queryCode, Class<T> clazz) {
        Element element = cache.get(queryCode);
        if(element!=null){
            return (T) element.getObjectValue();
        }
        return null;
    }

    @Override
    public boolean save(String queryCode, Object object) {
        Element element = new Element(queryCode, object);
        cache.put(element);
        return true;
    }

    @Override
    public boolean delete(String queryCode) {
        cache.remove(queryCode);
        return true;
    }

    @Override
    public boolean clean() {
        for (Object key : cache.getKeys()) {
            cache.remove(key);
        }
        return true;
    }

    @Override
    public SSRDynamicSQL getSSRDynamicSQL(String queryCode) {
        return get(queryCode,SSRDynamicSQL.class);
    }
}
