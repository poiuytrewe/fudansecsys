package com.fudanuniversity.cms.controller.mng;

import com.fudanuniversity.cms.business.service.CmsStudyPlanWorkService;
import com.fudanuniversity.cms.business.service.CmsUserService;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanWorkAddVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanWorkQueryVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanWorkUpdateVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanWorkVo;
import com.fudanuniversity.cms.commons.model.JsonResult;
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
 * CmsStudyPlanWorkController
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 18:08:08
 */
@Api(tags = "管理员 - 培养计划任务")
@RestController
// @CrossOrigin
@RequestMapping("/mng/study/plan/work")
public class CmsStudyPlanWorkMngController extends BaseController {

    @Resource
    private CmsStudyPlanWorkService cmsStudyPlanWorkService;

    @Resource
    private CmsUserService cmsUserService;

    @Operation(summary = "管理员新增培养计划任务")
    @PostMapping("/add")
    public JsonResult<?> saveCmsStudyPlanWork(@Valid @RequestBody CmsStudyPlanWorkAddVo addVo) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.checkEducateMng(loginUser.getStuId());
        cmsStudyPlanWorkService.saveCmsStudyPlanWork(addVo);
        return JsonResult.buildSuccess();
    }

    @Operation(summary = "管理员更新培养计划任务")
    @PostMapping("/update")
    public JsonResult<?> updateCmsStudyPlanWorkById(@Valid @RequestBody CmsStudyPlanWorkUpdateVo updateVo) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.checkEducateMng(loginUser.getStuId());
        cmsStudyPlanWorkService.updateCmsStudyPlanWorkById(updateVo);
        return JsonResult.buildSuccess();
    }

    @Operation(summary = "管理员删除培养计划任务")
    @PostMapping("/delete")
    public JsonResult<?> deleteCmsStudyPlanWorkById(@NotNull @Min(1L) Long id) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.checkEducateMng(loginUser.getStuId());
        cmsStudyPlanWorkService.deleteCmsStudyPlanWorkById(id);
        return JsonResult.buildSuccess();
    }

    /**
     * 根据条件查询信息列表
     */
    @Operation(summary = "管理员查询培养计划任务分页列表")
    @GetMapping("/list")
    public JsonResult<List<CmsStudyPlanWorkVo>> queryCmsStudyPlanWorkList(@Valid CmsStudyPlanWorkQueryVo queryVo) {
        List<CmsStudyPlanWorkVo> pagingResult = cmsStudyPlanWorkService.queryCmsStudyPlanWorkList(queryVo);
        return JsonResult.buildSuccess(pagingResult);
    }
}