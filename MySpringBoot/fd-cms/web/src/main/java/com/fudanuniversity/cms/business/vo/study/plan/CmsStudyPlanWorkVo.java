package com.fudanuniversity.cms.business.vo.study.plan;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


/**
 * 培养方案任务
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 17:59:46
 */
@Data
@NoArgsConstructor
@ToString
public class CmsStudyPlanWorkVo implements Serializable {

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
     * 培养方案阶段id
     */
	private Long planStageId;

    /**
     * 任务类型
     */
	private Integer workType;

    /**
     * 任务序号
     */
	private Integer index;

    /**
     * 名称
     */
	private String name;

    /**
     * 创建时间
     */
	private Date createTime;

    /**
     * 更新时间
     */
	private Date modifyTime;
}

