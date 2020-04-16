package com.natsuki_kining.ssr.beans;

import lombok.Data;

import java.util.List;

/**
 * TODO
 *
 * @Author natsuki_kining
 * @Date 2020/4/16 20:02
 **/
@Data
public class QueryResult<T> {

    private List<T> data;
    private int count;
    private int pageSize;
    private int pageNo;

}
