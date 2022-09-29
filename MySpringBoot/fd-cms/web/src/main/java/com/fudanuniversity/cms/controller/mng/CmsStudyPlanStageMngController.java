package com.fudanuniversity.cms.controller.mng;

import com.fudanuniversity.cms.business.service.CmsStudyPlanStageService;
import com.fudanuniversity.cms.business.service.CmsUserService;
import com.fudanuniversity.cms.business.vo.study.plan.*;
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
 * CmsStudyPlanStageController
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 18:08:08
 */
@Api(tags = "管理员 - 培养计划阶段")
@RestController
// @CrossOrigin
@RequestMapping("/mng/study/plan/stage")
public class CmsStudyPlanStageMngController extends BaseController {

    @Resource
    private CmsStudyPlanStageService cmsStudyPlanStageService;

    @Resource
    private CmsUserService cmsUserService;

    @Operation(summary = "管理员新增培养计划阶段")
    @PostMapping("/add")
    public JsonResult<?> addCmsStudyPlanStage(@Valid @RequestBody CmsStudyPlanStageAddVo addVo) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.checkEducateMng(loginUser.getStuId());
        cmsStudyPlanStageService.addCmsStudyPlanStage(addVo);
        return JsonResult.buildSuccess();
    }

    @Operation(summary = "管理员更新培养计划阶段")
    @PostMapping("/update")
    public JsonResult<?> updateCmsStudyPlanStageById(@Valid @RequestBody CmsStudyPlanStageUpdateVo updateVo) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.checkEducateMng(loginUser.getStuId());
        cmsStudyPlanStageService.updateCmsStudyPlanStageById(updateVo);
        return JsonResult.buildSuccess();
    }

    @Operation(summary = "管理员批量更新处理培养计划阶段（调整顺序）")
    @PostMapping("/edit")
    public JsonResult<?> updateCmsStudyPlanStageById(@Valid @RequestBody List<CmsStudyPlanStageEditVo> editVoList) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.checkEducateMng(loginUser.getStuId());
        cmsStudyPlanStageService.editCmsStudyPlanStages(editVoList);
        return JsonResult.buildSuccess();
    }

    @Operation(summary = "管理员删除培养计划阶段")
    @PostMapping("/delete")
    public JsonResult<?> deleteCmsStudyPlanStageById(@NotNull @Min(1L) Long id) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.checkEducateMng(loginUser.getStuId());
        cmsStudyPlanStageService.deleteCmsStudyPlanStageById(id);
        return JsonResult.buildSuccess();
    }

    @Operation(summary = "管理员查询培养计划阶段分页列表")
    @GetMapping("/list")
    public JsonResult<List<CmsStudyPlanStageVo>> queryCmsStudyPlanStageList(@Valid CmsStudyPlanStageQueryVo queryVo) {
        List<CmsStudyPlanStageVo> pagingResult = cmsStudyPlanStageService.queryCmsStudyPlanStageList(queryVo);
        return JsonResult.buildSuccess(pagingResult);
    }
}