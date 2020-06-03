package com.natsuki_kining.ssr.test.service;

import com.natsuki_kining.ssr.core.beans.SSRDynamicSQL;
import com.natsuki_kining.ssr.test.mapper.SSRDynamicSQLMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * TODO
 *
 * @Author : natsuki_kining
 * @Date : 2020/5/24 10:21
 */
@Service
public class SSRDynamicSQLService {

    @Autowired
    private SSRDynamicSQLMapper ssrDynamicSqlMapper;

    public int insertSSRDynamicSQL(SSRDynamicSQL dynamicSql) {
        dynamicSql.setId(UUID.randomUUID().toString());
        return ssrDynamicSqlMapper.insertSSRDynamicSQL(dynamicSql);
    }

    public SSRDynamicSQL get(String id) {
        return ssrDynamicSqlMapper.get(id);
    }

}
