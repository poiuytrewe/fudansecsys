package com.fudanuniversity.cms.business.service;

import com.fudanuniversity.cms.business.vo.device.CmsDeviceAllocationApplyVo;
import com.fudanuniversity.cms.business.vo.device.CmsDeviceAllocationReturnVo;
import com.fudanuniversity.cms.business.vo.device.CmsDeviceAllocationVo;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;

/**
 * CmsDeviceAllocationService
 * <p>
 * Created by Xinyue.Tang at 2021-05-02
 */
public interface CmsDeviceAllocationService {

    /*  ～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～  */

    /**
     * 设备分配
     */
    void applyDeviceAllocation(Long userId, CmsDeviceAllocationApplyVo allocationApplyVo);

    /**
     * 设备返还
     */
    void returnDeviceAllocation(Long userId, CmsDeviceAllocationReturnVo allocationReturnVo);

    /**
     * 分页查询数据列表
     */
    PagingResult<CmsDeviceAllocationVo> queryPagingResult(Long userId, Paging paging);
}