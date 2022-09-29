package com.fudanuniversity.cms.business.vo.recorder;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


/**
 * 演讲记录
 * <p>
 * Created by Xinyue.Tang at 2021-05-03
 */
@Data
@NoArgsConstructor

@ToString
public class CmsRecorderVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 演讲时间
     */
    private Date date;

    private Integer groupId;

    /**
     * 辅读人员1用户id
     */
    private String recorder1Name;

    private String recorder2Name;

    private String summarizerName;

    private byte[] summarizerFile;

    private Integer isSummarizerFile;

    private String summarizerFileName;

    private String recorder;

    private byte[] recorderFile;

    private Integer isRecorderFile;

    private String recorderFileName;

    private Integer baseScore;

    private Integer isBattle;
    /**
     * 更新时间
     */
    private Date modifyTime;
}

