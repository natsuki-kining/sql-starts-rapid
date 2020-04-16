package com.natsuki_kining.ssr.test;

import com.natsuki_kining.ssr.beans.QueryParams;
import com.natsuki_kining.ssr.query.Query;

/**
 * TODO
 *
 * @Author natsuki_kining
 * @Date 2020/4/16 20:02
 **/
public class TestQuery {

    private static Query<QueryParams> q;

    public static void main(String[] args) {
        QueryParams queryParams = q.query();
    }
}
