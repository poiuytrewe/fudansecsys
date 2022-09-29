package com.fudanuniversity.cms.business.vo.device;

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
public class CmsDeviceAllocationReturnVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 设备分配ID
     */
    private Long allocationId;
}

