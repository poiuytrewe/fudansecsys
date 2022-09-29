package com.fudanuniversity.cms.repository.dao;

import com.fudanuniversity.cms.repository.entity.CmsStudyPlanItem;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanItemInfo;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanItemQuery;

import java.util.List;

/**
 * CmsStudyPlanAllocationDao
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 17:50:00
 */
public interface CmsStudyPlanItemDao {

    /**
     * 保存处理
     */
    int insert(CmsStudyPlanItem cmsStudyPlanItem);

    /**
     * 批量upsert
     */
    int bulkUpsert(List<CmsStudyPlanItem> cmsStudyPlanItemList);

    /**
     * 根据id更新处理
     */
    int updateById(CmsStudyPlanItem cmsStudyPlanItem);

    /**
     * 根据id删除
     */
    int deleteById(Long id);

    /**
     * 根据id列表删除
     */
    int deleteByIds(List<Long> idList);

    /**
     * 根据planId删除
     */
    int deleteByPlanId(Long planId);

    int deleteByPlanId(Long planId, Long userId);

    /**
     * 查询数据
     */
    List<CmsStudyPlanItem> selectListByParam(CmsStudyPlanItemQuery query);

    /**
     * 查询数据
     */
    List<CmsStudyPlanItemInfo> selectInfoByParam(CmsStudyPlanItemQuery query);

    /**
     * 查询数量
     */
    Long selectCountByParam(CmsStudyPlanItemQuery query);

}
