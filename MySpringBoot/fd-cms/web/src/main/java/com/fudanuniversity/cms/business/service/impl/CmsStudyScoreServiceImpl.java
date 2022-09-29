package com.fudanuniversity.cms.business.service.impl;

import com.fudanuniversity.cms.business.service.CmsStudyScoreService;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanVitalVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyScoreAddVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyScoreDate;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyScoreVo;
import com.fudanuniversity.cms.business.vo.user.CmsUserVo;
import com.fudanuniversity.cms.commons.constant.CmsConstants;
import com.fudanuniversity.cms.commons.exception.BusinessException;
import com.fudanuniversity.cms.commons.model.JsonResult;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.model.query.SortColumn;
import com.fudanuniversity.cms.commons.model.query.SortMode;
import com.fudanuniversity.cms.commons.util.AssertUtils;
import com.fudanuniversity.cms.commons.validation.constraints.In;
import com.fudanuniversity.cms.repository.dao.CmsScoreDao;
import com.fudanuniversity.cms.repository.entity.CmsScore;
import com.fudanuniversity.cms.repository.query.CmsStudyScoreQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CmsStudyScoreServiceImpl implements CmsStudyScoreService {
    private static final Logger logger = LoggerFactory.getLogger(CmsStudyScoreServiceImpl.class);

    @Resource
    CmsScoreDao cmsScoreDao;

    @Override
    public void saveCmsScore(CmsStudyScoreAddVo addVo) {
        CmsStudyScoreQuery query=CmsStudyScoreQuery.listQuery();
        query.setStuId(addVo.getStuId());
        query.setYear(addVo.getYear());
        query.setSeason(addVo.getSeason());
        Long count=cmsScoreDao.selectCountByParam(query);
        if(count>0L){
            throw new BusinessException("年份或季度出现重复，请重新填写");
        }
        CmsScore cmsScore=new CmsScore();
        cmsScore.setYear(addVo.getYear());
        cmsScore.setSeason(addVo.getSeason());
        cmsScore.setStuId(addVo.getStuId());
        cmsScore.setStuName(addVo.getStuName());
        cmsScore.setStuType(addVo.getStuType());
        if(addVo.getScore()==null){
            cmsScore.setScore(0);
        }
        else {
            cmsScore.setScore(addVo.getScore());
        }
        cmsScore.setMentor(addVo.getMentor());
        cmsScore.setComment(addVo.getComment());
        cmsScore.setTeaComment(null);
        int affect=cmsScoreDao.insert(cmsScore);
        AssertUtils.state(affect==1);
        logger.info("保存CmsScore affect:{}, cmsScore:{}",  affect, cmsScore);
    }

    @Override
    public PagingResult<CmsStudyScoreVo> queryScoreResult(String stuId, Paging paging){
        PagingResult<CmsStudyScoreVo> pagingResult=PagingResult.create(paging);
        CmsStudyScoreQuery query =CmsStudyScoreQuery.listQuery();
        query.setStuId(stuId);
        Long count=cmsScoreDao.selectCountByParam(query);
        pagingResult.setTotal(count);

        if(count> 0L){
            query.setOffset(paging.getOffset());
            query.setLimit(paging.getLimit());
            query.setSorts(SortColumn.create(CmsConstants.CreatedTimeColumn, SortMode.DESC));
            List<CmsScore> cmsScoreList=cmsScoreDao.selectListByParam(query);
            pagingResult.setRows(cmsScoreList, score->{
                CmsStudyScoreVo scoreVo=new CmsStudyScoreVo();
                scoreVo.setId(score.getId());
                scoreVo.setYear(score.getYear());
                scoreVo.setSeason(score.getSeason());
                scoreVo.setStuId(score.getStuId());
                scoreVo.setStuName(score.getStuName());
                scoreVo.setStuType(score.getStuType());
                scoreVo.setScore(score.getScore());
                scoreVo.setComment(score.getComment());
                scoreVo.setTeaComment(score.getTeaComment());
                return scoreVo;
            });
        }
        return pagingResult;
    }

    @Override
    public List<Integer> getLastScoreList(PagingResult<CmsUserVo> pagingResult,CmsStudyScoreDate scoreDate){
        //pagingResult是对应的人员，scoreDate是对应的年份和季度
        List<Integer> lastList=new ArrayList<>();
        if(pagingResult.getRows()!=null){
            for(int i=0;i<pagingResult.getRows().size();i++){
                Integer lastScore=querySingleScoreResult(pagingResult.getRows().get(i).getStuId(),scoreDate);
                if(lastScore!=null && lastScore!=0){
                    lastList.add(lastScore);
                }
                else {
                    lastList.add(0);
                }
            }
        }
        return lastList;
    }

    @Override
    public void updateCmsScoreById(CmsStudyScoreVo cmsStudyScoreVo){
        CmsScore cmsScore = new CmsScore();
        cmsScore.setId(cmsStudyScoreVo.getId());
        cmsScore.setYear(cmsStudyScoreVo.getYear());
        cmsScore.setSeason(cmsStudyScoreVo.getSeason());
        cmsScore.setStuId(cmsStudyScoreVo.getStuId());
        cmsScore.setScore(cmsStudyScoreVo.getScore());
        cmsScore.setComment(cmsStudyScoreVo.getComment());
        cmsScore.setTeaComment(cmsStudyScoreVo.getTeaComment());
        int affect=cmsScoreDao.updateById(cmsScore);
        logger.info("更新CmsScore affect:{}, updater:{}",affect, cmsScore);
        AssertUtils.state(affect==1);
    }

    @Override
    public void deleteCmsScoreById(Integer scoreId){
        int affect=cmsScoreDao.deleteById(scoreId);
    }

    @Override
    public List<CmsScore> queryScoreDateList(){
        List<CmsScore> allDate= cmsScoreDao.queryScoreDateList();
        return allDate;
    }

    @Override
    public List<Integer> queryScoreVital(String stuId){
        List<Integer> vitalList=new ArrayList<>();
        CmsStudyPlanVitalVo vo=new CmsStudyPlanVitalVo();
        vo.setStuId(stuId);
        for(int i=0;i<6;i++){
            vo.setScore(i);
            vitalList.add(cmsScoreDao.queryScoreVital(vo));
        }
        return vitalList;
    }

    @Override
    public List<String> selectUndoneStuId(CmsStudyScoreDate scoreDate){
        CmsStudyScoreQuery query=CmsStudyScoreQuery.listQuery();
        query.setYear(scoreDate.getYear());
        query.setSeason(scoreDate.getSeason());
        List<String> undoneStuIdList=cmsScoreDao.selectUndoneStuId(query);
        return undoneStuIdList;
    }

    private Integer querySingleScoreResult(String stuId,CmsStudyScoreDate scoreDate){ //查找这个学生某个年份某个季度的成绩
        CmsStudyScoreQuery scoreQuery=CmsStudyScoreQuery.singleQuery();
        scoreQuery.setStuId(stuId);
        scoreQuery.setYear(scoreDate.getYear());
        scoreQuery.setSeason(scoreDate.getSeason());
        Integer lastScore=cmsScoreDao.selectLastScore(scoreQuery);
        return lastScore;
    }

    @Override
    public PagingResult<CmsStudyScoreVo> queryMentorStudentResult(String name, Paging paging, CmsStudyScoreDate date, Integer sort){
        PagingResult<CmsStudyScoreVo> pagingResult=PagingResult.create(paging);
        if (name==""){ //导师的名字为空
            name=null;
        }
        if(sort==null){
            sort=0;
        }

        CmsStudyScoreQuery scoreQuery=CmsStudyScoreQuery.listQuery();
        scoreQuery.setMentor(name);
        scoreQuery.setYear(date.getYear());
        scoreQuery.setSeason(date.getSeason());
        Long count = cmsScoreDao.selectCountByParam(scoreQuery);
        pagingResult.setTotal(count);

        if (count>0L){
            if (sort==0){
                scoreQuery.setOffset(paging.getOffset());
                scoreQuery.setLimit(paging.getLimit());
                List<CmsScore> cmsScoreList=cmsScoreDao.selectListByParam(scoreQuery);
                pagingResult.setRows(cmsScoreList, this::convertCmsScoreVo);
            }

            else if (sort==1){
                scoreQuery.setOffset(paging.getOffset());
                scoreQuery.setLimit(paging.getLimit());
                List<CmsScore> cmsScoreList=cmsScoreDao.selectASCListByParam(scoreQuery);
                pagingResult.setRows(cmsScoreList, this::convertCmsScoreVo);
            }

            else if (sort==2){
                scoreQuery.setOffset(paging.getOffset());
                scoreQuery.setLimit(paging.getLimit());
                List<CmsScore> cmsScoreList=cmsScoreDao.selectDESCListByParam(scoreQuery);
                pagingResult.setRows(cmsScoreList, this::convertCmsScoreVo);
            }
        }
        return pagingResult;
    }

    private CmsStudyScoreVo convertCmsScoreVo(CmsScore cmsScore){
        CmsStudyScoreVo vo = new CmsStudyScoreVo();
        vo.setId(cmsScore.getId());
        vo.setYear(cmsScore.getYear());
        vo.setSeason(cmsScore.getSeason());
        vo.setStuId(cmsScore.getStuId());
        vo.setStuName(cmsScore.getStuName());
        vo.setStuType(cmsScore.getStuType());
        vo.setScore(cmsScore.getScore());
        vo.setComment(cmsScore.getComment());
        vo.setTeaComment(cmsScore.getTeaComment());
        vo.setMentor(cmsScore.getMentor());
        return vo;
    }
}
