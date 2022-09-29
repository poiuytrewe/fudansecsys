package com.fudanuniversity.cms.business.vo.study.plan;

import com.fudanuniversity.cms.commons.enums.StudyPlanAllocationStatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


/**
 * 培养方案
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 17:59:46
 */
@Data
@NoArgsConstructor
@ToString
public class CmsStudyPlanAllocationVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * planId
     */
    private Long planId;

    /**
     * 入学年份
     */
    private Integer enrollYear;

    /**
     * 基准日期
     */
    private Date referenceDate;

    /**
     * 名称
     */
    private String name;

    /**
     * 培养方案状态
     *
     * @see StudyPlanAllocationStatusEnum
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date modifyTime;
}

