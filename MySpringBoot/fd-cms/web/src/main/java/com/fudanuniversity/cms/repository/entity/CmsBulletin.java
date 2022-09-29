package com.fudanuniversity.cms.repository.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 通知
 * <p>
 * Created by Xinyue.Tang at 2021-05-02
 */
@Data
@NoArgsConstructor
@ToString
public class CmsBulletin implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字段备注:id <p>
     * 数据库字段长度:(19,0) <p>
     * 是否索引:是
     */
    private Long id;

    private String stuId;

    private String title;

    private String content;

    private Integer isRead;

    private Date createTime;

    /**
     * 字段备注:更新时间 <p>
     * 数据库字段长度:(19,0) <p>
     * 是否索引:不是
     */
    private Date modifyTime;
}

