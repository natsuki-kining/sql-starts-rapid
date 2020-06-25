package com.natsuki_kining.ssr.core.beans;

import com.natsuki_kining.ssr.core.utils.Constant;
import lombok.Data;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 查询参数
 *
 * @Author : natsuki_kining
 * @Date : 2020/4/14 0:33
 */
@Data
public class QueryParams implements Serializable {

    private static final long serialVersionUID = 1L;

    private String queryCode;
    /**
     * key->property,
     * value->queryValue
     */
    private Map<String, Object> params;
    /**
     * key->property,
     * value->sort
     */
    private LinkedHashMap<String, String> sort;
    /**
     * 是否生成排序
     */
    private boolean generateSort = true;

    /**
     * 自动生成sql用
     * 自动生成sql where 条件用
     */
    private List<QueryCondition> condition;
    /**
     * 自动生成sql用
     * 查询字段，多个用英文逗号分隔
     */
    private String selectFields = "*";
    private int pageNo = 1;
    /**
     * 是否生成分页
     */
    private boolean generatePage = true;
    /**
     * pageSize = -1 则查询全部、不进行分页
     */
    private int pageSize = Constant.QueryPage.DEFAULT_PAGE_SIZE;

    public int getPageNo() {
        if (pageNo < 1) {
            pageNo = 1;
        }
        return pageNo;
    }

    public int getPageSize() {
        if (pageSize == Constant.QueryPage.QUERY_ALL){
            return pageSize;
        }
        if (pageSize < 1) {
            pageSize = Constant.QueryPage.DEFAULT_PAGE_SIZE;
        }
        return pageSize;
    }

    public int getPageEnd() {
        return getPageNo() * getPageSize();
    }

    public int getPageStart() {
        return getPageEnd() - getPageSize();
    }

    public boolean isPageQuery(){
        if (getPageSize() == Constant.QueryPage.QUERY_ALL){
            return false;
        }
        return generatePage;
    }
}
