package com.fudanuniversity.cms.business.vo.weeklyPublication;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@ToString
public class CmsWeeklyPublicationForBossVo {
    private static final long serialVersionUID = 1L;


    private Long id;

    private String stuName;

    private String stuId;

    private Integer type;//学生类型

    private Integer isMentor;//判断是不是导师，如果是，值为1，否则为0

    private String target;

    private String outlinedAchievements;

    private String achievements;

    private String plan;

    private Integer score;

    private String comment;

    private Date date;
}
