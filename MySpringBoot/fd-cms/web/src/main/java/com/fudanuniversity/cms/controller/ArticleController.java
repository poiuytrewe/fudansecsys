package com.fudanuniversity.cms.controller;

import com.fudanuniversity.cms.business.service.CmsArticleService;
import com.fudanuniversity.cms.business.service.CmsUserService;
import com.fudanuniversity.cms.business.vo.article.*;
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

@Api(tags = "文章")
@RestController
// @CrossOrigin
@RequestMapping(path = "/article")
public class ArticleController extends BaseController {

    @Resource
    private CmsArticleService cmsArticleService;

    @Resource
    private CmsUserService cmsUserService;

    @GetMapping(path = "/paging")
    @ResponseBody
    public JsonResult<?> getArticle(@Valid Paging paging) {
        PagingResult<CmsArticleVo> pagingResult = cmsArticleService.queryPagingResult(paging);
        return JsonResult.buildSuccess(pagingResult);
    }

    @PostMapping(path = "/add")
    public JsonResult<?> addArticle(@RequestBody @Valid CmsArticleAddVo articleAddVo) {
        LoginUser loginUser=getLoginUser();
        cmsUserService.checkPaperMng(loginUser.getStuId()); //查看是否有论文管理员权限
        cmsArticleService.saveCmsArticle(articleAddVo);
        return JsonResult.buildSuccess();
    }

    @PostMapping(path = "/edit")
    public JsonResult<?> editArticle(@RequestBody @Valid CmsArticleEditVo editVo) {
        LoginUser loginUser=getLoginUser();
        cmsUserService.checkPaperMng(loginUser.getStuId());
        cmsArticleService.editCmsArticleBy(editVo);
        return JsonResult.buildSuccess();
    }

    @PostMapping(path = "/delete")
    public JsonResult<?> deleteArticle(@NotNull @Min(1L) Long id) {
        LoginUser loginUser=getLoginUser();
        cmsUserService.checkPaperMng(loginUser.getStuId());
        cmsArticleService.deleteCmsArticleById(id);
        return JsonResult.buildSuccess();
    }

}
