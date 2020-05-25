package com.natsuki_kining.ssr.test.project.controller;

import com.natsuki_kining.ssr.beans.QueryParams;
import com.natsuki_kining.ssr.beans.SSRDynamicSql;
import com.natsuki_kining.ssr.query.Query;
import com.natsuki_kining.ssr.test.project.service.SSRDynamicSqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * TODO
 *
 * @Author : natsuki_kining
 * @Date : 2020/5/23 23:43
 */
@RestController
@RequestMapping("dynamicSql")
public class SSRDynamicSqlController {

    @Autowired
    private SSRDynamicSqlService dynamicSqlService;

    @Autowired
    private Query query;

    @GetMapping("get/{id}")
    public SSRDynamicSql get(@PathVariable("id") String id){
        return dynamicSqlService.get(id);
    }

    @PostMapping("insert")
    public Integer insert(@RequestBody SSRDynamicSql dynamicSql){
        return dynamicSqlService.insertUser(dynamicSql);
    }

    @PostMapping("query")
    public Object query(@RequestBody QueryParams queryParams){
        return query.query(queryParams);
    }
}
