package com.fudanuniversity.cms.controller.mng;

import com.fudanuniversity.cms.business.service.CmsStudyPlanItemService;
import com.fudanuniversity.cms.business.service.CmsUserService;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanItemEditVo;
import com.fudanuniversity.cms.commons.model.JsonResult;
import com.fudanuniversity.cms.commons.model.web.LoginUser;
import com.fudanuniversity.cms.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * CmsStudyPlanAllocationController
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 18:08:08
 */
@Api(tags = "管理员 - 用户分配的培养计划任务")
// @CrossOrigin
@RestController
@RequestMapping("/mng/study/plan/allocation")
public class CmsStudyPlanItemMngController extends BaseController {

    @Resource
    private CmsStudyPlanItemService cmsStudyPlanItemService;

    @Resource
    private CmsUserService cmsUserService;

    @Operation(summary = "管理员变更用户分配的培养计划任务状态")
    @PostMapping("/edit")
    public JsonResult<?> editAllocation(
            @Valid @RequestBody CmsStudyPlanItemEditVo editVo) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.checkEducateMng(loginUser.getStuId());
        cmsStudyPlanItemService.editStudyPlanItem(editVo);
        return JsonResult.buildSuccess();
    }
}