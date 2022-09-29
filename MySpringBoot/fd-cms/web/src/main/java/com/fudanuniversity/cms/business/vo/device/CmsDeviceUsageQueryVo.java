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
public class CmsDeviceUsageQueryVo {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 设备类型
     */
    private Long type;

    /**
     * 设备型号
     */
    private String model;

    /**
     * 负责人姓名
     */
    private String principal;

    /**
     * 设备型号
     */
    private String name;

    /**
     * 库存
     */
    private Integer inventory;

    /**
     * 库存单位
     */
    private String inventoryUnit;

    /**
     * 小于等于创建时间
     */
    private Date eltCreateTime;

    /**
     * 大于等于创建时间
     */
    private Date egtCreateTime;

    /**
     * 小于等于更新时间
     */
    private Date eltModifyTime;

    /**
     * 大于等于更新时间
     */
    private Date egtModifyTime;
}