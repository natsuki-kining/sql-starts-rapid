package com.natsuki_kining.ssr.test.controller;

import com.natsuki_kining.ssr.core.config.multisource.DynamicDataSourceHandle;
import com.natsuki_kining.ssr.core.config.properties.SSRDruidProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @Author : natsuki_kining
 * @Date : 2020/10/3 1:14
 */
@RestController
@RequestMapping("dynamicDataSource")
public class DynamicDataSourceController {

    @Autowired
    private DynamicDataSourceHandle dataSourceHandle;

    @PostMapping("addDataSource")
    public Object addDataSource(@RequestBody SSRDruidProperties druidProperties){
        return dataSourceHandle.addDatasource(druidProperties);
    }
}
