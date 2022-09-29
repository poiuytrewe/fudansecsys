package com.fudanuniversity.cms.repository.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fudanuniversity.cms.commons.validation.constraints.In;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 演讲记录安排
 * <p>
 * Created by Xinyue.Tang at 2021-05-03
 */
@Data
@NoArgsConstructor
@ToString
public class CmsRecorder implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Date date;

    private Integer groupId;

    private String recorder1Name;

    private String recorder2Name;

    private String summarizerName;

    private byte[] summarizerFile;

    private String summarizerFileName;

    private String summarizerFileType;

    private Integer isSummarizerFile;

    private String recorder;

    private byte[] recorderFile;

    private String recorderFileName;

    private String recorderFileType;

    private Integer isRecorderFile;

    private Integer baseScore;

    private Integer isBattle;

    private Date modifyTime;

}

