package com.fudanuniversity.cms.business.service;

import com.fudanuniversity.cms.business.vo.device.*;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;

/**
 * CmsDeviceService
 * <p>
 * Created by Xinyue.Tang at 2021-05-02
 */
public interface CmsDeviceService {

    /**
     * 保存处理
     */
    void saveCmsDevice(CmsDeviceAddVo addVo);

    /**
     * 根据id更新处理
     */
    void updateCmsDeviceById(CmsDeviceUpdateVo updateVo);

    /**
     * 根据id删除处理
     */
    void deleteCmsDeviceById(Long id);

    /**
     * 分页查询数据列表
     */
    PagingResult<CmsDeviceVo> queryPagingResult(CmsDeviceQueryVo queryVo, Paging paging, Long type, String selectName);

    /**
     * 分页查询设备使用情况数据列表
     */
    PagingResult<CmsDeviceUsageVo> queryUsagePagingResult(Long deviceId, Paging paging);
}