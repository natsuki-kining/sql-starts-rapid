package com.natsuki_kining.ssr.test.controller;

import com.natsuki_kining.ssr.core.beans.QueryParams;
import com.natsuki_kining.ssr.core.data.cache.SSRCache;
import com.natsuki_kining.ssr.core.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * controller层
 *
 * @Author : natsuki_kining
 * @Date : 2020/5/23 23:43
 */
@RestController
@RequestMapping("query")
public class QueryController {

    @Autowired
    private Query query;

    @PostMapping("query")
    public Object query(@RequestBody QueryParams queryParams) {
        Object result = this.query.query(queryParams);
        return result;
    }

    @PostMapping("queryList")
    public Object queryList(@RequestBody QueryParams queryParams) {
        Object result = this.query.queryList(queryParams);
        return result;
    }

    @PostMapping("queryUser")
    public Object queryUser(@RequestBody QueryParams queryParams) throws ClassNotFoundException {
        Class<?> aClass = Class.forName("com.natsuki_kining.ssr.test.entity.SSRUser");
        List<?> result = this.query.queryList(queryParams, aClass);
        return result;
    }

    @PostMapping("page")
    public Object page(@RequestBody QueryParams queryParams) throws ClassNotFoundException {
        Class<?> aClass = Class.forName("com.natsuki_kining.ssr.test.entity.SSRUser");
        return this.query.queryResult(queryParams, aClass);
    }


    @Autowired(required = false)
    private SSRCache cache;

    @GetMapping("clean")
    public Object clean(){
        return cache.clean();
    }
}
