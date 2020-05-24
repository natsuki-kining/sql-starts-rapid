package com.natsuki_kining.ssr.test.project.mapper;

import com.natsuki_kining.ssr.test.project.entity.SSRUser;

/**
 * TODO
 *
 * @Author : natsuki_kining
 * @Date : 2020/5/24 10:21
 */
public interface SSRUserMapper {

    int insertUser(SSRUser user);

    SSRUser get(int id);
}
