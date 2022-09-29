package com.fudanuniversity.cms.business.vo.device;

import com.fudanuniversity.cms.commons.enums.DeviceTypeEnum;
import com.fudanuniversity.cms.commons.validation.constraints.EnumValue;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;


/**
 * 设备
 * <p>
 * Created by Xinyue.Tang at 2021-05-03
 */
@Data
@NoArgsConstructor
@ToString
public class CmsDeviceAddVo implements Serializable {//CmsDeviceAddVo已改

    private static final long serialVersionUID = 1L;

    /**
     * 设备类型
     */
    @NotNull
    @EnumValue(enumClass = DeviceTypeEnum.class, property = "code")
    private Long type;

    /**
     * 设备型号
     */
    @NotEmpty
    private String model;

    /**
     * 设备名称
     */
    private String name;

    /**
     * 负责人姓名
     */
    private String principal;

    private String borrow;//借用人
}

