package com.fudanuniversity.cms.business.vo.study.plan;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;


/**
 * 培养方案分配
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 20:04:58
 */
@Data
@NoArgsConstructor
@ToString
public class CmsStudyPlanItemInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 培养方案id
     */
    private Long planId;

    /**
     * 总数
     */
    private Long total;

    private Long unfinished;

    private Long regularUnfinished;

    private Long delayUnfinished;

    private Long overtimeUnfinished;

    private Long finished;

    private Long regularFinished;

    private Long delayFinished;

    private Long overtimeFinished;
}

