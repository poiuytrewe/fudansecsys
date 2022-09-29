package com.fudanuniversity.cms.controller.mng;

import com.fudanuniversity.cms.business.service.CmsStudyPlanProcessService;
import com.fudanuniversity.cms.business.service.CmsUserService;
import com.fudanuniversity.cms.business.vo.study.plan.*;
import com.fudanuniversity.cms.business.vo.user.CmsUserQueryVo;
import com.fudanuniversity.cms.business.vo.user.CmsUserVo;
import com.fudanuniversity.cms.commons.model.JsonResult;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.controller.BaseController;
import com.fudanuniversity.cms.repository.dao.CmsUserDao;
import com.fudanuniversity.cms.repository.entity.CmsStudyPlanProcess;
import com.fudanuniversity.cms.repository.entity.CmsUser;
import com.fudanuniversity.cms.repository.query.CmsUserQuery;
import com.google.errorprone.annotations.Var;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;
import springfox.documentation.spring.web.json.Json;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
// @CrossOrigin
@RequestMapping(path = "/mng/study/process")
public class CmsStudyPlanProcessMngController extends BaseController {

    @Resource
    CmsStudyPlanProcessService cmsStudyPlanProcessService;

    @Resource
    CmsUserService cmsUserService;

    @Resource
    CmsUserDao cmsUserDao;

    @GetMapping(path ="/paging" )
    public JsonResult<?> queryPagingResult(String stuId){
        PagingResult<CmsStudyPlanProcessVo> pagingResult=cmsStudyPlanProcessService.queryPagingResult(stuId);
        return JsonResult.buildSuccess(pagingResult);
    }

    @PostMapping(path = "/add")
    public JsonResult<?> addProcess(@RequestBody CmsStudyPlanProcessAddVo addVo){
        cmsStudyPlanProcessService.addProcess(addVo);
        return JsonResult.buildSuccess();
    }

    @PostMapping(path = "/update")
    public JsonResult<?> updateProcess(@RequestBody CmsStudyPlanProcessVo processVo){
        cmsStudyPlanProcessService.updateProcess(processVo);
        return JsonResult.buildSuccess();
    }

    @PostMapping(path = "/reset")
    public JsonResult<?> resetProcess(@RequestBody CmsStudyPlanProcessAddVo addVo){
        cmsStudyPlanProcessService.resetProcess(addVo);
        return JsonResult.buildSuccess();
    }

    @PostMapping(path = "/delete")
    public JsonResult<?> deleteProcess(Long id){
        cmsStudyPlanProcessService.deleteProcess(id);
        return JsonResult.buildSuccess();
    }

    @PostMapping(path = "/groupUpdate")//整体任务向后延期
    public JsonResult<?> groupUpdateProcess(@RequestBody CmsStudyPlanGroupUpdateVo updateVo){
        cmsStudyPlanProcessService.groupUpdateProcess(updateVo);
        return JsonResult.buildSuccess();
    }

    @PostMapping(path = "/reConfig")
    public JsonResult<?> reConfigProcess(String stuId){//重新配置
        cmsStudyPlanProcessService.reConfigProcess(stuId);
        return JsonResult.buildSuccess();
    }

    @GetMapping(path = "/getMissionStatus")
    public JsonResult<?> getStuMissionStatus(@Valid String name, Paging paging){//获取任务状态
        CmsUserQuery query=CmsUserQuery.listQuery();
        List<CmsUser> cmsStudentList=new ArrayList<>();
        query.setMentor(name);
        query.setType(0);
        Long count=cmsUserDao.selectMentorStudentCountByParam(query);
        if(count>0L){
            cmsStudentList=cmsUserDao.selectMentorStudentListByParam(query);
        }//上述步骤是获取到该导师的所有学生

        List<Integer> stuMissionStatusList=cmsStudyPlanProcessService.selectStuMissionStatusList(cmsStudentList, paging);
        return JsonResult.buildSuccess(stuMissionStatusList);
    }

    @GetMapping(path = "/student")
    public JsonResult<?> getStuMissionStudent(@Valid String name, Paging paging){
        CmsUserQuery query=CmsUserQuery.listQuery();
        List<CmsUser> cmsStudentList=new ArrayList<>();
        query.setMentor(name);
        query.setType(0);
        Long count=cmsUserDao.selectMentorStudentCountByParam(query);
        if(count>0L){
            cmsStudentList=cmsUserDao.selectMentorStudentListByParam(query);
        }//上述步骤是获取到该导师的所有学生
        List<CmsUser> studentList=cmsStudyPlanProcessService.selectMissionStudentList(cmsStudentList, paging);
        return JsonResult.buildSuccess(studentList);
    }

    @GetMapping(path = "/count")
    public JsonResult<?> selectCount(@Valid String name){
        CmsUserQuery query=CmsUserQuery.listQuery();
        List<CmsUser> cmsStudentList=new ArrayList<>();
        query.setMentor(name);
        query.setType(0);
        Long count=cmsUserDao.selectMentorStudentCountByParam(query);
        if(count>0L){
            cmsStudentList=cmsUserDao.selectMentorStudentListByParam(query);
        }//上述步骤是获取到该导师的所有学生
        int[] c= cmsStudyPlanProcessService.selectCount(cmsStudentList);
        return JsonResult.buildSuccess(c);
    }

    @GetMapping(path = "/mngGetMissionStatus")
    public JsonResult<?> mngGetStuMissionStatus(@Valid String name, Paging paging, CmsUserQueryVo queryVo){
        PagingResult<CmsUserVo> pagingResult = cmsUserService.queryStudentResult(queryVo, paging, name);
        List<Integer> stuMissionStatusList=cmsStudyPlanProcessService.selectStuMissionStatusListMng(pagingResult);
        return JsonResult.buildSuccess(stuMissionStatusList);
    }

    @GetMapping(path = "/getAllApprove")
    public JsonResult<?> getAllApprove(@Valid String name){//某个导师的名字
        CmsUserQuery query=CmsUserQuery.listQuery();
        List<CmsUser> cmsStudentList=new ArrayList<>();
        query.setMentor(name);
        Long count=cmsUserDao.selectMentorStudentCountByParam(query);
        if(count>0L){
            cmsStudentList=cmsUserDao.selectMentorStudentListByParam(query);
        }//上述步骤是获取到该导师的所有学生
        //接下来就是将所有missionStatus为2或者4的状态返回
        List<CmsStudyPlanProcess> approveList=cmsStudyPlanProcessService.getAllApprove(cmsStudentList);
        return JsonResult.buildSuccess(approveList);
    }

    @GetMapping(path = "/getAllApplyCount")
    public JsonResult<?> getAllApplyCount(@Valid String name){
        CmsUserQuery query=CmsUserQuery.listQuery();
        List<CmsUser> cmsStudentList=new ArrayList<>();
        query.setMentor(name);
        Long count=cmsUserDao.selectMentorStudentCountByParam(query);
        if(count>0L){
            cmsStudentList=cmsUserDao.selectMentorStudentListByParam(query);
        }//上述步骤是获取到该导师的所有学生
        Integer mount=cmsStudyPlanProcessService.getAllApplyCount(cmsStudentList);
        return JsonResult.buildSuccess(mount);
    }

    @GetMapping(path = "/getAllDelay")
    public JsonResult<?> getAllDelay(@Valid String name){
        CmsUserQuery query=CmsUserQuery.listQuery();
        List<CmsUser> cmsStudentList=new ArrayList<>();
        query.setMentor(name);
        Long count=cmsUserDao.selectMentorStudentCountByParam(query);
        if(count>0L){
            cmsStudentList=cmsUserDao.selectMentorStudentListByParam(query);
        }//上述步骤是获取到该导师的所有学生
        List<CmsStudyPlanProcess> delayList =cmsStudyPlanProcessService.getAllDelay(cmsStudentList);
        return JsonResult.buildSuccess(delayList);
    }

    @PostMapping(path = "/groupApprove")
    public JsonResult<?> groupApprove(@Valid @RequestBody CmsStudyPlanGroupApproveVo approveVo){
        cmsStudyPlanProcessService.groupApprove(approveVo);
        return JsonResult.buildSuccess();
    }
    @PostMapping(path = "/groupDone")
    public JsonResult<?> groupDone(@RequestBody CmsStudyPlanGroupDoneVo doneVo){
        cmsStudyPlanProcessService.groupDone(doneVo.getGroupList());
        return JsonResult.buildSuccess();
    }

    @PostMapping(path = "/groupDelay")
    public JsonResult<?> groupDelay(@RequestBody CmsStudyPlanGroupDelayVo delayVo){
        cmsStudyPlanProcessService.groupDelay(delayVo);
        return JsonResult.buildSuccess();
    }

    @GetMapping(path = "/total")
    public JsonResult<?> getMasterFromMentor(@Valid String name, Paging paging){
        CmsUserQuery query = CmsUserQuery.listQuery();
        query.setMentor(name);
        query.setType(0);
        Long count = cmsUserDao.selectCountByParam(query);
        return JsonResult.buildSuccess(count);
    }

    @GetMapping(path = "/graduate")
    public JsonResult<?> getGraduateInformation(@Valid String mentor, Paging paging){
        CmsUserQuery userQuery = CmsUserQuery.listQuery();
        userQuery.setMentor(mentor);
        userQuery.setType(0);
        userQuery.setLimit(paging.getLimit());
        userQuery.setOffset(paging.getOffset());
        List<CmsUser> cmsUserList = cmsUserDao.selectMentorStudentListByParam(userQuery);
        List<CmsGraduateInformationVo> graduateInformationVoList = cmsStudyPlanProcessService.selectGraduateInformation(cmsUserList);
        return JsonResult.buildSuccess(graduateInformationVoList);
    }
}
