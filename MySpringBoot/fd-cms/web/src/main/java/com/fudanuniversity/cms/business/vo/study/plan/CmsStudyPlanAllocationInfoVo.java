package com.fudanuniversity.cms.business.vo.study.plan;

import com.fudanuniversity.cms.commons.enums.StudyPlanAllocationStatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


/**
 * 培养方案分配
 * <p>
 * Created by Xinyue.Tang at 2021-05-07 11:39:06
 */
@Data
@NoArgsConstructor
@ToString
public class CmsStudyPlanAllocationInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;
    private String userStuId;
    private String userName;

    /**
     * 培养方案id
     */
    private Long planId;

    /**
     * 培养方案状态
     *
     * @see StudyPlanAllocationStatusEnum
     */
    private Integer status;

    /**
     *
     */
    private CmsStudyPlanItemInfoVo info;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date modifyTime;
}

