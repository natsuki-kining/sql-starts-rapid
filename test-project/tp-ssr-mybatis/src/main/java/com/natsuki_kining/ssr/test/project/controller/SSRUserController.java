package com.natsuki_kining.ssr.test.project.controller;

import com.natsuki_kining.ssr.query.Query;
import com.natsuki_kining.ssr.test.project.entity.SSRUser;
import com.natsuki_kining.ssr.test.project.service.SSRUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * TODO
 *
 * @Author : natsuki_kining
 * @Date : 2020/5/23 23:43
 */
@RestController
@RequestMapping("user")
public class SSRUserController {

    @Autowired
    private SSRUserService userService;

    @Autowired
    private Query query;

    @GetMapping("get/{id}")
    public SSRUser get(@PathVariable("id") Integer id) {
        return userService.get(id);
    }

    @PostMapping("insert")
    public Integer insert(@RequestBody SSRUser user) {
        return userService.insertUser(user);
    }

}
