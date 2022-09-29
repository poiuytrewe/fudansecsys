package com.fudanuniversity.cms.controller.mng;

import com.fasterxml.jackson.databind.annotation.JsonValueInstantiator;
import com.fudanuniversity.cms.business.service.CmsProcessTinyCategoryService;
import com.fudanuniversity.cms.business.vo.study.plan.CmsProcessTinyCategoryVo;
import com.fudanuniversity.cms.commons.model.JsonResult;
import com.fudanuniversity.cms.controller.BaseController;
import com.fudanuniversity.cms.repository.entity.CmsProcessTinyCategory;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
// @CrossOrigin
@RequestMapping(path ="/mng/study/process/tinyCategory/")
public class CmsProcessTinyCategoryController extends BaseController {

    @Resource
    CmsProcessTinyCategoryService cmsProcessTinyCategoryService;

    @GetMapping(path = "/paging")
    public JsonResult<?> getTinyCategory(@Valid Integer type,Integer studyType){
        List<CmsProcessTinyCategory> categoryList=cmsProcessTinyCategoryService.getTinyCategory(type, studyType);
        return JsonResult.buildSuccess(categoryList);
    }

    @PostMapping(path = "/add")
    public JsonResult<?> addTinyCategory(@RequestBody CmsProcessTinyCategoryVo categoryVo){
        cmsProcessTinyCategoryService.addTinyCategory(categoryVo);
        return JsonResult.buildSuccess();
    }
}
