package com.fudanuniversity.cms.repository.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 培养方案分配
 * <p>
 * Created by Xinyue.Tang at 2021-05-07 11:34:54
 */
@Data
@NoArgsConstructor
@ToString
public class CmsStudyPlanAllocation implements Serializable {

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
     * 字段备注:培养方案版本 <p>
     * 数据库字段长度:(19,0) <p>
     * 是否索引:不是
     */
    private Long planVersion;

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

