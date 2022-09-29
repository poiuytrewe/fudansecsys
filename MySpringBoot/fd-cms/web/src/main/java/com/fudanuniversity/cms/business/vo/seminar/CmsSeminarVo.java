package com.fudanuniversity.cms.business.vo.seminar;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


/**
 * 演讲
 * <p>
 * Created by Xinyue.Tang at 2021-05-02
 */
@Data
@NoArgsConstructor

@ToString
public class CmsSeminarVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 演讲用户id
     */

    /**
     * 演讲时间
     */
    private Date date;

    private Integer groupId;

    private String speakerName;

    private String speakerStuId;

    private String fileName;//文件名称

    private Integer isFile;

    private Date modifyTime;
}

