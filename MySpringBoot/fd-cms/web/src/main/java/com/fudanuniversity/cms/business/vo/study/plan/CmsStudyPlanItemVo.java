package com.fudanuniversity.cms.business.vo.study.plan;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


/**
 * 培养方案分配
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 20:04:58
 */
@Data
@NoArgsConstructor
@ToString
public class CmsStudyPlanItemVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 培养方案id
     */
    private Long planId;

    /**
     * 培养方案阶段id
     */
    private Long planStageId;

    /**
     * 培养方案任务id
     */
    private Long planWorkId;

    /**
     * 培养方案任务开始日期
     */
    private Date planWorkStartDate;

    /**
     * 培养方案任务结束日期
     */
    private Date planWorkEndDate;

    /**
     * 培养方案任务期望的结束日期（加上延期天数后的结果）
     */
    private Date planWorkExpectedEndDate;

    /**
     * 培养方案任务延期天数
     */
    private Integer planWorkDelay;

    /**
     * 是否完成
     */
    private Integer finished;

    /**
     * 任务完成日期
     */
    private Date finishedDate;

    /**
     * 任务状态
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date modifyTime;
}