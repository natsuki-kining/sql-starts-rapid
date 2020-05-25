package com.natsuki_kining.ssr.test.project.mapper;

import com.natsuki_kining.ssr.beans.SSRDynamicSql;

/**
 * TODO
 *
 * @Author : natsuki_kining
 * @Date : 2020/5/24 10:21
 */
public interface SSRDynamicSqlMapper {

    SSRDynamicSql get(String id);
    
    int insertUser(SSRDynamicSql dynamicSql);

}
