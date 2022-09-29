package com.fudanuniversity.cms.controller.user;

import com.fudanuniversity.cms.business.service.CmsStudyPlanWorkService;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanWorkQueryVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanWorkVo;
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
 * CmsStudyPlanWorkController
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 18:08:08
 */
@Api(tags = "用户 - 培养计划任务")
// @CrossOrigin
@RestController
@RequestMapping("/u/study/plan/work")
public class CmsStudyPlanWorkController extends BaseController {

    @Resource
    private CmsStudyPlanWorkService cmsStudyPlanWorkService;

    @Operation(summary = "用户查询培养计划任务分页列表")
    @GetMapping("/list")
    public JsonResult<List<CmsStudyPlanWorkVo>> queryCmsStudyPlanWorkList(@Valid CmsStudyPlanWorkQueryVo queryVo) {
        List<CmsStudyPlanWorkVo> pagingResult = cmsStudyPlanWorkService.queryCmsStudyPlanWorkList(queryVo);
        return JsonResult.buildSuccess(pagingResult);
    }
}