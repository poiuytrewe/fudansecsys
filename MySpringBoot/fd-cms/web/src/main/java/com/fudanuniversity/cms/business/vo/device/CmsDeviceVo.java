package com.fudanuniversity.cms.business.vo.device;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


/**
 * 设备
 * <p>
 * Created by Xinyue.Tang at 2021-05-03
 */
@Data
@NoArgsConstructor
@ToString
public class CmsDeviceVo implements Serializable {//已改

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
     * 设备型号
     */
    private String name;

    /**
     * 负责人姓名
     */
    private String principal;

    private String borrow;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date modifyTime;
}

