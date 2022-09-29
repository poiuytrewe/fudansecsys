package com.fudanuniversity.cms.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fudanuniversity.cms.business.service.CmsRecorderService;
import com.fudanuniversity.cms.business.service.CmsUserService;
import com.fudanuniversity.cms.business.service.impl.CmsRecorderServiceImpl;
import com.fudanuniversity.cms.business.vo.recorder.*;
import com.fudanuniversity.cms.commons.model.JsonResult;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.model.web.LoginUser;
import com.fudanuniversity.cms.commons.util.UrlUtils;
import com.fudanuniversity.cms.repository.entity.CmsRecorder;
import com.fudanuniversity.cms.repository.mapper.CmsRecorderMapper;
import io.swagger.annotations.Api;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;


@Api(tags = "演讲记录安排")
@Controller
// @CrossOrigin
@RequestMapping(path = "/recorder")
public class RecorderController extends BaseController {

    @Resource
    private CmsRecorderService cmsRecorderService;

    @Resource
    private CmsUserService cmsUserService;

    @Resource
    private CmsRecorderMapper cmsRecorderMapper;

    @PostMapping(path = "/add")
    @ResponseBody
    public JsonResult<?> addRecorder() {
        LoginUser loginUser = getLoginUser();
        cmsUserService.checkTalkMng(loginUser.getStuId());//检查用户是否有讨论班权限
        cmsRecorderService.saveCmsRecorder();
        return JsonResult.buildSuccess();
    }

    @PostMapping(path = "/update")
    @ResponseBody
    public JsonResult<?> updateRecorder(@Valid @RequestBody CmsRecorderUpdateVo updateVo) {
        cmsRecorderService.updateCmsRecorderById(updateVo);
        return JsonResult.buildSuccess();
    }

    @PostMapping(path = "/updateBattle")
    @ResponseBody
    public JsonResult<?> updateBattleRecorder(@Valid @RequestBody CmsRecorderUpdateVo updateVo){
        cmsRecorderService.updateBattle(updateVo);
        return JsonResult.buildSuccess();
    }

    @PostMapping(path = "/delete")
    @ResponseBody
    public JsonResult<?> deleteRecorder(@Valid CmsRecorderVo cmsRecorderVo) {
        LoginUser loginUser = getLoginUser();
        Long id=cmsRecorderVo.getId();
        Integer groupId=cmsRecorderVo.getGroupId();
        cmsUserService.checkTalkMng(loginUser.getStuId());
        cmsRecorderService.deleteCmsRecorderById(id, groupId);
        return JsonResult.buildSuccess();
    }

    /**
     * 上传文稿
     */
    @PostMapping(path = "/recorderUpload")
    @ResponseBody
    public JsonResult<?> uploadRecorderFile(@Valid CmsRecorderUploadVo uploadVo) {
        cmsRecorderService.uploadRecorderFile(uploadVo);
        return JsonResult.buildSuccess();
    }

    @PostMapping(path = "/uploadSummary")
    @ResponseBody
    public JsonResult<?> uploadSummarizerFile(@Valid CmsRecorderUploadVo uploadVo) {
        cmsRecorderService.uploadSummarizerFile(uploadVo);
        return JsonResult.buildSuccess();
    }

    @GetMapping(path = "/paging")
    @ResponseBody
    public JsonResult<?> queryPagingResult(@Valid CmsRecorderQueryVo queryVo, Paging paging) { //这里似乎可以不用参数
        PagingResult<CmsRecorderVo> pagingResult = cmsRecorderService.queryPagingResult(queryVo, paging);
        return JsonResult.buildSuccess(pagingResult);
    }

    @GetMapping(path = "/downloadRecorder")
    @ResponseBody
    public void downloadRecorderFile(@Valid Long id, HttpServletResponse response) throws IOException {
        cmsRecorderService.downloadRecorderFile(id,response);
    }

    @GetMapping(path = "/downloadSummarizer")
    @ResponseBody
    public void downloadSummarizerFile(@Valid Long id, HttpServletResponse response) throws IOException {
        cmsRecorderService.downloadSummarizerFile(id,response);
    }
}
