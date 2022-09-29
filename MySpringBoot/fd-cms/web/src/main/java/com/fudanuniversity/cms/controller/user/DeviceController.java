package com.fudanuniversity.cms.controller.user;

import com.fudanuniversity.cms.business.service.CmsDeviceService;
import com.fudanuniversity.cms.business.vo.device.CmsDeviceQueryVo;
import com.fudanuniversity.cms.business.vo.device.CmsDeviceUsageVo;
import com.fudanuniversity.cms.business.vo.device.CmsDeviceVo;
import com.fudanuniversity.cms.commons.model.JsonResult;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.controller.BaseController;
import io.swagger.annotations.Api;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "用户 - 设备")
// @CrossOrigin
@RestController
@RequestMapping(path = "/u/device")
public class DeviceController extends BaseController {

    @Resource
    private CmsDeviceService cmsDeviceService;

    /**
     * 查看设备分页列表
     */
    @GetMapping(path = "/paging")
    public JsonResult<?> queryPagingResult(@Valid CmsDeviceQueryVo queryVo, Paging paging, Long type,String selectName) {
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
