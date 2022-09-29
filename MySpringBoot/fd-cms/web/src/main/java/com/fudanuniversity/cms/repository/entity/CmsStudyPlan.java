package com.fudanuniversity.cms.repository.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 培养方案
 * <p>
 * Created by Xinyue.Tang at 2021-05-07 11:39:06
 */
@Data
@NoArgsConstructor
@ToString
public class CmsStudyPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字段备注:id <p>
     * 数据库字段长度:(19,0) <p>
     * 是否索引:是
     */
    private Long id;

    /**
     * 字段备注:入学年份 <p>
     * 数据库字段长度:(4,0) <p>
     * 是否索引:是
     */
    private Integer enrollYear;

    /**
     * 字段备注:基准日期 <p>
     * 数据库字段长度:(10,0) <p>
     * 是否索引:不是
     */
    private Date referenceDate;

    /**
     * 字段备注:名称 <p>
     * 数据库字段长度:(32,0) <p>
     * 是否索引:不是
     */
    private String name;

    /**
     * 字段备注:版本 <p>
     * 数据库字段长度:(19,0) <p>
     * 是否索引:不是
     */
    private Long version;

    /**
     * 字段备注:创建时间 <p>
     * 数据库字段长度:(19,0) <p>
     * 是否索引:不是
     */
    private Date createTime;

    /**
     * 字段备注:更新时间 <p>
     * 数据库字段长度:(19,0) <p>
     * 是否索引:不是
     */
    private Date modifyTime;
}

