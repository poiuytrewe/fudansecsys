package com.fudanuniversity.cms.controller.user;

import com.fudanuniversity.cms.business.service.CmsStudyPlanStageService;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanStageQueryVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanStageVo;
import com.fudanuniversity.cms.commons.model.JsonResult;
import com.fudanuniversity.cms.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * CmsStudyPlanStageController
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 18:08:08
 */
@Api(tags = "用户 - 培养计划阶段")
// @CrossOrigin
@RestController
@RequestMapping("/u/study/plan/stage")
public class CmsStudyPlanStageController extends BaseController {

    @Resource
    private CmsStudyPlanStageService cmsStudyPlanStageService;

    @Operation(summary = "用户查询培养计划阶段分页列表")
    @GetMapping("/list")
    public JsonResult<List<CmsStudyPlanStageVo>> queryCmsStudyPlanStageList(@Valid CmsStudyPlanStageQueryVo queryVo) {
        List<CmsStudyPlanStageVo> pagingResult = cmsStudyPlanStageService.queryCmsStudyPlanStageList(queryVo);
        return JsonResult.buildSuccess(pagingResult);
    }
}