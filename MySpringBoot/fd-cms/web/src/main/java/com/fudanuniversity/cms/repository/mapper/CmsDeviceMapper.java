package com.fudanuniversity.cms.repository.mapper;

import com.fudanuniversity.cms.repository.entity.CmsDevice;
import com.fudanuniversity.cms.repository.query.CmsDeviceQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * CmsDeviceMapper接口
 * <p>
 * Created by Xinyue.Tang at 2021-05-02
 */
@Mapper
public interface CmsDeviceMapper {

    /**
     * 保存处理
     */
    int insert(CmsDevice cmsDevice);

    /**
     * 批量upsert
     */
    int bulkUpsert(List<CmsDevice> cmsDeviceList);

    /**
     * 删除处理
     */
    int deleteById(Long id);

    /**
     * 更新处理
     */
    int updateById(CmsDevice cmsDevice);

    CmsDevice selectByIdForUpdate(Long id);

    /**
     * 根据条件查询信息列表
     */
    List<CmsDevice> selectListByParam(CmsDeviceQuery query);

    /**
     * 根据条件查询信息总数目
     */
    Long selectCountByParam(CmsDeviceQuery query);

}
