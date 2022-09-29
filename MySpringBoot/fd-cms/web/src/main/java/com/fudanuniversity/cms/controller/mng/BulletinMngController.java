package com.fudanuniversity.cms.controller.mng;
//对未读通知数量产生影响—— /add /update /delete
import com.fudanuniversity.cms.business.service.CmsBulletinService;
import com.fudanuniversity.cms.business.service.CmsUserService;
import com.fudanuniversity.cms.business.vo.bulletin.CmsBulletinAddVo;
import com.fudanuniversity.cms.business.vo.bulletin.CmsBulletinEditVo;
import com.fudanuniversity.cms.business.vo.bulletin.CmsBulletinQueryVo;
import com.fudanuniversity.cms.business.vo.bulletin.CmsBulletinVo;
import com.fudanuniversity.cms.commons.model.JsonResult;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.model.web.LoginUser;
import com.fudanuniversity.cms.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Api(tags = "管理员 - 通知")
// @CrossOrigin
@RestController
@RequestMapping(path = "/mng/bulletin")
public class BulletinMngController extends BaseController {

    @Resource
    private CmsBulletinService cmsBulletinService;

    @Resource
    private CmsUserService cmsUserService;

    @Operation(summary = "管理员新增通知")
    @PostMapping(path = "/add")
    public JsonResult<?> addNewBulletin(@RequestBody @Valid CmsBulletinAddVo addVo) {
        cmsBulletinService.addNewBulletin(addVo);
        return JsonResult.buildSuccess();
    }

    @Operation(summary = "管理员修改通知")
    @PostMapping(path = "/update")
    public JsonResult<?> editBulletin(@NotNull Long id) {
        cmsBulletinService.editBulletin(id);
        return JsonResult.buildSuccess();
    }

    @Operation(summary = "管理员删除通知")
    @PostMapping(path = "/delete")
    public JsonResult<?> deleteBulletin(@NotNull Long id) {
        cmsBulletinService.deleteCmsBulletinById(id);
        return JsonResult.buildSuccess();
    }
}
