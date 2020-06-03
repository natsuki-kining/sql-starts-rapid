package com.natsuki_kining.ssr.core.beans;

import lombok.Data;

import java.util.Date;

/**
 * 动态sql持久化实体
 *
 * @Author natsuki_kining
 * @Date 2020-4-12 23:03:53
 * @Version 1.0.0
 **/
@Data
public class SSRDynamicSQL {

    public SSRDynamicSQL(String queryCode) {
        this.queryCode = queryCode;
    }

    /**
     * 主键
     */
    private String id;

    /**
     * 查询的code
     * 不允许使用英文逗号“,”跟冒号“:”
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

    /**
     * 是否删除。1：是，0：否
     */
    private Integer delFlag;

    /**
     * 排序编号。正序。
     */
    private Integer orderNum;

    /**
     * 创建人名称
     */
    private String createName;

    /**
     * 创建人id
     */
    private String createId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人名称
     */
    private String updateName;

    /**
     * 修改人id
     */
    private String updateId;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;

}