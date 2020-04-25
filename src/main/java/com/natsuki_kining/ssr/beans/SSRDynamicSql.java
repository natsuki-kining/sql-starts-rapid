package com.natsuki_kining.ssr.beans;

import lombok.Data;

/**
 *
 * 动态sql持久化实体
 *
 * @Author natsuki_kining
 * @Date 2020-4-12 23:03:53
 * @Version 1.0.0
 **/
@Data
public class SSRDynamicSql {

	/**
	 * 主键
	 */
	private String id;
    
	/**
	 * 查询的code
	 */
	private String queryCode;
    
	/**
	 * 查询的名称
	 */
	private String queryName;

	/**
	 * 查询的类型
	 */
	private String queryType;
    
	/**
	 * sql模板
	 */
	private String sqlTemplate;

	/**
	 * 预处理脚本
	 */
	private String preScript;

	/**
	 * 查询之前脚本
	 */
	private String beforeScript;

	/**
	 * 查询之后脚本
	 */
	private String afterScript;

	/**
	 * 版本号
	 */
	private Integer version;

}