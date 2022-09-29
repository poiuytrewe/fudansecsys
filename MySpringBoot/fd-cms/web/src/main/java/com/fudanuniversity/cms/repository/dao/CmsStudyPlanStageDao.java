package com.fudanuniversity.cms.repository.dao;

import com.fudanuniversity.cms.repository.entity.CmsStudyPlanStage;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanStageQuery;

import java.util.List;

/**
 * CmsStudyPlanStageDao
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 17:50:00
 */
public interface CmsStudyPlanStageDao {

    /**
     * 保存处理
     */
    int insert(CmsStudyPlanStage cmsStudyPlanStage);

    /**
     * 批量upsert
     */
    int bulkUpsert(List<CmsStudyPlanStage> cmsStudyPlanStageList);

    /**
     * 根据id更新处理
     */
    int updateById(CmsStudyPlanStage cmsStudyPlanStage);

    /**
     * 根据id删除
     */
    int deleteById(Long id);

    /**
     * 根据planId删除
     */
    int deleteByPlanId(Long planId);

    /**
     * 查询数据
     */
    List<CmsStudyPlanStage> selectListByParam(CmsStudyPlanStageQuery query);

    /**
     * 查询数量
     */
    Long selectCountByParam(CmsStudyPlanStageQuery query);

}
