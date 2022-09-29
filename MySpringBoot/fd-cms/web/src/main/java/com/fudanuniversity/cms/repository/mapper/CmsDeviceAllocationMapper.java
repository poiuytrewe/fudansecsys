package com.fudanuniversity.cms.repository.mapper;

import com.fudanuniversity.cms.repository.entity.CmsDeviceAllocation;
import com.fudanuniversity.cms.repository.query.CmsDeviceAllocationQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * CmsDeviceAllocationMapper接口
 * <p>
 * Created by Xinyue.Tang at 2021-05-02
 */
@Mapper
public interface CmsDeviceAllocationMapper {

    /**
     * 保存处理
     */
    int insert(CmsDeviceAllocation cmsDeviceAllocation);

    /**
     * 批量upsert
     */
    int bulkUpsert(List<CmsDeviceAllocation> cmsDeviceAllocationList);

    /**
     * 删除处理
     */
    int deleteById(Long id);

    /**
     * 更新处理
     */
    int updateById(CmsDeviceAllocation cmsDeviceAllocation);

    /**
     * 根据条件查询信息列表
     */
    List<CmsDeviceAllocation> selectListByParam(CmsDeviceAllocationQuery query);

    /**
     * 根据条件查询信息总数目
     */
    Long selectCountByParam(CmsDeviceAllocationQuery query);

}
