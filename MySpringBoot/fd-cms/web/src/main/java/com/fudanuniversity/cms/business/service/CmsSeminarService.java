package com.fudanuniversity.cms.business.service;

import com.fudanuniversity.cms.business.vo.seminar.*;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * CmsSeminarService
 * <p>
 * Created by Xinyue.Tang at 2021-05-02
 */
public interface CmsSeminarService {

    /**
     * 新增处理
     */
    void addNewSeminar(CmsSeminarAddVo addVo);

    /**
     * 根据id更新处理
     */
    void updateSeminarById(CmsSeminarUpdateVo seminarUpdateVo);

    /**
     * 查询最近的演讲
     */
    PagingResult<CmsSeminarVo> queryPagingResult(CmsSeminarQueryVo queryVo, Paging paging);

    /**
     * 根据id删除处理
     */
    void deleteCmsSeminarById(Long seminarId);

    void uploadSeminarFile(CmsSeminarUploadVo uploadVo);

    void downloadSeminarFile(Long id, HttpServletResponse response);

}