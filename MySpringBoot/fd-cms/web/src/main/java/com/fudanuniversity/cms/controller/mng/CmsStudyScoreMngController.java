package com.fudanuniversity.cms.controller.mng;

import com.fudanuniversity.cms.business.service.CmsStudyPlanService;
import com.fudanuniversity.cms.business.service.CmsStudyScoreService;
import com.fudanuniversity.cms.business.service.CmsUserService;
import com.fudanuniversity.cms.business.vo.device.CmsDeviceVo;
import com.fudanuniversity.cms.business.vo.study.plan.*;
import com.fudanuniversity.cms.business.vo.user.CmsUserQueryVo;
import com.fudanuniversity.cms.business.vo.user.CmsUserVo;
import com.fudanuniversity.cms.commons.model.JsonResult;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.model.web.LoginUser;
import com.fudanuniversity.cms.controller.BaseController;
import com.fudanuniversity.cms.repository.dao.CmsScoreDao;
import com.fudanuniversity.cms.repository.dao.CmsUserDao;
import com.fudanuniversity.cms.repository.entity.CmsScore;
import com.fudanuniversity.cms.repository.entity.CmsUser;
import com.fudanuniversity.cms.repository.query.CmsStudyScoreQuery;
import com.fudanuniversity.cms.repository.query.CmsUserQuery;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import javax.annotation.Resource;
import java.util.*;

@Api(tags = "管理员 - 培养计划评分")
@RestController
// @CrossOrigin
@RequestMapping("/mng/study/score")
public class CmsStudyScoreMngController extends BaseController{

    @Resource
    private CmsUserService cmsUserService;

    @Resource
    private CmsStudyScoreService cmsStudyScoreService;

    @Resource
    private CmsUserDao cmsUserDao;

    @Resource
    private CmsScoreDao cmsScoreDao;

    @PostMapping(path = "/add")
    public JsonResult<?> addNewScore(@Valid @RequestBody CmsStudyScoreAddVo addVo){ //这里还应该找有没有已经添加过的年份季度
        cmsStudyScoreService.saveCmsScore(addVo);
        return JsonResult.buildSuccess();
    }

    @GetMapping(path = "/return")
    public JsonResult<?> queryScoreResult(String stuId, Paging paging){
        PagingResult<CmsStudyScoreVo> pagingResult=cmsStudyScoreService.queryScoreResult(stuId, paging);
        return JsonResult.buildSuccess(pagingResult);
    }

    @GetMapping(path = "/vital") //用于展示图表的
    public JsonResult<?> queryScoreVital(String stuId){
        List<Integer> scoreVital=cmsStudyScoreService.queryScoreVital(stuId);
        return JsonResult.buildSuccess(scoreVital);
    }

    @GetMapping(path = "/returnSingle")
    public JsonResult<?> queryMentorStudentResult(@Valid String name, Paging paging, CmsStudyScoreDate scoreDate,Integer sort) {
        //scoreDate对应的是年份和季度
        PagingResult<CmsStudyScoreVo> pagingResult=cmsStudyScoreService.queryMentorStudentResult(name, paging, scoreDate, sort);
        return JsonResult.buildSuccess(pagingResult);
    }

    @PostMapping(path = "/update")
    public JsonResult<?> updateScore(@Valid @RequestBody CmsStudyScoreVo cmsStudyScoreVo){
        cmsStudyScoreService.updateCmsScoreById(cmsStudyScoreVo);
        return JsonResult.buildSuccess();
    }

    @PostMapping(path = "/delete")
    public JsonResult<?> deleteScore(@NotNull @Min(1L) Integer scoreId){
        cmsStudyScoreService.deleteCmsScoreById(scoreId);
        return JsonResult.buildSuccess();
    }

    @GetMapping(path = "/getDate")
    public JsonResult<?> getScoreDate(){
        List<CmsScore> list=cmsStudyScoreService.queryScoreDateList();
        return JsonResult.buildSuccess(list);
    }

    @GetMapping( path = "/unDone")
    public JsonResult<?> getUnDoneUser(@Valid CmsStudyScoreDate scoreDate){
        CmsUserQuery query=new CmsUserQuery();
        List<CmsUser> cmsStudentList = cmsUserDao.selectStudentListByParam(query);//得到所有的学生列表
        Map map=new HashMap();
        for(int i=0;i<cmsStudentList.size();i++){
            map.put(cmsStudentList.get(i).getStuId(),0);
        }//做映射
        List<String> stuIdList =cmsStudyScoreService.selectUndoneStuId(scoreDate);//已经评分的学生StuId
        for(int i=0;i<stuIdList.size();i++){
            map.replace(stuIdList.get(i),1);
        }

        List<CmsUser> undone=new ArrayList<>();//存储未评分的学生信息
        for(int i=0;i<cmsStudentList.size();i++){
            if(Objects.equals(map.get(cmsStudentList.get(i).getStuId()),0)){
                undone.add(cmsStudentList.get(i));
            }
        }
        return JsonResult.buildSuccess(undone);
    }

    @GetMapping(path = "/mentorStatics")
    public JsonResult<?> getMentorStatics(@Valid CmsStudyScoreDate date, String mentor){
        CmsUserQuery query = CmsUserQuery.listQuery();
        query.setMentor(mentor);
        List<CmsUser> cmsUserList = cmsUserDao.selectMentorStudentListByParam(query);
        Map map = new HashMap();
        for(int i=0;i<cmsUserList.size();i++){
            map.put(cmsUserList.get(i).getName(),0);
        }
        CmsStudyScoreQuery scoreQuery=CmsStudyScoreQuery.listQuery();
        scoreQuery.setYear(date.getYear());
        scoreQuery.setSeason(date.getSeason());
        scoreQuery.setMentor(mentor);
        List<CmsScore> cmsScoreList=cmsScoreDao.selectListByParam(scoreQuery);
        for(int i=0;i<cmsScoreList.size();i++){
            map.replace(cmsScoreList.get(i).getStuName(),1);
        }

        List<String> undoneList = new ArrayList<>();
        for(int i=0;i<cmsUserList.size();i++){
            if(Objects.equals(map.get(cmsUserList.get(i).getName()), 0)){
                undoneList.add(cmsUserList.get(i).getName());
            }
        }
        return JsonResult.buildSuccess(undoneList);
    }
}
