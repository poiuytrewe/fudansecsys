package com.fudanuniversity.cms.repository.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 设备分配
 * <p>
 * Created by Xinyue.Tang at 2021-05-04
 */
@Data
@NoArgsConstructor
@ToString
public class CmsDeviceAllocation implements Serializable {//已改

    private static final long serialVersionUID = 1L;

    /**
     * 字段备注:id <p>
     * 数据库字段长度:(19,0) <p>
     * 是否索引:是
     */
    private Long id;

    /**
     * 字段备注:用户id <p>
     * 数据库字段长度:(19,0) <p>
     * 是否索引:是
     */
    private Long userId;

    /**
     * 字段备注:设备id <p>
     * 数据库字段长度:(19,0) <p>
     * 是否索引:是
     */
    private Long deviceId;

    /**
     * 字段备注:使用库存 <p>
     * 数据库字段长度:(10,0) <p>
     * 是否索引:不是
     */
    private Integer status;

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

