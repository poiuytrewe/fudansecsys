package com.fudanuniversity.cms.repository.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 培养方案任务
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 20:04:58
 */
@Data
@NoArgsConstructor
@ToString
public class CmsStudyPlanWork implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字段备注:id <p>
     * 数据库字段长度:(19,0) <p>
     * 是否索引:是
     */
    private Long id;

    /**
     * 字段备注:培养方案id <p>
     * 数据库字段长度:(19,0) <p>
     * 是否索引:是
     */
    private Long planId;

    /**
     * 字段备注:培养方案阶段id <p>
     * 数据库字段长度:(19,0) <p>
     * 是否索引:是
     */
    private Long planStageId;

    /**
     * 字段备注:任务类型 <p>
     * 数据库字段长度:(10,0) <p>
     * 是否索引:不是
     */
    private Integer workType;

    /**
     * 字段备注:任务序号 <p>
     * 数据库字段长度:(10,0) <p>
     * 是否索引:不是
     */
    private Integer index;

    /**
     * 字段备注:名称 <p>
     * 数据库字段长度:(256,0) <p>
     * 是否索引:不是
     */
    private String name;

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

