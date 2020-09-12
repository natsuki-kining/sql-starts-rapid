package com.natsuki_kining.ssr.test.controller;

import com.natsuki_kining.ssr.core.beans.SSRDynamicSQL;
import com.natsuki_kining.ssr.test.service.SSRDynamicSQLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * controller层
 *
 * @Author : natsuki_kining
 * @Date : 2020/5/23 23:43
 */
@RestController
@RequestMapping("dynamicSQL")
public class SSRDynamicSQLController {

    @Autowired
    private SSRDynamicSQLService dynamicSqlService;

    @GetMapping("get/{id}")
    public SSRDynamicSQL get(@PathVariable("id") String id) {
        return dynamicSqlService.get(id);
    }

    @PostMapping("insert")
    public Integer insert(@RequestBody SSRDynamicSQL dynamicSql) {
        return dynamicSqlService.insertSSRDynamicSQL(dynamicSql);
    }
}
