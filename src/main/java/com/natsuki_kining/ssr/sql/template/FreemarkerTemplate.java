package com.natsuki_kining.ssr.sql.template;

import com.natsuki_kining.ssr.beans.QueryParams;
import com.natsuki_kining.ssr.exception.SSRException;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.StringWriter;

/**
 * Freemarker模板
 *
 * @Author natsuki_kining
 * @Date 2020/4/16 20:02
 **/
public class FreemarkerTemplate implements SSRTemplate {

    @Override
    public String formatSql(String templateSql, QueryParams queryParams) {
        try (StringWriter stringWriter = new StringWriter();) {
            String templateName = "query-sql-freemarker";
            Configuration configuration = new Configuration();
            StringTemplateLoader templateLoader = new StringTemplateLoader();
            configuration.setTemplateLoader(templateLoader);
            configuration.setDefaultEncoding("UTF-8");
            Template template = new Template(templateName, templateSql, configuration);
            template.process(queryParams.getParams(), stringWriter);
            stringWriter.flush();
            String querySql = stringWriter.toString();
            return querySql;
        } catch (Exception e) {
            throw new SSRException("freemarker 处理sql模板失败。", e);
        }
    }
}
