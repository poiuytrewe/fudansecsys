package com.fudanuniversity.cms.repository.mapper;

import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanVitalVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyScoreDate;
import com.fudanuniversity.cms.repository.entity.CmsScore;
import com.fudanuniversity.cms.repository.query.CmsStudyScoreQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CmsScoreMapper {
    int insert(CmsScore cmsScore);

    Long selectCountByParam(CmsStudyScoreQuery query);

    List<CmsScore> selectListByParam(CmsStudyScoreQuery query);

    int updateById(CmsScore cmsScore);

    int deleteById(Integer scoreId);

    List<CmsScore> queryScoreDateList();

    List<CmsScore> selectASCListByParam(CmsStudyScoreQuery scoreQuery);

    List<CmsScore> selectDESCListByParam(CmsStudyScoreQuery scoreQuery);

    Integer selectLastScore(CmsStudyScoreQuery scoreQuery);

    Integer queryScoreVital(CmsStudyPlanVitalVo vitalVo);

    List<String> selectUndoneStuId(CmsStudyScoreQuery query);
}
