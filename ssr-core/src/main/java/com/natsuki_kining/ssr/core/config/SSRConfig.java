package com.natsuki_kining.ssr.core.config;

import com.natsuki_kining.ssr.core.config.properties.SSRProperties;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 配置抽象类
 *
 * @Author : natsuki_kining
 * @Date : 2020/8/29 20:18
 */
public abstract class SSRConfig {

    @Autowired
    protected SSRProperties properties;
}
