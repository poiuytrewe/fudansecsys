package com.fudanuniversity.cms.business.vo.study.plan;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@ToString
public class CmsStudyScoreAddVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    private Integer year;

    @NotNull
    private Integer season;

    @NotNull
    private String stuId;

    @NotNull
    private String stuName;

    @NotNull
    private Integer stuType;

    @NotNull
    private String comment;

    private Integer score;

    @NotNull
    private String mentor;
}
