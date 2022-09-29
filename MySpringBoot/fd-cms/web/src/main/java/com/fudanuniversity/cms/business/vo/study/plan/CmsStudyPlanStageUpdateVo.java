package com.fudanuniversity.cms.business.vo.study.plan;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


/**
 * 培养方案阶段
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 20:04:58
 */
@Data
@NoArgsConstructor
@ToString
public class CmsStudyPlanStageUpdateVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @NotNull
    @Min(1L)
    private Long id;

    /**
     * 节点日期
     */
    @NotNull
    private Date endDate;
}

