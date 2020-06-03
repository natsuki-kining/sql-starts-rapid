package com.natsuki_kining.ssr.test.controller;

import com.natsuki_kining.ssr.core.beans.QueryParams;
import com.natsuki_kining.ssr.core.beans.SSRDynamicSQL;
import com.natsuki_kining.ssr.core.query.Query;
import com.natsuki_kining.ssr.test.service.SSRDynamicSQLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @Author : natsuki_kining
 * @Date : 2020/5/23 23:43
 */
@RestController
@RequestMapping("dynamicSql")
public class SSRDynamicSQLController {

    @Autowired
    private SSRDynamicSQLService dynamicSqlService;

    @Autowired
    private Query query;

    @GetMapping("get/{id}")
    public SSRDynamicSQL get(@PathVariable("id") String id) {
        return dynamicSqlService.get(id);
    }

    @PostMapping("insert")
    public Integer insert(@RequestBody SSRDynamicSQL dynamicSql) {
        return dynamicSqlService.insertSSRDynamicSQL(dynamicSql);
    }

    @PostMapping("query")
    public Object query(@RequestBody QueryParams queryParams) {
        List<Map> result = this.query.query(queryParams);
        return result;
    }

    @PostMapping("queryUser")
    public Object queryUser(@RequestBody QueryParams queryParams) throws ClassNotFoundException {
        Class<?> aClass = Class.forName("com.natsuki_kining.ssr.test.entity.SSRUser");
        List<?> result = this.query.query(queryParams, aClass);
        return result;
    }
}
