package com.fudanuniversity.cms.business.vo.study.plan;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@ToString
public class CmsStudyPlanVitalVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    private String stuId;

    @NotNull
    private Integer score;
}
