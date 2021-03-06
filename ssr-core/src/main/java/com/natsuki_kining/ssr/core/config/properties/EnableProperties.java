package com.natsuki_kining.ssr.core.config.properties;

import lombok.Getter;

/**
 * 开关配置参数类
 *
 * @Author : natsuki_kining
 * @Date : 2020/8/28 23:20
 */
@Getter
public class EnableProperties {

    /**
     * 是否开启根据实体生成SQL
     */
    private boolean generateByEntity = false;

    /**
     * 是否开启根据表名生成SQL
     */
    private boolean generateByTable = false;

    /**
     * 是否开启打印查询信息
     */
    private boolean showQueryInfo = true;

    /**
     * 是否开启多数据源
     */
//    private boolean multiDataSourceOpen = false;
}
