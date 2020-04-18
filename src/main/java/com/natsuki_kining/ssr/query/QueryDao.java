package com.natsuki_kining.ssr.query;

/**
 * 查询数据库接口
 *
 * 默认实现了hibernate跟mybatis，通过配置项ssr.orm.type启用不同的查询实现
 *
 * 如果需要自己实现其他查询方式，继承QueryDao类，重写查询方法即可
 *
 * @Author : natsuki_kining
 * @Date : 2020/4/17 18:00
 */
public interface QueryDao extends Query{



}
