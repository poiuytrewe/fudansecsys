package com.fudanuniversity.cms.business.service.impl;

import com.fudanuniversity.cms.business.service.CmsSectionService;
import com.fudanuniversity.cms.business.vo.section.CmsSectionAddVo;
import com.fudanuniversity.cms.business.vo.section.CmsSectionUpdateVo;
import com.fudanuniversity.cms.business.vo.section.CmsSectionUploadVo;
import com.fudanuniversity.cms.business.vo.section.CmsSectionVo;
import com.fudanuniversity.cms.commons.constant.CmsConstants;
import com.fudanuniversity.cms.commons.exception.BusinessException;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.model.query.SortColumn;
import com.fudanuniversity.cms.commons.model.query.SortMode;
import com.fudanuniversity.cms.repository.dao.CmsSectionDao;
import com.fudanuniversity.cms.repository.entity.CmsSection;
import com.fudanuniversity.cms.repository.query.CmsSectionQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service //标记当前是一个service类
public class CmsSectionServiceImpl implements CmsSectionService {
    private static final Logger logger = LoggerFactory.getLogger(CmsRecorderServiceImpl.class);

    @Resource
    CmsSectionDao cmsSectionDao;

    @Override
    public PagingResult<CmsSectionVo> queryPagingResult(Paging paging){
        PagingResult<CmsSectionVo> pagingResult=PagingResult.create(paging);
        CmsSectionQuery query=CmsSectionQuery.listQuery();
        Long count=cmsSectionDao.selectCountByParam(query);
        pagingResult.setTotal(count);
        if(count>0L){
            query.setOffset(paging.getOffset());
            query.setLimit(paging.getLimit());
            query.setSorts(SortColumn.create(CmsConstants.CreatedTimeColumn, SortMode.DESC));
            List<CmsSection> cmsSectionList=cmsSectionDao.selectListByParam(query);
            pagingResult.setRows(cmsSectionList, this::convertCmsSectionVo);
        }
        return pagingResult;
    }

    @Override
    public void uploadSectionFile(String stuId, CmsSectionUploadVo uploadVo){
        CmsSectionQuery cmsSectionQuery=CmsSectionQuery.singletonQuery();
        cmsSectionQuery.setId(uploadVo.getId());
        CmsSection c=cmsSectionDao.selectListByParam(cmsSectionQuery).get(0);
        if(!Objects.equals(c.getStuId(),stuId)){
            throw new BusinessException("不是专题创建者，无权上传文章");
        }

        CmsSection cmsSection=new CmsSection();
        cmsSection.setId(uploadVo.getId());
        boolean fileUpload=false;//用于记录文件是否上传完成
        MultipartFile file=uploadVo.getSectionFile();
        if(file!=null){
            try{
                byte[] bytes=file.getBytes();
                String fileName=file.getOriginalFilename();
                String fileType=file.getContentType();
                cmsSection.setSectionFile(bytes);
                cmsSection.setSectionFileName(fileName);
                cmsSection.setSectionFileType(fileType);
            }catch (IOException e){
                throw new BusinessException("上传文件出了错误，请稍后重试");
            }
            fileUpload=true;//代表上传完成
        }

        if(!fileUpload){
            throw new BusinessException("未上传任何文件");
        }

        cmsSection.setIsFile(1);
        cmsSectionDao.updateById(cmsSection);
    }

    @Override
    public void downloadSectionFile(Long id, HttpServletResponse response){
        OutputStream outputStream=null;

        try{
            CmsSection cmsSection=cmsSectionDao.queryDownloadSectionFile(id); //从后台提取相应的专题信息
            byte[] file=cmsSection.getSectionFile();
            String fileName=cmsSection.getSectionFileName();
            String fileType=cmsSection.getSectionFileType();

            response.setContentType(fileType);
            response.setHeader("Content-Disposition", "attachment;filename=\"" + URLEncoder.encode( fileName,"UTF-8") + "\"");

            outputStream=response.getOutputStream();
            outputStream.write(file);
            outputStream.flush();
        }catch (IOException e){
            logger.error("error",e);
        }finally {
            try{
                outputStream.close();
            }catch (IOException e){
                logger.error("error",e);
            }
        }
    }

    @Override
    public void saveSection(CmsSectionAddVo addVo){
        CmsSection cmsSection=new CmsSection();
        if(selectByDate(addVo.getDate())!=null){
            throw new BusinessException("请重新选择演讲时间");
        }
        cmsSection.setDate(addVo.getDate());
        cmsSection.setName(addVo.getName());
        cmsSection.setStuId(addVo.getStuId());
        cmsSection.setTopic(addVo.getTopic());
        cmsSection.setPaperRec(addVo.getPaperRec());
        cmsSection.setIsFile(0);
        cmsSectionDao.saveSection(cmsSection);
    }

    @Override
    public void updateSection(String stuId, CmsSectionUpdateVo updateVo){//需要检查更新的是不是本人
        if(selectByDate(updateVo.getDate())!=null){
            throw new BusinessException("请重新选择演讲时间");
        }
        CmsSectionQuery query=CmsSectionQuery.singletonQuery();
        query.setId(updateVo.getId());
        String updateId=cmsSectionDao.selectListByParam(query).get(0).getStuId();//这是对应专题的对应发布人的学号
        if(!Objects.equals(stuId,updateId)){//如果两者不相等，不进行更新
            throw new BusinessException("只有相应的发布者才能更新或删除");
        }

        CmsSection cmsSection=new CmsSection();
        cmsSection.setId(updateVo.getId());
        cmsSection.setTopic(updateVo.getTopic());
        cmsSection.setPaperRec(updateVo.getPaperRec());
        cmsSectionDao.updateById(cmsSection);
    }

    @Override
    public void deleteSection(Long id){
        cmsSectionDao.deleteById(id);
    }

    private CmsSectionVo convertCmsSectionVo(CmsSection cmsSection){
        CmsSectionVo sectionVo=new CmsSectionVo();
        sectionVo.setId(cmsSection.getId());
        sectionVo.setDate(cmsSection.getDate());
        sectionVo.setName(cmsSection.getName());
        sectionVo.setStuId(cmsSection.getStuId());
        sectionVo.setTopic(cmsSection.getTopic());
        sectionVo.setSectionFileName(cmsSection.getSectionFileName());
        sectionVo.setSectionFileType(cmsSection.getSectionFileType());
        sectionVo.setIsFile(cmsSection.getIsFile());
        sectionVo.setPaperRec(cmsSection.getPaperRec());
        return sectionVo;
    }

    private CmsSection selectByDate(Date date){//这个方法的作用是寻找是否用date相同的section
        return cmsSectionDao.selectByDate(date);
    }
}
