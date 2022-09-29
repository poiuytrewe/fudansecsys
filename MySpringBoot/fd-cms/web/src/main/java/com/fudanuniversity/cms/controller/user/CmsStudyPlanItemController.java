package com.fudanuniversity.cms.controller.user;

import com.fudanuniversity.cms.business.service.CmsStudyPlanItemService;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanItemUserEditVo;
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
@Api(tags = "用户 - 用户分配的培养计划任务")
// @CrossOrigin
@RestController
@RequestMapping("/u/study/plan/allocation")
public class CmsStudyPlanItemController extends BaseController {

    @Resource
    private CmsStudyPlanItemService cmsStudyPlanItemService;

    @Operation(summary = "用户变更用户分配的培养计划任务状态")
    @PostMapping("/edit")
    public JsonResult<?> changeUserCmsStudyPlanAllocationStatus(
            @Valid @RequestBody CmsStudyPlanItemUserEditVo userEditVo) {
        LoginUser loginUser = getLoginUser();
        cmsStudyPlanItemService.editUserStudyPlanItem(loginUser.getUserId(), userEditVo);
        return JsonResult.buildSuccess();
    }
}