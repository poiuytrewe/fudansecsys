package com.fudanuniversity.cms.repository.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;

/**
 * 演讲
 * <p>
 * Created by Xinyue.Tang at 2021-05-04 20:03:38
 */
@Data
@NoArgsConstructor
@ToString
public class CmsSeminar implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字段备注:id <p>
     * 数据库字段长度:(19,0) <p>
     * 是否索引:是
     */
    private Long id;

    /**
     * 字段备注:演讲用户id <p>
     * 数据库字段长度:(19,0) <p>
     * 是否索引:不是
     */
    /**
     * 字段备注:演讲时间 <p>
     * 数据库字段长度:(10,0) <p>
     * 是否索引:不是
     */
    private Date date;

    private Integer groupId;
    /**
     * 字段备注:演讲主题 <p>
     * 数据库字段长度:(128,0) <p>
     * 是否索引:不是
     */
    private String speakerName;

    private String speakerStuId;

    private byte[] file;

    private String fileName;

    private String fileType;

    private Integer isFile;

    private Date modifyTime;
}

