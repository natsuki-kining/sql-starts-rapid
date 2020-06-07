package com.natsuki_kining.ssr.core.beans;

import com.natsuki_kining.ssr.core.utils.Constant;
import lombok.Data;

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
public class QueryParams {

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
     * 自动生成sql where 条件用
     */
    private List<QueryCondition> condition;
    private int pageNo = 1;
    /**
     * pageSize = -1 则查询全部、不进行分页
     */
    private int pageSize = Constant.QueryPage.DEFAULT_PAGE_SIZE;
    private transient int pageEnd;
    private transient int pageStart;

    public int getPageNo() {
        if (pageNo < 1) {
            pageNo = 1;
        }
        return pageNo;
    }

    public int getPageSize() {
        if (pageSize == -1){
            return pageSize;
        }
        if (pageSize < 1) {
            pageSize = 10;
        }
        return pageSize;
    }

    public int getPageEnd() {
        pageEnd = getPageNo() * getPageSize();
        return pageEnd;
    }

    public int getPageStart() {
        pageStart = getPageEnd() - getPageSize();
        return pageStart;
    }

}
