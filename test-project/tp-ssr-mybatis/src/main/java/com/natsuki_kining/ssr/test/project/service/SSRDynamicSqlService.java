package com.natsuki_kining.ssr.test.project.service;

import com.natsuki_kining.ssr.beans.SSRDynamicSql;
import com.natsuki_kining.ssr.test.project.mapper.SSRDynamicSqlMapper;
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
public class SSRDynamicSqlService {

    @Autowired
    private SSRDynamicSqlMapper ssrDynamicSqlMapper;
    
    public int insertUser(SSRDynamicSql dynamicSql){
        dynamicSql.setId(UUID.randomUUID().toString());
        return ssrDynamicSqlMapper.insertUser(dynamicSql);
    }
    
    public SSRDynamicSql get(String id){
        return ssrDynamicSqlMapper.get(id);
    }

}
