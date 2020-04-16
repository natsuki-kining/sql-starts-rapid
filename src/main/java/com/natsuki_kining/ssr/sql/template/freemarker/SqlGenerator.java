package com.natsuki_kining.ssr.sql.template.freemarker;

import com.natsuki_kining.ssr.beans.QueryParams;
import com.natsuki_kining.ssr.beans.SSRDynamicSqlVO;
import com.natsuki_kining.ssr.exception.SSRException;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @Author natsuki_kining
 * @Date 2020-4-12 23:03:53
 * @Version 1.0.0
 **/
public class SqlGenerator {

    public int queryCount(QueryParams vo) {
        Map<String, Object> queryMap = getQueryMap(vo);
//        return queryCount(queryMap);
        return 0;
    }

    public List<Map<String,Object>> query(QueryParams vo) {
        Map<String, Object> queryMap = getQueryMap(vo);
//        return query(queryMap);
        return null;
    }

    public Map<String,Object> queryPage(QueryParams vo) {
        Map<String, Object> queryMap = getQueryMap(vo);

        int pageEnd = vo.getPageNo() * vo.getPageSize();
        int pageStart =  pageEnd - vo.getPageSize();

        queryMap.put("pageStart",pageStart);
        queryMap.put("pageEnd",pageEnd);

        List<Map<String, Object>> list = null;//queryPage(queryMap);
        int count = queryCount(vo);

        Map<String,Object> data = new HashMap<>();
        data.put("list",list);
        data.put("count",count);
        data.put("pageSize",vo.getPageSize());
        data.put("pageNo",vo.getPageNo());

        return data;
    }

    private Map<String, Object> getQueryMap(QueryParams vo) {
        String sql = getSql(vo);
        Map<String,Object> queryMap = new HashMap();
        queryMap.put("sql",sql);
        if (vo.getParams() != null){
            queryMap.putAll(vo.getParams());
        }
        return queryMap;
    }

    private String getSql(QueryParams vo) {
        try (StringWriter stringWriter = new StringWriter();) {
            SSRDynamicSqlVO mmDepBiVO = new SSRDynamicSqlVO();
            mmDepBiVO.setQueryCode(vo.getCode());
            List<SSRDynamicSqlVO> mmDepBiVOS = null;//queryVOList(mmDepBiVO);
            if (mmDepBiVOS == null || mmDepBiVOS.size() == 0) {
                throw new SSRException("根据queryCode：" + vo.getCode() + "，找不到对应的sql。");
            }
            String querySqlFreemarker = mmDepBiVOS.get(0).getSqlFreemarker();

            String templateName = "query-sql-freemarker";
            Configuration configuration = new Configuration();
            StringTemplateLoader templateLoader = new StringTemplateLoader();
            configuration.setTemplateLoader(templateLoader);
            configuration.setDefaultEncoding("UTF-8");

            Template template = new Template(templateName, querySqlFreemarker, configuration);
            template.process(vo.getParams(), stringWriter);
            stringWriter.flush();
            String querySql = stringWriter.toString();
            String orderBySql = getOrderBy(vo);
            String sql = querySql + orderBySql;
            return sql;
        } catch (Exception e) {
            throw new SSRException("获取Sql失败。", e);
        }
    }

    private String getOrderBy(QueryParams vo){
        Map<String, String> sortMap = vo.getSort();
        if (sortMap.size() == 0){
            return "";
        }
        StringBuilder orderBy = new StringBuilder(" ORDER BY ");
        sortMap.forEach((k,v)->{
            orderBy.append(k);
            orderBy.append(" ");
            orderBy.append(v);
            orderBy.append(",");
        });
        String sort = orderBy.substring(0, orderBy.length() - 1);
        return sort;
    }

}
