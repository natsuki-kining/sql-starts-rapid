package com.natsuki_kining.ssr.core.data.cache;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.natsuki_kining.ssr.core.beans.SSRDynamicSQL;
import com.natsuki_kining.ssr.core.data.orm.AbstractQueryORM;
import com.natsuki_kining.ssr.core.data.orm.QueryORM;
import com.natsuki_kining.ssr.core.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * TODO
 *
 * @author natsuki_kining
 * @date 2021/5/3 18:14
 **/
public abstract class AbstractCache implements SSRCache{

    @Value("${ssr.bloom.insertions:1000000}")
    private int insertions;

    @Autowired
    private AbstractQueryORM orm;

    public BloomFilter<String> bloomFilter= BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), insertions);

    protected abstract void initCache();

    /**
     * 初始化数据
     * 布隆过滤器设置值
     */
    @PostConstruct
    private void init(){
        initCache();
        List<SSRDynamicSQL> dynamicSQLList = orm.selectList(orm.getQuerySSRDynamicSQL(true), null, SSRDynamicSQL.class);
        if (CollectionUtils.isNotEmpty(dynamicSQLList)){
            dynamicSQLList.forEach(dynamicSQL -> {
                bloomFilter.put(dynamicSQL.getQueryCode());
                save(dynamicSQL.getQueryCode(),dynamicSQL);
            });
        }
    }

}
