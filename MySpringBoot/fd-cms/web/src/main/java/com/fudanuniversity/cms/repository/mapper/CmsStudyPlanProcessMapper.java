package com.fudanuniversity.cms.repository.mapper;

import com.fudanuniversity.cms.repository.dao.CmsStudyPlanProcessDao;
import com.fudanuniversity.cms.repository.entity.CmsStudyPlanProcess;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanProcessQuery;

import java.util.List;

public interface CmsStudyPlanProcessMapper {
    Long selectCountByParam(CmsStudyPlanProcessQuery query);

    List<CmsStudyPlanProcess> selectListByParam(CmsStudyPlanProcessQuery query);

    List<CmsStudyPlanProcess> selectLateList(CmsStudyPlanProcessQuery query);

    void insert(CmsStudyPlanProcess cmsStudyPlanProcess);

    void updateById(CmsStudyPlanProcess process);

    void deleteById(Long id);

    Integer selectApplyCount(String stuId);

    void groupUpdateById(List<Long> idList);

    void groupUpdateList(List<CmsStudyPlanProcess> idList);

    Integer selectMissionStatus(String stuId);

    List<CmsStudyPlanProcess> getAllApprove(String stuId);

    List<CmsStudyPlanProcess> getAllDelay(String stuId);
}
