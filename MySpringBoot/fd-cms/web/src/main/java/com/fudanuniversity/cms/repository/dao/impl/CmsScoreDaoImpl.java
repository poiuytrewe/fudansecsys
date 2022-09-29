package com.fudanuniversity.cms.repository.dao.impl;

import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanVitalVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyScoreDate;
import com.fudanuniversity.cms.commons.validation.constraints.In;
import com.fudanuniversity.cms.repository.dao.CmsScoreDao;
import com.fudanuniversity.cms.repository.entity.CmsScore;
import com.fudanuniversity.cms.repository.entity.CmsUser;
import com.fudanuniversity.cms.repository.mapper.CmsScoreMapper;
import com.fudanuniversity.cms.repository.query.CmsStudyScoreQuery;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class CmsScoreDaoImpl implements CmsScoreDao {

    @Resource
    CmsScoreMapper cmsScoreMapper;

    @Override
    public int insert(CmsScore cmsScore) {
        Assert.notNull(cmsScore,"保存对象不能为空");

        validateEntity(cmsScore);

        return cmsScoreMapper.insert(cmsScore);
    }

    private void validateEntity(CmsScore cmsScore) {
        Assert.notNull(cmsScore.getYear(), "年份不能为空");
        Assert.notNull(cmsScore.getSeason(), "季度不能为空");
        Assert.notNull(cmsScore.getStuId(), "学生学号不能为空");
        Assert.notNull(cmsScore.getScore(), "分数不能为空");
        Assert.notNull(cmsScore.getComment(), "评语不能为空");
    }

    @Override
    public Long selectCountByParam(CmsStudyScoreQuery query){
        Assert.notNull(query, "查询参数不能为空");
        return cmsScoreMapper.selectCountByParam(query);
    }

    @Override
    public List<CmsScore> selectListByParam(CmsStudyScoreQuery query){
        Assert.notNull(query, "查询参数不能为空");
        return cmsScoreMapper.selectListByParam(query);
    }

    @Override
    public int updateById(CmsScore cmsScore){
        return cmsScoreMapper.updateById(cmsScore);
    }

    @Override
    public int deleteById(Integer scoreId){
        return cmsScoreMapper.deleteById(scoreId);
    }

    @Override
    public Integer selectLastScore(CmsStudyScoreQuery scoreQuery){
        Assert.notNull(scoreQuery,"参数不能为空");
        return cmsScoreMapper.selectLastScore(scoreQuery);
    }

    @Override
    public List<CmsScore> queryScoreDateList(){
        return cmsScoreMapper.queryScoreDateList();
    }

    @Override
    public List<CmsScore> selectASCListByParam(CmsStudyScoreQuery scoreQuery){
        return cmsScoreMapper.selectASCListByParam(scoreQuery);
    }

    @Override
    public List<CmsScore> selectDESCListByParam(CmsStudyScoreQuery scoreQuery){
        return cmsScoreMapper.selectDESCListByParam(scoreQuery);
    }

    @Override
    public Integer queryScoreVital(CmsStudyPlanVitalVo vitalVo){
        return cmsScoreMapper.queryScoreVital(vitalVo);
    }

    @Override
    public List<String> selectUndoneStuId(CmsStudyScoreQuery query){
        return cmsScoreMapper.selectUndoneStuId(query);
    }
}
