package com.fudanuniversity.cms.controller.mng;

import com.fudanuniversity.cms.business.service.CmsLabPublishmentService;
import com.fudanuniversity.cms.commons.model.JsonResult;
import com.fudanuniversity.cms.controller.BaseController;
import com.fudanuniversity.cms.repository.entity.CmsLabPublishment;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
// @CrossOrigin
@RequestMapping(path = "/lab")
public class CmsLabController extends BaseController {

    @Resource
    CmsLabPublishmentService cmsLabPublishmentService;

    @GetMapping(path = "/labData")
    public JsonResult<?> queryLabData(){
        List<CmsLabPublishment> labData = cmsLabPublishmentService.queryLabData();
        return JsonResult.buildSuccess(labData);

    }

    @PostMapping(path = "/deleteLabData")
    public JsonResult<?> deleteLabData(@Valid Long id){
        cmsLabPublishmentService.deleteLabData(id);
        return JsonResult.buildSuccess();
    }
}
