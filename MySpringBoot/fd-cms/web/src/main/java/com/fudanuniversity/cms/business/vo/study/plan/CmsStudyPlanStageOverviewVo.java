package com.fudanuniversity.cms.business.vo.study.plan;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 培养方案阶段
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 20:04:58
 */
@Data
@NoArgsConstructor
@ToString
public class CmsStudyPlanStageOverviewVo implements Serializable {

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

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date modifyTime;

    private List<CmsStudyPlanWorkOverviewVo> commonWorks;
    private List<CmsStudyPlanWorkOverviewVo> keshuoWorks;
    private List<CmsStudyPlanWorkOverviewVo> academicWorks;
    private List<CmsStudyPlanWorkOverviewVo> synthesizingWorks;
    private List<CmsStudyPlanWorkOverviewVo> technologyWorks;
}

