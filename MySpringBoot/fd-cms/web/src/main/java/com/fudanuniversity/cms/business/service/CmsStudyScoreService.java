package com.fudanuniversity.cms.business.service;

import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanVitalVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyScoreAddVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyScoreDate;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyScoreVo;
import com.fudanuniversity.cms.business.vo.user.CmsUserQueryVo;
import com.fudanuniversity.cms.business.vo.user.CmsUserVo;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.validation.constraints.In;
import com.fudanuniversity.cms.repository.entity.CmsScore;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface CmsStudyScoreService {

    void saveCmsScore(CmsStudyScoreAddVo addVo);//增

    PagingResult<CmsStudyScoreVo> queryScoreResult(String stuId, Paging paging);//查

    void updateCmsScoreById(CmsStudyScoreVo cmsStudyScoreVo);//改

    void deleteCmsScoreById(Integer scoreId);//删

    List<Integer> getLastScoreList(PagingResult<CmsUserVo> pagingResult, CmsStudyScoreDate scoreDate);//最后一次

    List<CmsScore> queryScoreDateList();

    List<Integer> queryScoreVital(String stuId);

    List<String> selectUndoneStuId(CmsStudyScoreDate scoreDate);

    PagingResult<CmsStudyScoreVo> queryMentorStudentResult(String name, Paging paging, CmsStudyScoreDate date, Integer sort);
}
