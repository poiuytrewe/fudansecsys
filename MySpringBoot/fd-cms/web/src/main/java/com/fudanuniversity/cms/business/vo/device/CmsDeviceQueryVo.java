package com.fudanuniversity.cms.business.vo.device;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * 设备
 *
 * Created by Xinyue.Tang at 2021-05-03
 */
@Data
@NoArgsConstructor
@ToString
public class CmsDeviceQueryVo {//已改

    private static final long serialVersionUID = 1L;

    /**
     * 设备类型
     */
    private Long type;

    /**
     * 设备型号
     */
    private String model;

    /**
     * 设备名称
     */
    private String name;

    /**
     * 负责人姓名
     */
    private String principal;

    private String borrow;

    /**
     * 小于等于创建时间
     */
    private Date eltCreateTime;

    /**
     * 大于等于创建时间
     */
    private Date egtCreateTime;
}