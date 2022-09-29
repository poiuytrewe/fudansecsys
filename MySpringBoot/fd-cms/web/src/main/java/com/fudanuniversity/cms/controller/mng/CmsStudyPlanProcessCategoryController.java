package com.fudanuniversity.cms.controller.mng;

import com.fudanuniversity.cms.business.service.CmsStudyPlanProcessCategoryService;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanProcessCategoryVo;
import com.fudanuniversity.cms.commons.model.JsonResult;
import com.fudanuniversity.cms.controller.BaseController;
import com.fudanuniversity.cms.repository.entity.CmsStudyPlanProcessCategory;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import javax.annotation.Resource;
import java.util.List;

@RestController
// @CrossOrigin
@RequestMapping(path = "/mng/study/process/category")
public class CmsStudyPlanProcessCategoryController extends BaseController {
    @Resource
    CmsStudyPlanProcessCategoryService cmsStudyPlanProcessCategoryService;

    @GetMapping(path = "/paging")
    public JsonResult<?> getAllProcessCategory(@Valid Integer type, Integer studyType){
        List<CmsStudyPlanProcessCategory> categoryList=cmsStudyPlanProcessCategoryService.getProcessCategory(type,studyType);
        return JsonResult.buildSuccess(categoryList);
    }

    @PostMapping(path = "/add")
    public JsonResult addProcessCategory(@RequestBody CmsStudyPlanProcessCategoryVo categoryVo){
        cmsStudyPlanProcessCategoryService.addProcessCategory(categoryVo);
        return JsonResult.buildSuccess();
    }

    @PostMapping(path = "/delete")
    public void deleteById(@Valid Long id){
        cmsStudyPlanProcessCategoryService.deleteById(id);
    }

    @PostMapping(path = "/update")
    public JsonResult<?> updateProcessCategory(@RequestBody CmsStudyPlanProcessCategoryVo categoryVo){
        cmsStudyPlanProcessCategoryService.updateProcessCategory(categoryVo);
        return JsonResult.buildSuccess();
    }

    @PostMapping(path = "/reset")
    public JsonResult<?> resetProcessCategory(@Valid Integer type, Integer studyType){
        cmsStudyPlanProcessCategoryService.resetProcessCategory(type, studyType);
        return JsonResult.buildSuccess();
    }
}
