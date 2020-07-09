package com.natsuki_kining.ssr.test.controller;

import com.natsuki_kining.ssr.core.beans.QueryParams;
import com.natsuki_kining.ssr.core.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @Author : natsuki_kining
 * @Date : 2020/7/9 22:53
 */
@RestController
@RequestMapping("query")
public class QueryController {

    @Autowired
    private Query query;

    @PostMapping("page")
    public Object page(@RequestBody QueryParams queryParams) throws ClassNotFoundException {
        Class<?> aClass = Class.forName("com.natsuki_kining.ssr.test.entity.SSRUser");
        return this.query.queryResult(queryParams, aClass);
    }
}
