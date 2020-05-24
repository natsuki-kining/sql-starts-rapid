package com.natsuki_kining.ssr.data.dao;

import com.natsuki_kining.ssr.beans.QueryParams;
import com.natsuki_kining.ssr.beans.SSRDynamicSql;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

/**
 * Mybatis 接口
 *
 * @Author natsuki_kining
 * @Date 2020/4/29 20:08
 **/
@Mapper
public interface MyBatisDao extends SSRDao {

    @SelectProvider(type = MyBatisSelectProvider.class,method = "getSql")
    Object select(String sql, QueryParams queryParams);

}
