package com.natsuki_kining.ssr.test.mapper;

import com.natsuki_kining.ssr.test.entity.SSRUser;

/**
 * user mapper
 *
 * @Author : natsuki_kining
 * @Date : 2020/5/24 10:21
 */
public interface SSRUserMapper {

    /**
     * 插入数据
     *
     * @param user SSRUser
     * @return 影响行数
     */
    int insertUser(SSRUser user);

    /**
     * 根据id查询SSRUser
     *
     * @param id 主键id
     * @return SSRUser
     */
    SSRUser get(int id);
}
