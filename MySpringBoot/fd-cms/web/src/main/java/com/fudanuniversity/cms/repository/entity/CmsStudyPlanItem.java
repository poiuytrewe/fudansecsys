package com.fudanuniversity.cms.repository.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 培养方案分配
 * <p>
 * Created by Xinyue.Tang at 2021-05-06 16:50:31
 */
@Data
@NoArgsConstructor
@ToString
public class CmsStudyPlanItem implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字段备注:id <p>
     * 数据库字段长度:(19,0) <p>
     * 是否索引:是
     */
    private Long id;

    /**
     * 字段备注:用户id <p>
     * 数据库字段长度:(19,0) <p>
     * 是否索引:是
     */
    private Long userId;

    /**
     * 字段备注:培养方案id <p>
     * 数据库字段长度:(19,0) <p>
     * 是否索引:是
     */
    private Long planId;

    /**
     * 字段备注:培养方案阶段id <p>
     * 数据库字段长度:(19,0) <p>
     * 是否索引:不是
     */
    private Long planStageId;

    /**
     * 字段备注:培养方案任务id <p>
     * 数据库字段长度:(19,0) <p>
     * 是否索引:不是
     */
    private Long planWorkId;

    /**
     * 字段备注:培养方案任务开始日期 <p>
     * 数据库字段长度:(10,0) <p>
     * 是否索引:不是
     */
    private Date planWorkStartDate;

    /**
     * 字段备注:培养方案任务结束日期 <p>
     * 数据库字段长度:(10,0) <p>
     * 是否索引:不是
     */
    private Date planWorkEndDate;

    /**
     * 字段备注:培养方案任务延期天数 <p>
     * 数据库字段长度:(10,0) <p>
     * 是否索引:不是
     */
    private Integer planWorkDelay;

    /**
     * 字段备注:是否完成 <p>
     * 数据库字段长度:(3,0) <p>
     * 是否索引:不是
     */
    private Integer finished;

    /**
     * 字段备注:任务完成日期 <p>
     * 数据库字段长度:(10,0) <p>
     * 是否索引:不是
     */
    private Date finishedDate;

    /**
     * 字段备注:备注 <p>
     * 数据库字段长度:(16383,0) <p>
     * 是否索引:不是
     */
    private String remark;

    /**
     * 字段备注:删除标记 <p>
     * 数据库字段长度:(19,0) <p>
     * 是否索引:不是
     */
    private Long deleted;

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

