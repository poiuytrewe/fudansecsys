package com.fudanuniversity.cms.business.vo.study.plan;

import com.fudanuniversity.cms.commons.enums.StudyPlanWorkTypeEnum;
import com.fudanuniversity.cms.commons.validation.constraints.EnumValue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;


/**
 * 培养方案任务
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 17:59:46
 */
@Data
@NoArgsConstructor
@ToString
public class CmsStudyPlanWorkAddVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 培养方案阶段id
     */
    @NotNull
    @Min(1L)
    private Long planStageId;

    /**
     * 任务类型
     */
    @NotNull
    @EnumValue(enumClass = StudyPlanWorkTypeEnum.class, property = "code")
    private Integer workType;

    /**
     * 名称
     */
    @NotEmpty
    private String name;
}

