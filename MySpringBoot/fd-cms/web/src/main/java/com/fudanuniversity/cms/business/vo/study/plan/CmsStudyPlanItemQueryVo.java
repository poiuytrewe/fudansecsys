package com.fudanuniversity.cms.business.vo.study.plan;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * 培养方案分配
 *
 * Created by Xinyue.Tang at 2021-05-05 20:04:58
 */
@Data
@NoArgsConstructor
@ToString
public class CmsStudyPlanItemQueryVo {

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
     * 培养方案任务预计开始日期
     */
    private Date planWorkExpectStartTime;

    /**
     * 培养方案任务预计截止日期
     */
    private Date planWorkExpectEndTime;

    /**
     * 是否完成
     */
    private Integer finished;

    /**
     * 任务完成日期
     */
    private Date finishedDate;

    /**
     * 备注
     */
    private String remark;

    /**
     * 小于等于创建时间
     */
    private Date eltCreateTime;

    /**
     * 大于等于创建时间
     */
    private Date egtCreateTime;

    /**
     * 小于等于更新时间
     */
    private Date eltModifyTime;

    /**
     * 大于等于更新时间
     */
    private Date egtModifyTime;
}