package com.fudanuniversity.cms.repository.dao;

import com.fudanuniversity.cms.repository.entity.CmsStudyPlan;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanQuery;

import java.util.List;

/**
 * CmsStudyPlanDao
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 17:50:00
 */
public interface CmsStudyPlanDao {

    /**
     * 保存处理
     */
    int insert(CmsStudyPlan cmsStudyPlan);

    /**
     * 批量upsert
     */
    int bulkUpsert(List<CmsStudyPlan> cmsStudyPlanList);

    /**
     * 根据id更新处理
     */
    int updateById(CmsStudyPlan cmsStudyPlan);

    /**
     * 根据id删除
     */
    int deleteById(Long id);

    /**
     * 更新版本
     */
    int increaseVersionById(Long id);

    /**
     * 查询数据
     */
    List<CmsStudyPlan> selectListByParam(CmsStudyPlanQuery query);

    /**
     * 查询数量
     */
    Long selectCountByParam(CmsStudyPlanQuery query);

}
