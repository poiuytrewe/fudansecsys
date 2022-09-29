package com.fudanuniversity.cms.repository.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户帐户
 * <p>
 * Created by Xinyue.Tang at 2021-05-02
 */
@Data
@NoArgsConstructor
@ToString
public class CmsUserAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字段备注:id <p>
     * 数据库字段长度:(19,0) <p>
     * 是否索引:是
     */
    private Long id;

    /**
     * 字段备注:学号 <p>
     * 数据库字段长度:(32,0) <p>
     * 是否索引:是
     */
    private String stuId;

    /**
     * 字段备注:盐 <p>
     * 数据库字段长度:(32,0) <p>
     * 是否索引:不是
     */
    private String salt;

    /**
     * 字段备注:密码 <p>
     * 数据库字段长度:(64,0) <p>
     * 是否索引:不是
     */
    private String password;

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

