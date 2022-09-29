package com.fudanuniversity.cms.business.vo.study.plan;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * 培养方案阶段
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 20:04:58
 */
@Data
@NoArgsConstructor
@ToString
public class CmsStudyPlanStageQueryVo {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 培养方案id
     */
    private Long planId;

    /**
     * 学期
     */
    private Integer term;

    /**
     * 阶段序号
     */
    private Integer index;

    /**
     * 节点日期
     */
    private Date endDate;
}