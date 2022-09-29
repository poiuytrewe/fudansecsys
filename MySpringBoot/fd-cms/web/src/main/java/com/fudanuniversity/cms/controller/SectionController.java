package com.fudanuniversity.cms.controller;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fudanuniversity.cms.business.service.CmsSectionService;
import com.fudanuniversity.cms.business.service.CmsUserService;
import com.fudanuniversity.cms.business.vo.section.CmsSectionAddVo;
import com.fudanuniversity.cms.business.vo.section.CmsSectionUpdateVo;
import com.fudanuniversity.cms.business.vo.section.CmsSectionUploadVo;
import com.fudanuniversity.cms.business.vo.section.CmsSectionVo;
import com.fudanuniversity.cms.commons.model.JsonResult;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.model.web.LoginUser;
import io.swagger.annotations.Api;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@Api(tags = "专题演讲记录")
@Controller
// @CrossOrigin
@RequestMapping(path = "/section") //请求映射的地址
public class SectionController extends BaseController{

    @Resource //相当于依赖注入吧
    CmsSectionService cmsSectionService;

    @Resource
    CmsUserService cmsUserService;


    @GetMapping(path = "/paging")
    @ResponseBody //作用在方法上，用@ResponseBody注解，返回结果直接写在response body里，直接返回json数据
    public JsonResult<?> queryPagingResult(@Valid Paging paging){
        PagingResult<CmsSectionVo> pagingResult=cmsSectionService.queryPagingResult(paging);
        return JsonResult.buildSuccess(pagingResult);
    }

    @PostMapping(path = "/upload")
    @ResponseBody
    public JsonResult<?> uploadSectionFile(@Valid CmsSectionUploadVo uploadVo){
        LoginUser loginUser=getLoginUser();
        String stuId=loginUser.getStuId();
        cmsSectionService.uploadSectionFile(stuId,uploadVo);
        return JsonResult.buildSuccess();
    }

    @GetMapping(path = "/download")
    public void downloadSectionFile(Long id, HttpServletResponse response){
        cmsSectionService.downloadSectionFile(id,response);
    }

    @PostMapping(path = "/add")
    @ResponseBody
    public JsonResult<?> addSection(@Valid @RequestBody CmsSectionAddVo addVo){
        //@RequestBody主要用来接收前端传递给后端的json字符串中的数据的(请求体中的数据的)，所以只能发送POST请求。
        //GET方式无请求体，所以使用@RequestBody接收数据时，前端不能使用GET方式提交数据，而是用POST方式进行提交。
        cmsUserService.checkBackbone(addVo.getStuId());
        cmsSectionService.saveSection(addVo);
        return JsonResult.buildSuccess();
    }

    @PostMapping(path = "/update")
    @ResponseBody
    public JsonResult<?> updateSection(@Valid @RequestBody CmsSectionUpdateVo updateVo){
        cmsUserService.checkBackbone(updateVo.getStuId());
        LoginUser loginUser=getLoginUser();
        cmsSectionService.updateSection(loginUser.getStuId(),updateVo);
        return JsonResult.buildSuccess();
    }

    @PostMapping(path = "/delete")
    @ResponseBody
    public JsonResult<?> deleteSection(@Valid Long id){
        cmsSectionService.deleteSection(id);
        return JsonResult.buildSuccess();
    }
}
