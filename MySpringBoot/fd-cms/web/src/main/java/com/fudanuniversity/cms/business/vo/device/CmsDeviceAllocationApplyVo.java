package com.fudanuniversity.cms.business.vo.device;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;


/**
 * 设备分配
 * <p>
 * Created by Xinyue.Tang at 2021-05-03
 */
@Data
@NoArgsConstructor
@ToString
public class CmsDeviceAllocationApplyVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 设备id
     */

    @NotNull
    private Long deviceId;

//    /**
//     * 申请使用的库存
//     */
//    @NotNull
//    @Min(1L)
//    private Integer inventoryUsage;
}

