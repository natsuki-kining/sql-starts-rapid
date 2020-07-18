package com.natsuki_kining.ssr.core.utils;

import java.util.ArrayList;
import java.util.List;

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
    public interface QueryPage {
        int QUERY_ALL = -1;
        //默认一页10条数据
        int DEFAULT_PAGE_SIZE = 10;
    }

    public interface Condition {
        String LEFT_LIKE = "ll";
        String RIGHT_LIKE = "rl";
        String ALL_LIKE = "al";

        String DEFAULT_OPERATIONAL_CHARACTER = "=";

        String DEFAULT_CONNECT = "AND";

        List<String> QUERY_CONNECT_LIST = new ArrayList<String>() {{
            add("AND");
            add("OR");
        }};

        List<String> QUERY_OPERATIONAL_CHARACTER_LIST = new ArrayList<String>() {{
            add("=");
            add("<>");
            add("!=");
            add(">");
            add(">=");
            add("<");
            add("<=");
        }};

        List<String> QUERY_LIKE_OPERATIONAL_CHARACTER_LIST = new ArrayList<String>() {{
            add("ll");
            add("rl");
            add("al");
        }};

    }

    public interface Intercept {
        /**
         * 最后一个拦截器的code
         */
        String SSR_LAST_INTERCEPT = "ssr-last-intercept";
    }
}
