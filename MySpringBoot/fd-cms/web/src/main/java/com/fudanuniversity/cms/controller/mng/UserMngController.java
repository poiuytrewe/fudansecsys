package com.fudanuniversity.cms.controller.mng;

import com.fudanuniversity.cms.business.service.CmsUserService;
import com.fudanuniversity.cms.business.service.CmsUserAccountService;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyScoreDate;
import com.fudanuniversity.cms.business.vo.study.plan.CmsUserStudyPlanQueryVo;
import com.fudanuniversity.cms.business.vo.user.*;
import com.fudanuniversity.cms.commons.model.JsonResult;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.model.web.LoginUser;
import com.fudanuniversity.cms.controller.BaseController;
import com.fudanuniversity.cms.repository.dao.CmsUserDao;
import com.fudanuniversity.cms.repository.entity.CmsScore;
import com.fudanuniversity.cms.repository.entity.CmsUser;
import com.fudanuniversity.cms.repository.query.CmsUserQuery;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.checkerframework.checker.units.qual.C;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@Api(tags = "用户")
@RestController
// @CrossOrigin
@RequestMapping(path = "/mng/user")
public class UserMngController extends BaseController {

    @Resource
    private CmsUserService cmsUserService;

    @Resource
    private CmsUserAccountService cmsUserAccountService;

    @Resource
    private CmsUserDao cmsUserDao;

    @GetMapping(path = "/list")
    public JsonResult<List<CmsUser>> queryUserList(@Valid CmsUserQueryVo queryVo, Paging paging) {
        LoginUser loginUser = getLoginUser();
        List<CmsUser> pagingResult = cmsUserService.forceQueryPagingResult(queryVo,paging);
        return JsonResult.buildSuccess(pagingResult);
    }

    @GetMapping(path = "/paging")
    public JsonResult<List<CmsUserVo>> queryPagingResult(@Valid CmsUserQueryVo queryVo, Paging paging, String name) {
        //CmsUserVo groupId已改 CmsUserQueryVo groupId已改

        LoginUser loginUser = getLoginUser();
        cmsUserService.checkManagePrivilege(loginUser.getStuId());
        PagingResult<CmsUserVo> pagingResult = cmsUserService.queryPagingResult(queryVo, paging, name);
        return JsonResult.buildSuccess(pagingResult);
    }

    @GetMapping(path = "/student")
    public JsonResult<List<CmsUserVo>> queryStudentResult(@Valid CmsUserQueryVo queryVo, Paging paging, String name) {
        PagingResult<CmsUserVo> pagingResult=cmsUserService.queryStudentResult(queryVo, paging, name);
        return JsonResult.buildSuccess(pagingResult);
    }

    @GetMapping(path = "/oneStudent")
    public JsonResult<CmsUserDetailVo> queryOneStudentDetail(@NotEmpty String stuId) {
        LoginUser loginUser = getLoginUser();
        CmsUserDetailVo userDetailVo = cmsUserService.queryUserDetail(stuId);
        return JsonResult.buildSuccess(userDetailVo);
    }

    @GetMapping(path = "/detail")
    public JsonResult<CmsUserDetailVo> queryUserDetail(@NotEmpty String stuId) {
        CmsUserDetailVo userDetailVo = cmsUserService.queryUserDetail(stuId);
        return JsonResult.buildSuccess(userDetailVo);
    }

    @GetMapping(path = "/studentList")
    @ResponseBody
    public JsonResult<?> queryStudentList (Integer type){ //获取不同类型的学生列表
        List<CmsUser> studentList=cmsUserService.queryStudentList(type);
        return JsonResult.buildSuccess(studentList);
    }

    //添加新用户，stuId是唯一的标识
    @PostMapping(path = "/add")
    public JsonResult<?> addNewUser(@Valid @RequestBody CmsUserAddVo userAddVo) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.checkSystemMng(loginUser.getStuId());
        cmsUserService.saveCmsUser(userAddVo);
        return JsonResult.buildSuccess();
    }

    @PostMapping(path = "/update")
    public JsonResult<?> updateUser(@Valid @RequestBody CmsUserUpdateMngVo updateVo) {
        cmsUserService.updateCmsUserById(updateVo);
        return JsonResult.buildSuccess();
    }

    @PostMapping(path = "/resetGroup")
    public JsonResult<?> resetStudentGroup(){
        cmsUserDao.resetGroupId();
        return JsonResult.buildSuccess();
    }

    @PostMapping(path = "/delete")
    public JsonResult<?> delete(@NotNull @Min(1L) Long id) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.checkSystemMng(loginUser.getStuId());
        cmsUserService.deleteCmsUserById(id);
        return JsonResult.buildSuccess();
    }

    @PostMapping(path="/reset")//强制重置用户密码
    public JsonResult<?> reset(@NotNull @Min(1L) Long id){
        LoginUser loginUser=getLoginUser();
        cmsUserService.checkSystemMng(loginUser.getStuId());//检查用户是否拥有系统管理员权限
        cmsUserAccountService.forceResetAccountPassword(id);
        return JsonResult.buildSuccess();
    }

}