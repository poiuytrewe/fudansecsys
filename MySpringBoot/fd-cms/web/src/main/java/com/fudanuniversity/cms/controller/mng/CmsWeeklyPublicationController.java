package com.fudanuniversity.cms.controller.mng;

import com.fudanuniversity.cms.business.service.CmsUserService;
import com.fudanuniversity.cms.business.service.CmsWeeklyPublicationService;
import com.fudanuniversity.cms.business.vo.weeklyPublication.CmsWeeklyPublicationForBossVo;
import com.fudanuniversity.cms.business.vo.weeklyPublication.CmsWeeklyPublicationManagerVo;
import com.fudanuniversity.cms.business.vo.weeklyPublication.CmsWeeklyPublicationUploadVo;
import com.fudanuniversity.cms.business.vo.weeklyPublication.CmsWeeklyPublicationVo;
import com.fudanuniversity.cms.commons.model.JsonResult;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.model.paging.WeeklyPublicationPaging;
import com.fudanuniversity.cms.controller.BaseController;
import com.fudanuniversity.cms.repository.dao.CmsUserDao;
import com.fudanuniversity.cms.repository.entity.CmsUser;
import com.fudanuniversity.cms.repository.query.CmsUserQuery;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

@RestController
// @CrossOrigin
@RequestMapping(path = "/mng/weeklyPublication")
public class CmsWeeklyPublicationController extends BaseController {

    @Resource
    CmsWeeklyPublicationService cmsWeeklyPublicationService;

    @Resource
    CmsUserService cmsUserService;

    @Resource
    CmsUserDao cmsUserDao;

    @GetMapping(path = "/paging")
    public JsonResult<?> queryPagingResult(@Valid String stuId, Paging paging){
        PagingResult<CmsWeeklyPublicationVo> pagingResult=cmsWeeklyPublicationService.queryPagingResult(stuId,paging);
        return JsonResult.buildSuccess(pagingResult);
    }

    @PostMapping(path = "/upload")
    public JsonResult<?> uploadPublication(@Valid @RequestBody CmsWeeklyPublicationUploadVo uploadVo){
        cmsWeeklyPublicationService.uploadPublication(uploadVo);
        return JsonResult.buildSuccess();
    }

    @PostMapping(path = "/delete")
    public JsonResult<?> deletePublication(@Valid Long id){
        cmsWeeklyPublicationService.deletePublication(id);
        return JsonResult.buildSuccess();
    }

    /**以上是学生的**/

    @GetMapping(path = "/mentor/paging")
    public JsonResult<?> queryTeacherPagingResult(@Valid String stuName, Paging paging, String week){
        List<CmsUser> userList=cmsUserService.queryTeacherStudentList(stuName);//查询这个导师名下所有学生
        PagingResult<CmsWeeklyPublicationVo> pagingResult=cmsWeeklyPublicationService.queryTeacherPagingResult(userList, paging, week);
        return JsonResult.buildSuccess(pagingResult);
    }

    @GetMapping(path = "/mentor/statistics")
    public JsonResult<?> mentorStatistics(@Valid String stuName, String week){
        List<CmsUser> userList=cmsUserService.queryTeacherStudentList(stuName);//查询这个导师名下所有学生
        WeeklyPublicationPaging<CmsUser> paging =cmsWeeklyPublicationService.mentorStatistics(userList, week);
        return JsonResult.buildSuccess(paging);
    }

    @PostMapping(path = "/update")
    public JsonResult<?> updateById(@Valid @RequestBody CmsWeeklyPublicationVo publicationVo){
        cmsWeeklyPublicationService.updateById(publicationVo);
        return JsonResult.buildSuccess();
    }
    /**以上是老师的**/

    @GetMapping(path = "/groupLeader/paging")
    public JsonResult<?> queryGroupLeaderPaging(@Valid String stuName, Paging paging, String week){
        List<CmsUser> studentList=cmsUserService.queryLeaderStudentList(stuName);//stuName是带组博士的名字
        PagingResult<CmsWeeklyPublicationVo> pagingResult=cmsWeeklyPublicationService.queryTeacherPagingResult(studentList, paging, week);
        return JsonResult.buildSuccess(pagingResult);
    }

    @GetMapping(path = "/groupLeader/statistics")
    public JsonResult<?> groupLeaderStatistics(@Valid String stuName, String week){
        List<CmsUser> userList=cmsUserService.queryLeaderStudentList(stuName);
        WeeklyPublicationPaging<CmsUser> paging=cmsWeeklyPublicationService.mentorStatistics(userList, week);
        return JsonResult.buildSuccess(paging);
    }
    /**以上是带组博士的**/

    @GetMapping(path = "/boss/paging")
    public JsonResult<?> queryBossPaging(@Valid Paging paging, String week){
        PagingResult<CmsWeeklyPublicationForBossVo> publication=cmsWeeklyPublicationService.queryBossPaging(paging, week);
        return JsonResult.buildSuccess(publication);
    }
    /**以上是boss的**/

    @GetMapping(path = "/manager/paging")
    public JsonResult<?> queryManagerPaging(@Valid Paging paging, String week){
        PagingResult<CmsWeeklyPublicationManagerVo> pagingResult = cmsWeeklyPublicationService.queryManagerPaging(paging, week);
        return JsonResult.buildSuccess(pagingResult);
    }

    @GetMapping(path = "/manager/statistics")
    public JsonResult<?> managerStatistics(@Valid String week){
        CmsUserQuery cmsUserQuery=new CmsUserQuery();
        List<CmsUser> userList=cmsUserDao.selectStudentListByParam(cmsUserQuery);//所有的学生列表
        WeeklyPublicationPaging<CmsUser> paging=cmsWeeklyPublicationService.mentorStatistics(userList, week);
        return JsonResult.buildSuccess(paging);
    }
}
