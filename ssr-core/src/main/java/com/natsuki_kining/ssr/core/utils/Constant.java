package com.natsuki_kining.ssr.core.utils;

/**
 * TODO
 *
 * @Author : natsuki_kining
 * @Date : 2020/5/31 0:12
 */
public class Constant {

    /**
     * 查询类型常量
     */
    public interface QueryCodeType {
        String GENERATE_BY_ENTITY = "generateByEntity";
        String GENERATE_BY_TABLE = "generateByTable";
        String SINGLE = "single";
    }

    /**
     * 分页常量
     */
    public interface QueryPage{
        int QUERY_ALL = -1;
        //默认一页10条数据
        int DEFAULT_PAGE_SIZE = 10;
    }

    public interface SQLCondition{
        String AND = "AND ";
        String OR = "OR ";
        String LIKE = "LIKE ";

        String EQ = "= :name ";
        String L_LIKE = "concat('%',:name) ";
        String R_LIKE = "concat(:name,'%') ";
        String A_LIKE = "concat(concat('%',:name),'%') ";
        String LT = "< :name ";
        String LE = "<= :name ";
        String GT = "> :name ";
        String GE = ">= :name ";
    }
}
