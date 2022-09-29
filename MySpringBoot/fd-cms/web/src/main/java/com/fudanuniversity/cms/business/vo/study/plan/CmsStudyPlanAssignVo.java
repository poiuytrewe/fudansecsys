package com.fudanuniversity.cms.business.vo.study.plan;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;


/**
 * 培养方案分配
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 17:59:46
 */
@Data
@NoArgsConstructor
@ToString
public class CmsStudyPlanAssignVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 培养方案id
     */
    @NotNull(message = "培养计划id不能为空")
    @Min(1L)
    private Long planId;

    /**
     * 用户id
     */
    @NotEmpty
    private List<Long> userIds;
}

