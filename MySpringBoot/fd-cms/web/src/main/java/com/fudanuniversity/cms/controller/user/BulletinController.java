package com.fudanuniversity.cms.controller.user;
//在这里，能对未读通知数量产生影响的——/read/all
import com.fudanuniversity.cms.business.service.CmsBulletinService;
import com.fudanuniversity.cms.business.vo.bulletin.CmsBulletinQueryVo;
import com.fudanuniversity.cms.business.vo.bulletin.CmsBulletinStateQueryVo;
import com.fudanuniversity.cms.business.vo.bulletin.CmsBulletinStateVo;
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

@Api(tags = "用户 - 通知")
// @CrossOrigin
@RestController
@RequestMapping(path = "/u/bulletin")
public class BulletinController extends BaseController {

    @Resource
    private CmsBulletinService cmsBulletinService;

    @Operation(summary = "用户标记所有通知已读")
    @PostMapping(path = "/read/all") //
    public JsonResult<?> readAllBulletin(@NotNull @Valid String stuId) {
        cmsBulletinService.readAllBulletin(stuId);
        return JsonResult.buildSuccess();
    }

    @Operation(summary = "用户查询通知列表")
    @GetMapping(path = "/paging")
    @ResponseBody
    public JsonResult<?> queryPagingResult(@Valid CmsBulletinQueryVo queryVo, Paging paging) { //这里是获取单个用户的所有通知记录，不管已读还是未读
        PagingResult<CmsBulletinVo> pagingResult = cmsBulletinService.queryPagingResult(queryVo, paging);
        return JsonResult.buildSuccess(pagingResult);
    }

    @Operation(summary = "用户查询通知已读未读情况")
    @GetMapping(path = "/state") //返回未读通知记录的数量
    @ResponseBody
    public Long queryCmsBulletinReadState(@Valid CmsBulletinStateQueryVo stateQueryVo) {
        Long count = cmsBulletinService.queryCmsBulletinReadState(stateQueryVo);
        return count;
    }
}
