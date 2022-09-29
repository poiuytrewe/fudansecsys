package com.fudanuniversity.cms.business.service;

import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanAllocationInfoVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanAllocationOverviewVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanAllocationVo;

import java.util.List;

/**
 * CmsStudyPlanAllocationService
 * <p>
 * Created by Xinyue.Tang at 2021-05-07 11:39:06
 */
public interface CmsStudyPlanAllocationService {

    /**
     *
     */
    List<CmsStudyPlanAllocationVo> queryAllocationList(Long userId);

    /**
     *
     */
    List<CmsStudyPlanAllocationInfoVo> queryAllocationInfoList(Long planId);

    /**
     *
     */
    CmsStudyPlanAllocationInfoVo queryAllocationInfo(Long userId, Long planId);

    /**
     *
     */
    CmsStudyPlanAllocationOverviewVo queryCmsStudyPlanOverview(Long planId);

    /**
     *
     */
    CmsStudyPlanAllocationOverviewVo queryUserAllocationOverview(Long userId, Long planId);

    /**
     *
     */
    void deleteCmsStudyPlanAllocationById(Long allocationId);

}