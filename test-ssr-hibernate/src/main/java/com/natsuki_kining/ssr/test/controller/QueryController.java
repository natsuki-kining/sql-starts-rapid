package com.natsuki_kining.ssr.test.controller;

import com.natsuki_kining.ssr.core.beans.QueryParams;
import com.natsuki_kining.ssr.core.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试controller
 *
 * @Author : natsuki_kining
 * @Date : 2020/7/9 22:53
 */
@RestController
@RequestMapping("query")
public class QueryController {

    @Autowired
    private Query query;

    @PostMapping("query")
    public Object query(@RequestBody QueryParams queryParams) {
        return this.query.query(queryParams);
    }
}
