package com.fudanuniversity.cms.business.service;

import com.fudanuniversity.cms.business.vo.recorder.*;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * CmsRecorderService
 * <p>
 * Created by Xinyue.Tang at 2021-05-02
 */
public interface CmsRecorderService {

    /**
     * 保存处理
     */
    void saveCmsRecorder();
//
//    /**
//     * 根据id更新处理
//     */
    void updateCmsRecorderById(CmsRecorderUpdateVo updateVo);
//
//    /**
//     * 根据id删除处理
//     */

    void updateBattle(CmsRecorderUpdateVo updateVo);

    void deleteCmsRecorderById(Long id, Integer groupId);
//
//    /**
//     * 上传文稿
//     */
    void uploadRecorderFile(CmsRecorderUploadVo uploadVo);

    void uploadSummarizerFile(CmsRecorderUploadVo uploadVo);

    /**
     * 分页查询数据列表
     */
    PagingResult<CmsRecorderVo> queryPagingResult(CmsRecorderQueryVo queryVo, Paging paging);

    /**
     * 下载文稿
     */
    void downloadRecorderFile(Long id, HttpServletResponse response) throws IOException;

    void downloadSummarizerFile(Long id, HttpServletResponse response) throws IOException;
}