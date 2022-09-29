package com.fudanuniversity.cms.business.vo.device;

import com.fudanuniversity.cms.commons.enums.DeviceTypeEnum;
import com.fudanuniversity.cms.commons.validation.constraints.EnumValue;
import jakarta.validation.constraints.NotNull;
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
public class CmsDeviceUpdateVo implements Serializable {//已改

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @NotNull
    private Long id;

    /**
     * 设备类型
     */
    @EnumValue(enumClass = DeviceTypeEnum.class, property = "code")
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
     * 负责人姓名s
     */
    private String principal;

    /**
     * 库存
     */

    private String borrow;

    private Date modifyTime;
}

