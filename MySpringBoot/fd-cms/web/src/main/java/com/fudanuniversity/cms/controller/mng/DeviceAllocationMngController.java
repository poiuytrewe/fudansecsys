package com.fudanuniversity.cms.controller.mng;

import com.fudanuniversity.cms.business.service.CmsDeviceAllocationService;
import com.fudanuniversity.cms.business.service.CmsUserService;
import com.fudanuniversity.cms.business.vo.device.CmsDeviceAllocationReturnVo;
import com.fudanuniversity.cms.business.vo.device.CmsDeviceAllocationVo;
import com.fudanuniversity.cms.commons.model.JsonResult;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.model.web.LoginUser;
import com.fudanuniversity.cms.controller.BaseController;
import io.swagger.annotations.Api;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * CmsDeviceAllocationController
 * <p>
 * Created by Xinyue.Tang at 2021-05-03
 */
@Api(tags = "管理员 - 设备分配")
@RestController
// @CrossOrigin
@RequestMapping("/mng/device/allocation")
public class DeviceAllocationMngController extends BaseController {

    @Resource
    private CmsDeviceAllocationService cmsDeviceAllocationService;

    @Resource
    private CmsUserService cmsUserService;

    /**
     * 返还设备
     */
    @PostMapping("/return")//设备管理员强制归还某一个设备
    public JsonResult<?> returnDeviceAllocation(@Valid @RequestBody CmsDeviceAllocationReturnVo allocationReturnVo) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.checkDeviceMng(loginUser.getStuId());
        cmsDeviceAllocationService.returnDeviceAllocation(null, allocationReturnVo);
        return JsonResult.buildSuccess();
    }

    /**
     * 查询用户当前申请的设备列表
     */
    @GetMapping("/paging")
    public JsonResult<?> queryPagingResult(Long userId, Paging paging) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.checkDeviceMng(loginUser.getStuId());
        PagingResult<CmsDeviceAllocationVo> pagingResult
                = cmsDeviceAllocationService.queryPagingResult(userId, paging);
        return JsonResult.buildSuccess(pagingResult);
    }
}