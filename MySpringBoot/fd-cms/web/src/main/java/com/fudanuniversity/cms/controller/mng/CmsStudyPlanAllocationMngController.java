package com.fudanuniversity.cms.controller.mng;

import com.fudanuniversity.cms.business.service.CmsStudyPlanAllocationService;
import com.fudanuniversity.cms.business.service.CmsUserService;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanAllocationInfoVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanAllocationOverviewVo;
import com.fudanuniversity.cms.commons.model.JsonResult;
import com.fudanuniversity.cms.commons.model.web.LoginUser;
import com.fudanuniversity.cms.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * CmsStudyPlanAllocationController
 * <p>
 * Created by Xinyue.Tang at 2021-05-07 11:39:06
 */
@Api(tags = "管理员 - 培养计划分配")
// @CrossOrigin
@RestController
@RequestMapping("/mng/study/plan/allocation")
public class CmsStudyPlanAllocationMngController extends BaseController {

    @Resource
    private CmsStudyPlanAllocationService cmsStudyPlanAllocationService;

    @Resource
    private CmsUserService cmsUserService;

    @Operation(summary = "管理员查询用户分配的培养计划任务完成情况")
    @GetMapping("/info/list")
    public JsonResult<List<CmsStudyPlanAllocationInfoVo>> queryAllocationInfoList(
            @NotNull(message = "培养计划id不能为空") @Min(1L) Long id) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.checkEducateMng(loginUser.getStuId());
        List<CmsStudyPlanAllocationInfoVo> infoVoList = cmsStudyPlanAllocationService.queryAllocationInfoList(id);
        return JsonResult.buildSuccess(infoVoList);
    }

    @Operation(summary = "管理员查询某个用户分配培养计划任务完成情况")
    @GetMapping("/info")
    public JsonResult<CmsStudyPlanAllocationInfoVo> queryAllocationInfo(
            @NotNull(message = "用户id不能为空") @Min(1L) Long userId,
            @NotNull(message = "培养计划id不能为空") @Min(1L) Long planId) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.checkEducateMng(loginUser.getStuId());
        CmsStudyPlanAllocationInfoVo allocationInfoVo = cmsStudyPlanAllocationService.queryAllocationInfo(userId, planId);
        return JsonResult.buildSuccess(allocationInfoVo);
    }

    @Operation(summary = "管理员删除用户分配的培养计划任务")
    @PostMapping("/delete")
    public JsonResult<?> deleteCmsStudyPlanAllocationById(@NotNull @Min(1L) Long id) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.checkEducateMng(loginUser.getStuId());
        cmsStudyPlanAllocationService.deleteCmsStudyPlanAllocationById(id);
        return JsonResult.buildSuccess();
    }

    @Operation(summary = "管理员预览培养计划")
    @GetMapping("/overview")
    public JsonResult<CmsStudyPlanAllocationOverviewVo> queryCmsStudyPlanOverview(
            @NotNull(message = "培养计划id不能为空") @Min(1L) Long planId) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.checkEducateMng(loginUser.getStuId());
        CmsStudyPlanAllocationOverviewVo overview = cmsStudyPlanAllocationService.queryCmsStudyPlanOverview(planId);
        return JsonResult.buildSuccess(overview);
    }


    @Operation(summary = "管理员预览用户分配的培养计划任务")
    @GetMapping("/user/overview")
    public JsonResult<CmsStudyPlanAllocationOverviewVo> queryUserCmsStudyPlanOverview(
            @NotNull(message = "用户id不能为空") @Min(1L) Long userId,
            @NotNull(message = "培养计划id不能为空") @Min(1L) Long planId) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.checkEducateMng(loginUser.getStuId());
        CmsStudyPlanAllocationOverviewVo overview = cmsStudyPlanAllocationService.queryUserAllocationOverview(userId, planId);
        return JsonResult.buildSuccess(overview);
    }
}