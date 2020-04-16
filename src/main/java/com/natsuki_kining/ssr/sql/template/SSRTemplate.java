package com.natsuki_kining.ssr.sql.template;

import com.natsuki_kining.ssr.beans.QueryParams;

import java.io.IOException;

/**
 * TODO
 *
 * @Author natsuki_kining
 * @Date 2020/4/16 20:02
 **/
public interface SSRTemplate {

    String formatSql(String templateSql,QueryParams queryParams);

}
