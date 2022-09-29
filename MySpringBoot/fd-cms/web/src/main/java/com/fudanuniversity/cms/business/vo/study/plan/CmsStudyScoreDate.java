package com.fudanuniversity.cms.business.vo.study.plan;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@ToString

public class CmsStudyScoreDate implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer year;

    private Integer season;
}
