package com.fudanuniversity.cms.business.vo.study.plan;

import com.fudanuniversity.cms.commons.enums.BooleanEnum;
import com.fudanuniversity.cms.commons.validation.constraints.EnumValue;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 培养方案分配
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 17:59:46
 */
@Data
@NoArgsConstructor
@ToString
public class CmsStudyPlanItemEditVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @NotNull
    private Long id;

    /**
     * 培养方案任务延迟日期
     */
    private Date delayDate;

    /**
     * 状态
     */
    @EnumValue(enumClass = BooleanEnum.class, property = "code")
    private Integer finished;

    /**
     * 培养方案任务完成日期（管理员更改用户该任务的完成日期）
     */
    private Date finishedDate;

    /**
     * 备注
     */
    private String remark;
}

