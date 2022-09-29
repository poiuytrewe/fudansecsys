package com.fudanuniversity.cms.controller.mng;

import com.fudanuniversity.cms.business.service.CmsDeviceService;
import com.fudanuniversity.cms.business.service.CmsUserService;
import com.fudanuniversity.cms.business.vo.device.*;
import com.fudanuniversity.cms.commons.model.JsonResult;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.model.web.LoginUser;
import com.fudanuniversity.cms.controller.BaseController;
import io.swagger.annotations.Api;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import javax.annotation.Resource;


@Api(tags = "管理员 - 设备")
@RestController
// @CrossOrigin
@RequestMapping(path = "/mng/device")
public class DeviceMngController extends BaseController {

    @Resource
    private CmsDeviceService cmsDeviceService;

    @Resource
    private CmsUserService cmsUserService;

    @PostMapping(path = "/add")
    public JsonResult<?> addNewDevice(@Valid @RequestBody CmsDeviceAddVo addVo) {//CmsDeviceAddVo已改
        LoginUser loginUser = getLoginUser();
        cmsUserService.checkDeviceMng(loginUser.getStuId());
        cmsDeviceService.saveCmsDevice(addVo);
        return JsonResult.buildSuccess();
    }

    @PostMapping(path = "/update")
    public JsonResult<?> updateDevice(@Valid @RequestBody CmsDeviceUpdateVo updateVo) {//CmsDeviceUpdateVo已改
        cmsDeviceService.updateCmsDeviceById(updateVo);
        return JsonResult.buildSuccess();
    }

    @PostMapping(path = "/delete")
    public JsonResult<?> deleteDevice(@NotNull @Min(1L) Long id) {
        LoginUser loginUser = getLoginUser();
        cmsUserService.checkDeviceMng(loginUser.getStuId());
        cmsDeviceService.deleteCmsDeviceById(id);
        return JsonResult.buildSuccess();
    }

    /**
     * 查看设备分页列表
     */
    @GetMapping(path = "/paging")
    public JsonResult<List<CmsDeviceVo>> queryPagingResult(@Valid CmsDeviceQueryVo queryVo, Paging paging, Long type, String selectName) {
        PagingResult<CmsDeviceVo> pagingResult = cmsDeviceService.queryPagingResult(queryVo, paging, type, selectName);
        return JsonResult.buildSuccess(pagingResult);
    }

    /**
     * 查看某一设备使用情况分页列表
     */
    @GetMapping(path = "/usage/paging")
    public JsonResult<?> queryUsagePagingResult(Long deviceId, Paging paging) {
        PagingResult<CmsDeviceUsageVo> pagingResult = cmsDeviceService.queryUsagePagingResult(deviceId, paging);
        return JsonResult.buildSuccess(pagingResult);
    }
}
