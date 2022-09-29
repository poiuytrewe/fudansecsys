package com.fudanuniversity.cms.controller.mng;

import com.fudanuniversity.cms.business.service.CmsStudyPlanService;
import com.fudanuniversity.cms.business.service.CmsUserService;
import com.fudanuniversity.cms.business.vo.study.plan.*;
import com.fudanuniversity.cms.commons.model.JsonResult;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.model.web.LoginUser;
import com.fudanuniversity.cms.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * CmsStudyPlanController
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 17:59:46
 */
@Api(tags = "管理员 - 培养计划")
// @CrossOrigin
@RestController
@RequestMapping("/mng/study/plan")
public class CmsStudyPlanMngController extends BaseController {

    @Resource
    private CmsStudyPlanService cmsStudyPlanService;

    @Resource
    private CmsUserService cmsUserService;

    @Operation(summary = "管理员新增培养计划")
    @PostMapping("/add")
    public JsonResult<?> saveCmsStudyPlan(@Valid @RequestBody CmsStudyPlanAddVo addVo) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.checkEducateMng(loginUser.getStuId());
        cmsStudyPlanService.saveCmsStudyPlan(addVo);
        return JsonResult.buildSuccess();
    }

    @Operation(summary = "管理员更新培养计划")
    @PostMapping("/update")
    public JsonResult<?> updateCmsStudyPlanById(@Valid @RequestBody CmsStudyPlanUpdateVo updateVo) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.checkEducateMng(loginUser.getStuId());
        cmsStudyPlanService.updateCmsStudyPlanById(updateVo);
        return JsonResult.buildSuccess();
    }

    @Operation(summary = "管理员删除培养计划")
    @PostMapping("/delete")
    public JsonResult<?> deleteCmsStudyPlanById(@NotNull @Min(1L) Long id) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.checkEducateMng(loginUser.getStuId());
        cmsStudyPlanService.deleteCmsStudyPlanById(id);
        return JsonResult.buildSuccess();
    }

    @Operation(summary = "管理员查询培养计划分页列表")
    @GetMapping("/paging")
    public JsonResult<List<CmsStudyPlanVo>> queryPagingResult(@Valid CmsStudyPlanQueryVo queryVo, Paging paging) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.checkEducateMng(loginUser.getStuId());
        PagingResult<CmsStudyPlanVo> pagingResult = cmsStudyPlanService.queryPagingResult(queryVo, paging);
        return JsonResult.buildSuccess(pagingResult);
    }

    @Operation(summary = "管理员预览培养计划")
    @GetMapping("/overview")
    public JsonResult<CmsStudyPlanOverviewVo> queryCmsStudyPlanOverview(@NotNull(message = "培养计划id不能为空") @Min(1L) Long id) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.checkEducateMng(loginUser.getStuId());
        CmsStudyPlanOverviewVo overview = cmsStudyPlanService.queryCmsStudyPlanOverview(id);
        return JsonResult.buildSuccess(overview);
    }

    @Operation(summary = "管理员将培养计划分配给用户")
    @PostMapping("/assign")
    public JsonResult<?> assignCmsStudyPlan(@Valid @RequestBody CmsStudyPlanAssignVo assignVo) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.checkEducateMng(loginUser.getStuId());
        cmsStudyPlanService.assignCmsStudyPlan(assignVo);
        return JsonResult.buildSuccess();
    }
}