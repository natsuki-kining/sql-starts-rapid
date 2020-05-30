package com.natsuki_kining.ssr.test.project.service;

import com.natsuki_kining.ssr.test.project.entity.SSRUser;
import com.natsuki_kining.ssr.test.project.mapper.SSRUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * TODO
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
