package com.natsuki_kining.ssr.core.beans;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 查询结果
 *
 * @Author natsuki_kining
 * @Date 2020/4/16 20:02
 **/
@Data
public class QueryResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<T> data;
    private int count;
    private int pageSize;
    private int pageNo;

}
