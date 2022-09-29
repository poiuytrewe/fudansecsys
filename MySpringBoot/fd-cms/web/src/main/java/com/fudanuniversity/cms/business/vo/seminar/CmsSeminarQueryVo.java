package com.fudanuniversity.cms.business.vo.seminar;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * 演讲
 * <p>
 * Created by Xinyue.Tang at 2021-05-04
 */
@Data
@NoArgsConstructor

@ToString
public class CmsSeminarQueryVo {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 演讲用户id
     */
    private Integer groupId;
    private String speakerName;
    private String speakerStuId;

    /**
     * 演讲主题

    /**
     * 演讲时间
     */
    private Date date;
}