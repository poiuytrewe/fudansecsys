package com.fudanuniversity.cms.repository.dao;

import com.fudanuniversity.cms.repository.entity.CmsDevice;
import com.fudanuniversity.cms.repository.query.CmsDeviceQuery;

import java.util.List;

/**
 * CmsDeviceDao
 * <p>
 * Created by Xinyue.Tang at 2021-05-02
 */
public interface CmsDeviceDao {//不需要改

    /**
     * 保存处理
     */
    int insert(CmsDevice cmsDevice);

    /**
     * 批量upsert
     */
    int bulkUpsert(List<CmsDevice> cmsDeviceList);

    /**
     * 根据id更新处理
     */
    int updateById(CmsDevice cmsDevice);

    /**
     * 根据id删除
     */
    int deleteById(Long id);

    /**
     * 根据id查询并加锁
     */
    CmsDevice selectByIdForUpdate(Long id);

    /**
     * 查询数据
     */
    List<CmsDevice> selectListByParam(CmsDeviceQuery query);

    /**
     * 查询数量
     */
    Long selectCountByParam(CmsDeviceQuery query);

}
