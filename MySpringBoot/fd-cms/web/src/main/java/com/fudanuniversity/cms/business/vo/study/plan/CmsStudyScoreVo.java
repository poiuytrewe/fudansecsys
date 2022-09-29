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
public class CmsStudyScoreVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    private Integer id;

    private Integer year;

    private Integer season;

    private String stuId;

    private String stuName;

    private Integer stuType;

    private String comment;

    private String teaComment;

    private Integer score;

    private String mentor;
}
