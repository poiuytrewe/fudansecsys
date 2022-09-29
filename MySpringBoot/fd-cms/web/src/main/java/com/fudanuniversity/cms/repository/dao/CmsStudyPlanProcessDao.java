package com.fudanuniversity.cms.repository.dao;

import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanEssentialInit;
import com.fudanuniversity.cms.repository.entity.CmsStudyPlanProcess;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanProcessQuery;

import java.util.List;

public interface CmsStudyPlanProcessDao {

    Long selectCountByParam(CmsStudyPlanProcessQuery query);

    List<CmsStudyPlanProcess> selectListByParam(CmsStudyPlanProcessQuery query);

    List<CmsStudyPlanProcess> selectLateList(CmsStudyPlanProcessQuery query);

    void insert(CmsStudyPlanProcess cmsStudyPlanProcess);

    void updateById(CmsStudyPlanProcess process);

    void deleteById(Long id);

    void groupUpdateById(List<Long> idList);

    Integer selectApplyCount(String stuId);

    void groupUpdateList(List<CmsStudyPlanProcess> idList);

    Integer selectMissionStatus(String stuId);

    List<CmsStudyPlanProcess> getAllApprove(String stuId);

    List<CmsStudyPlanProcess> getAllDelay(String stuId);

}
