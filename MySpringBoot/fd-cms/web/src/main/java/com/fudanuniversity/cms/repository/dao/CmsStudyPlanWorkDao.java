package com.fudanuniversity.cms.repository.dao;

import com.fudanuniversity.cms.repository.entity.CmsStudyPlanWork;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanWorkQuery;

import java.util.List;

/**
 * CmsStudyPlanWorkDao
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 17:50:00
 */
public interface CmsStudyPlanWorkDao {

    /**
     * 保存处理
     */
    int insert(CmsStudyPlanWork cmsStudyPlanWork);

    /**
     * 批量upsert
     */
    int bulkUpsert(List<CmsStudyPlanWork> cmsStudyPlanWorkList);

    /**
     * 根据id更新处理
     */
    int updateById(CmsStudyPlanWork cmsStudyPlanWork);

    /**
     * 根据id删除
     */
    int deleteById(Long id);

    /**
     * 根据planId除
     */
    int deleteByPlanId(Long planId);

    /**
     * 查询数据
     */
    List<CmsStudyPlanWork> selectListByParam(CmsStudyPlanWorkQuery query);

    /**
     * 查询数量
     */
    Long selectCountByParam(CmsStudyPlanWorkQuery query);

}
