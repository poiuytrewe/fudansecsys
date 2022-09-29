package com.fudanuniversity.cms.business.vo.weeklyPublication;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@ToString
public class CmsWeeklyPublicationVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String stuName;

    private String stuId;

    private String target;

    private String outlinedAchievements;

    private String achievements;

    private String plan;

    private String week;

    private Integer score;

    private String comment;

    private String tempComment;

    private Integer isRead;

    private Date date;
}
