/******************************************************************************
 * Copyright (C) 2019 Dingxin Information Technology Co., Ltd.
 * All Rights Reserved.
 * 本软件为鼎信信息科技有限责任公司开发研制。
 * 未经本公司正式书面同意，其他任何个人、团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/

package com.natsuki_kining.ssr.annotation;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 根据查询code设置拦截器
 * @Author natsuki_kining
 * @Date 2020/5/28 15:12
 **/
@Target({TYPE})
@Retention(RUNTIME)
@Documented
public @interface QueryCode {

    /**
     * 拦截的code值
     * @return
     */
    String value();
}
