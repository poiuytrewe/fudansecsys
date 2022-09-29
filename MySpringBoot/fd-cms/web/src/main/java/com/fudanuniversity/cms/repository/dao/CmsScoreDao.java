package com.fudanuniversity.cms.repository.dao;

import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanVitalVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyScoreDate;
import com.fudanuniversity.cms.repository.entity.CmsScore;
import com.fudanuniversity.cms.repository.query.CmsStudyScoreQuery;

import java.util.List;

public interface CmsScoreDao {

    int insert(CmsScore cmsScore);

    Long selectCountByParam(CmsStudyScoreQuery query);

    List<CmsScore> selectListByParam(CmsStudyScoreQuery query);

    int updateById(CmsScore cmsScore);

    int deleteById(Integer scoreId);

    Integer selectLastScore(CmsStudyScoreQuery scoreQuery);

    List<CmsScore> queryScoreDateList();

    List<CmsScore> selectASCListByParam(CmsStudyScoreQuery scoreQuery);

    List<CmsScore> selectDESCListByParam(CmsStudyScoreQuery scoreQuery);

    Integer queryScoreVital(CmsStudyPlanVitalVo vitalVo);

    List<String> selectUndoneStuId(CmsStudyScoreQuery query);
}
