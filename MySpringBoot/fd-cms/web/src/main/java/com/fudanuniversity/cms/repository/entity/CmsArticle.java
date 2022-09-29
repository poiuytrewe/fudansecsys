package com.fudanuniversity.cms.repository.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 文章
 * <p>
 * Created by Xinyue.Tang at 2021-05-04 14:15:26
 */
@Data
@NoArgsConstructor
@ToString
public class CmsArticle implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字段备注:id <p>
     * 数据库字段长度:(19,0) <p>
     * 是否索引:是
     */
    private Long id;

    /**
     * 字段备注:分类id <p>
     * 数据库字段长度:(19,0) <p>
     * 是否索引:是
     */
    private Date date;

    /**
     * 字段备注:名称 <p>
     * 数据库字段长度:(128,0) <p>
     * 是否索引:是
     */
    private String title;

    /**
     * 字段备注:内容 <p>
     * 数据库字段长度:(536870911,0) <p>
     * 是否索引:不是
     */
    private String content;

    /**
     * 字段备注:创建时间 <p>
     * 数据库字段长度:(19,0) <p>
     * 是否索引:不是
     */
    private Date createTime;

    /**
     * 字段备注:更新时间 <p>
     * 数据库字段长度:(19,0) <p>
     * 是否索引:不是
     */
    private Date modifyTime;
}

