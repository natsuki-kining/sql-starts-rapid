package com.natsuki_kining.ssr.sql.generator.freemarker;

import com.natsuki_kining.ssr.beans.SSRDynamicQueryVO;
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

    public int queryCount(SSRDynamicQueryVO vo) {
        Map<String, Object> queryMap = getQueryMap(vo);
//        return queryCount(queryMap);
        return 0;
    }

    public List<Map<String,Object>> query(SSRDynamicQueryVO vo) {
        Map<String, Object> queryMap = getQueryMap(vo);
//        return query(queryMap);
        return null;
    }

    public Map<String,Object> queryPage(SSRDynamicQueryVO vo) {
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

    private Map<String, Object> getQueryMap(SSRDynamicQueryVO vo) {
        String sql = getSql(vo);
        Map<String,Object> queryMap = new HashMap();
        queryMap.put("sql",sql);
        if (vo.getQueryData() != null){
            queryMap.putAll(vo.getQueryData());
        }
        return queryMap;
    }

    private String getSql(SSRDynamicQueryVO vo) {
        try (StringWriter stringWriter = new StringWriter();) {
            SSRDynamicSqlVO mmDepBiVO = new SSRDynamicSqlVO();
            mmDepBiVO.setQueryCode(vo.getQueryCode());
            List<SSRDynamicSqlVO> mmDepBiVOS = null;//queryVOList(mmDepBiVO);
            if (mmDepBiVOS == null || mmDepBiVOS.size() == 0) {
                throw new SSRException("根据queryCode：" + vo.getQueryCode() + "，找不到对应的sql。");
            }
            String querySqlFreemarker = mmDepBiVOS.get(0).getSqlFreemarker();

            String templateName = "query-sql-freemarker";
            Configuration configuration = new Configuration();
            StringTemplateLoader templateLoader = new StringTemplateLoader();
            configuration.setTemplateLoader(templateLoader);
            configuration.setDefaultEncoding("UTF-8");

            Template template = new Template(templateName, querySqlFreemarker, configuration);
            template.process(vo.getQueryData(), stringWriter);
            stringWriter.flush();
            String querySql = stringWriter.toString();
            String orderBySql = getOrderBy(vo);
            String sql = querySql + orderBySql;
            return sql;
        } catch (Exception e) {
            throw new SSRException("获取Sql失败。", e);
        }
    }

    private String getOrderBy(SSRDynamicQueryVO vo){
        if (vo.getSortNames().size() == 0){
            return "";
        }
        StringBuilder orderBy = new StringBuilder(" ORDER BY ");
        if (vo.getSortNames().size() == vo.getSortTypes().size()) {
            for(int i = 0,len = vo.getSortNames().size(); i < len; i++) {
                orderBy.append(vo.getSortNames().get(i)+" "+vo.getSortTypes().get(i));
                if (i != len -1){
                    orderBy.append(",");
                }
            }
        } else {
            for(int i = 0,len = vo.getSortNames().size(); i < len; i++) {
                orderBy.append(vo.getSortNames().get(i));
                if (i != len -1){
                    orderBy.append(",");
                }
            }
        }

        return orderBy.toString();
    }
}
