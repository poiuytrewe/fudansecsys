package com.fudanuniversity.cms.business.service;
import com.fudanuniversity.cms.business.vo.section.CmsSectionAddVo;
import com.fudanuniversity.cms.business.vo.section.CmsSectionUpdateVo;
import com.fudanuniversity.cms.business.vo.section.CmsSectionUploadVo;
import com.fudanuniversity.cms.business.vo.section.CmsSectionVo;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;

import javax.servlet.http.HttpServletResponse;

public interface CmsSectionService {

    PagingResult<CmsSectionVo> queryPagingResult(Paging paging);

    void uploadSectionFile(String stuId, CmsSectionUploadVo uploadVo);//stuId的作用是用于验证是否有权限上传

    void downloadSectionFile(Long id, HttpServletResponse response);

    void saveSection(CmsSectionAddVo addVo);

    void updateSection(String stuId,CmsSectionUpdateVo updateVo);

    void deleteSection(Long id);
}
