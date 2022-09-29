package com.fudanuniversity.cms.business.service.impl;

import com.fudanuniversity.cms.business.component.CmsUserComponent;
import com.fudanuniversity.cms.business.service.CmsSeminarService;
import com.fudanuniversity.cms.business.vo.seminar.*;
import com.fudanuniversity.cms.commons.constant.CmsConstants;
import com.fudanuniversity.cms.commons.exception.BusinessException;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.model.query.SortColumn;
import com.fudanuniversity.cms.commons.model.query.SortMode;
import com.fudanuniversity.cms.commons.util.AssertUtils;
import com.fudanuniversity.cms.commons.util.ValueUtils;
import com.fudanuniversity.cms.repository.dao.CmsSeminarDao;
import com.fudanuniversity.cms.repository.entity.CmsRecorder;
import com.fudanuniversity.cms.repository.entity.CmsSeminar;
import com.fudanuniversity.cms.repository.entity.CmsUser;
import com.fudanuniversity.cms.repository.query.CmsSeminarQuery;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
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
import java.util.Map;

/**
 * CmsSeminarService 实现类
 * <p>
 * Created by Xinyue.Tang at 2021-05-02
 */
@Service
public class CmsSeminarServiceImpl implements CmsSeminarService {

    private static final Logger logger = LoggerFactory.getLogger(CmsSeminarServiceImpl.class);

    @Resource
    private CmsSeminarDao cmsSeminarDao;

    @Resource
    private CmsUserComponent cmsUserComponent;

    @Override
    public void addNewSeminar(CmsSeminarAddVo addVo) {
        CmsSeminar cmsSeminar = new CmsSeminar();
        cmsSeminar.setGroupId(addVo.getGroupId());
        cmsSeminar.setDate(new Date());
        cmsSeminar.setIsFile(0);
        cmsSeminarDao.insert(cmsSeminar);
    }

    @Override
    public void updateSeminarById(CmsSeminarUpdateVo updateVo) {
        Long seminarId = updateVo.getId();
        checkCmsSeminarExists(seminarId);

        CmsSeminar updater = new CmsSeminar();
        updater.setId(seminarId);
        updater.setSpeakerName(updateVo.getSpeakerName());
        updater.setSpeakerStuId(updateVo.getSpeakerStuId());
        updater.setModifyTime(new Date());
        int affect = cmsSeminarDao.updateById(updater);
        logger.info("更新CmsSeminar affect:{}, updater: {}", affect, updater);
        AssertUtils.state(affect == 1);
    }

    @Override
    public PagingResult<CmsSeminarVo> queryPagingResult(CmsSeminarQueryVo queryVo, Paging paging) {
        PagingResult<CmsSeminarVo> pagingResult = PagingResult.create(paging);

        CmsSeminarQuery query = new CmsSeminarQuery();
        query.setId(queryVo.getId());
        query.setSpeakerName(queryVo.getSpeakerName());
        query.setSpeakerStuId(queryVo.getSpeakerStuId());
        query.setDate(queryVo.getDate());
        Long total = cmsSeminarDao.selectCountByParam(query);
        pagingResult.setTotal(total);

        if (total > 0L) {
            query.setOffset(paging.getOffset());
            query.setPaging(paging);
            query.setSorts(SortColumn.create(CmsConstants.CreatedTimeColumn, SortMode.DESC));
            List<CmsSeminar> seminars = cmsSeminarDao.selectListByParam(query);
            pagingResult.setRows(seminars, this::convertCmsSeminarVo);
        }

        return pagingResult;
    }

    /**
     * 根据id删除处理
     */
    private CmsSeminarVo convertCmsSeminarVo(CmsSeminar cmsSeminar){
        CmsSeminarVo cmsSeminarVo=new CmsSeminarVo();
        cmsSeminarVo.setId(cmsSeminar.getId());
        cmsSeminarVo.setDate(cmsSeminar.getDate());
        cmsSeminarVo.setGroupId(cmsSeminar.getGroupId());
        cmsSeminarVo.setSpeakerName(cmsSeminar.getSpeakerName());
        cmsSeminarVo.setSpeakerStuId(cmsSeminar.getSpeakerStuId());
        cmsSeminarVo.setFileName(cmsSeminar.getFileName());
        cmsSeminarVo.setIsFile(cmsSeminar.getIsFile());
        cmsSeminarVo.setModifyTime(cmsSeminar.getModifyTime());
        return cmsSeminarVo;
    }

    @Override
    public void deleteCmsSeminarById(Long seminarId) {
        checkCmsSeminarExists(seminarId);
        int affect = cmsSeminarDao.deleteById(seminarId);
        logger.info("删除CmsSeminar affect:{}, id: {}", affect, seminarId);
    }

    private void checkCmsSeminarExists(Long seminarId) {
        CmsSeminarQuery query = new CmsSeminarQuery();
        query.setId(seminarId);
        List<CmsSeminar> seminars = cmsSeminarDao.selectListByParam(query);
        if (CollectionUtils.isEmpty(seminars)) {
            throw new BusinessException("演讲记录不存在");
        }
    }

    @Override
    public void uploadSeminarFile(CmsSeminarUploadVo uploadVo){
        CmsSeminar cmsSeminar=new CmsSeminar();

        cmsSeminar.setId(uploadVo.getId());

        boolean fileUpload=false;

        MultipartFile file = uploadVo.getFile();
        if(file!=null){
            try{
                byte[] fileContent=file.getBytes();
                String fileName=file.getOriginalFilename();
                String fileType=file.getContentType();
                cmsSeminar.setFileName(fileName);
                cmsSeminar.setFileType(fileType);
                cmsSeminar.setFile(fileContent);
            }catch (IOException e){
                throw new BusinessException("上传文件出了错误，请稍后重试");
            }
            fileUpload=true;
        }
        if(!fileUpload){
            throw new BusinessException("未上传任何文件");
        }
        cmsSeminar.setIsFile(1);
        cmsSeminar.setModifyTime(new Date());
        int affect=cmsSeminarDao.updateById(cmsSeminar);
        AssertUtils.state(affect==1);
    }

    @Override
    public void downloadSeminarFile(Long id, HttpServletResponse response){
        OutputStream outputStream=null;

        try{
            CmsSeminar cmsSeminar = cmsSeminarDao.selectFile(id);//得到想要的cmsRecorder
            byte[] file=cmsSeminar.getFile();//从数据库得到的文件
            //file已经成功提取出来
            String fileType=cmsSeminar.getFileType();//文件类型
            String fileName=cmsSeminar.getFileName();//文件名称

            response.setContentType(fileType);
            response.setHeader("Content-Disposition", "attachment;filename=\"" + URLEncoder.encode( fileName,"UTF-8") + "\"");

            outputStream=response.getOutputStream();
            outputStream.write(file);
            outputStream.flush();

        } catch (IOException e) {
            logger.error("error",e);
        }finally {
            try {
                outputStream.close();
            }catch (IOException e){
                logger.error("error",e);
            }
        }
    }
}