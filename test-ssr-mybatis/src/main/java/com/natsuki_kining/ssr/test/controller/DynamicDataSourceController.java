package com.natsuki_kining.ssr.test.controller;

import com.natsuki_kining.ssr.core.config.multisource.MultiSourceConfig;
import com.natsuki_kining.ssr.core.config.properties.SSRDruidProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 动态新增数据源测试Controller
 *
 * @Author : natsuki_kining
 * @Date : 2020-10-7
 */
@RestController
@RequestMapping("dynamicDataSource")
public class DynamicDataSourceController {

    @Autowired
    private MultiSourceConfig multiSourceConfig;

    @PostMapping("addDataSource")
    public Object addDataSource(@RequestBody SSRDruidProperties druidProperties){
        return multiSourceConfig.addDataSource(druidProperties);
    }
}
