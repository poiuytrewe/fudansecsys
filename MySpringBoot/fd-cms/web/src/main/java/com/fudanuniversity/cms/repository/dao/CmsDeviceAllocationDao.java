package com.fudanuniversity.cms.repository.dao;

import com.fudanuniversity.cms.repository.entity.CmsDeviceAllocation;
import com.fudanuniversity.cms.repository.query.CmsDeviceAllocationQuery;

import java.util.List;

/**
 * CmsDeviceAllocationDao
 * <p>
 * Created by Xinyue.Tang at 2021-05-02
 */
public interface CmsDeviceAllocationDao {

    /**
     * 保存处理
     */
    int insert(CmsDeviceAllocation cmsDeviceAllocation);

    /**
     * 批量upsert
     */
    int bulkUpsert(List<CmsDeviceAllocation> cmsDeviceAllocationList);

    /**
     * 根据id更新处理
     */
    int updateById(CmsDeviceAllocation cmsDeviceAllocation);

    /**
     * 根据id删除
     */
    int deleteById(Long id);

    /**
     * 查询数据
     */
    List<CmsDeviceAllocation> selectListByParam(CmsDeviceAllocationQuery query);

    /**
     * 查询数量
     */
    Long selectCountByParam(CmsDeviceAllocationQuery query);

}
