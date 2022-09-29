package com.fudanuniversity.cms.business.service;

import com.fudanuniversity.cms.business.vo.study.plan.*;
import com.fudanuniversity.cms.business.vo.user.CmsUserVo;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.repository.entity.CmsStudyPlanProcess;
import com.fudanuniversity.cms.repository.entity.CmsStudyPlanProcessMissionStatus;
import com.fudanuniversity.cms.repository.entity.CmsUser;

import java.util.List;

public interface CmsStudyPlanProcessService {
    PagingResult<CmsStudyPlanProcessVo> queryPagingResult(String stuId);

//    void processEssentialInit(CmsStudyPlanEssentialInit essentialInit);

//    void processBaseInit(CmsUserVo userVo);

    void addProcess(CmsStudyPlanProcessAddVo addVo);

    void resetProcess(CmsStudyPlanProcessAddVo addVo);

    void updateProcess(CmsStudyPlanProcessVo processVo);

    void deleteProcess(Long id);

    void reConfigProcess(String stuId);

    void groupUpdateProcess(CmsStudyPlanGroupUpdateVo updateVo);

    List<Integer> selectStuMissionStatusList(List<CmsUser> cmsStudentList, Paging paging);

    List<CmsStudyPlanProcessMissionStatus> statusList(List<CmsUser> cmsStudentList);

    List<Integer> selectStuMissionStatusListMng(PagingResult<CmsUserVo> pagingResult);

    List<CmsUser> selectMissionStudentList(List<CmsUser> cmsStudentList, Paging paging);

    Integer getAllApplyCount(List<CmsUser> cmsStudentList);

    int[] selectCount(List<CmsUser> cmsStudentList);

    List<CmsStudyPlanProcess> getAllApprove(List<CmsUser> cmsStudentList);

    List<CmsStudyPlanProcess> getAllDelay(List<CmsUser> cmsStudentList);

    void groupDone(List<CmsStudyPlanProcess> groupList);

    void groupDelay(CmsStudyPlanGroupDelayVo delayVo);

    void groupApprove(CmsStudyPlanGroupApproveVo approveVo);

    List<CmsGraduateInformationVo> selectGraduateInformation(List<CmsUser> cmsUserList);
}
