package com.natsuki_kining.ssr.test.mapper;


import com.natsuki_kining.ssr.core.beans.SSRDynamicSQL;

/**
 * SSRDynamicSQL Mapper
 *
 * @Author : natsuki_kining
 * @Date : 2020/5/24 10:21
 */
public interface SSRDynamicSQLMapper {

    /**
     * 根据id获取 SSRDynamicSql
     *
     * @param id id
     * @return SSRDynamicSql
     */
    SSRDynamicSQL get(String id);

    /**
     * 插入数据
     *
     * @param dynamicSql SSRDynamicSql
     * @return 影响行数
     */
    int insertSSRDynamicSQL(SSRDynamicSQL dynamicSql);

}
