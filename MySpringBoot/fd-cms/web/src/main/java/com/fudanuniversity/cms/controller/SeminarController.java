package com.fudanuniversity.cms.controller;

import com.fudanuniversity.cms.business.service.CmsSeminarService;
import com.fudanuniversity.cms.business.service.CmsUserService;
import com.fudanuniversity.cms.business.vo.recorder.CmsRecorderUploadVo;
import com.fudanuniversity.cms.business.vo.seminar.*;
import com.fudanuniversity.cms.commons.enums.UserRoleEnum;
import com.fudanuniversity.cms.commons.model.JsonResult;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.model.web.LoginUser;
import io.swagger.annotations.Api;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Api(tags = "演讲")
@RestController
// @CrossOrigin
@RequestMapping(path = "/seminar")
public class SeminarController extends BaseController {

    @Resource
    private CmsUserService cmsUserService;

    @Resource
    private CmsSeminarService cmsSeminarService;

    @PostMapping(path = "/add")
    public JsonResult<?> addNewSeminar(@Valid @RequestBody CmsSeminarAddVo addVo) {
        cmsSeminarService.addNewSeminar(addVo);
        return JsonResult.buildSuccess();
    }

    @PostMapping(path = "/update")
    public JsonResult<?> updateSeminar(@Valid @RequestBody CmsSeminarUpdateVo seminarUpdateVo) {
        LoginUser loginUser = getLoginUser();
        cmsSeminarService.updateSeminarById(seminarUpdateVo);
        return JsonResult.buildSuccess();
    }

    @PostMapping(path = "/delete")
    public JsonResult<?> deleteSeminar(@NotNull @Min(1L) Long id) {
        LoginUser loginUser = getLoginUser();
        cmsSeminarService.deleteCmsSeminarById(id);
        return JsonResult.buildSuccess();
    }

    @GetMapping(path = "/paging")
    public JsonResult<?> queryPagingResult(@Valid CmsSeminarQueryVo queryVo, Paging paging) {
        PagingResult<CmsSeminarVo> pagingResult = cmsSeminarService.queryPagingResult(queryVo, paging);
        return JsonResult.buildSuccess(pagingResult);
    }

    @PostMapping(path = "/upload")
    @ResponseBody
    public JsonResult<?> uploadRecorderFile(@Valid CmsSeminarUploadVo uploadVo) {
        cmsSeminarService.uploadSeminarFile(uploadVo);
        return  JsonResult.buildSuccess();
    }

    @GetMapping(path = "/download")
    public void downloadSeminarFile(Long id, HttpServletResponse response) {
        cmsSeminarService.downloadSeminarFile(id,response);
    }
}
