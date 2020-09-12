package com.natsuki_kining.ssr.test.service;

import com.natsuki_kining.ssr.test.entity.SSRUser;
import com.natsuki_kining.ssr.test.mapper.SSRUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户 service 层
 *
 * @Author : natsuki_kining
 * @Date : 2020/5/24 10:34
 */
@Service
public class SSRUserService {

    @Autowired
    private SSRUserMapper userMapper;

    public SSRUser get(Integer id) {
        return userMapper.get(id);
    }

    public Integer insertUser(SSRUser user) {
        return userMapper.insertUser(user);
    }
}
