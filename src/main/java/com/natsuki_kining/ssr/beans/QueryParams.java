package com.natsuki_kining.ssr.beans;

import lombok.Data;

import java.util.Map;

/**
 * TODO
 *
 * @Author : natsuki_kining
 * @Date : 2020/4/14 0:33
 */
@Data
public class QueryParams {

    String code;
    Map<String,Object> params;
}
