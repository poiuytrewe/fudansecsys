package com.fudanuniversity.cms.repository.entity;

import com.fudanuniversity.cms.commons.validation.constraints.In;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.io.Serializable;

@Data
@NoArgsConstructor
@ToString
public class CmsScore implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer year;

    private Integer season;

    private String stuId;

    private String stuName;

    private Integer stuType;

    private Integer score;

    private String mentor;

    private String comment;

    private String teaComment;

}
