package com.fudanuniversity.cms.business.service.impl;

import com.fudanuniversity.cms.business.component.CmsUserComponent;
import com.fudanuniversity.cms.business.service.CmsRecorderService;
import com.fudanuniversity.cms.business.vo.recorder.*;
import com.fudanuniversity.cms.commons.constant.CmsConstants;
import com.fudanuniversity.cms.commons.exception.BusinessException;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.model.query.SortColumn;
import com.fudanuniversity.cms.commons.model.query.SortMode;
import com.fudanuniversity.cms.commons.util.AssertUtils;
import com.fudanuniversity.cms.commons.util.DateExUtils;
import com.fudanuniversity.cms.commons.util.ValueUtils;
import com.fudanuniversity.cms.repository.dao.CmsRecorderDao;
import com.fudanuniversity.cms.repository.entity.CmsRecorder;
import com.fudanuniversity.cms.repository.entity.CmsUser;
import com.fudanuniversity.cms.repository.query.CmsRecorderQuery;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.C;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * CmsRecorderService 实现类
 * <p>
 * Created by Xinyue.Tang at 2021-05-02
 */
@Service
public class CmsRecorderServiceImpl implements CmsRecorderService {

    private static final Logger logger = LoggerFactory.getLogger(CmsRecorderServiceImpl.class);

    @Resource
    private CmsRecorderDao cmsRecorderDao;

    @Resource
    private CmsUserComponent cmsUserComponent;

    private List<String> recorderName;

   private String choseRecorderName(List<String> recorderName){
        int recorderNum;
        if(recorderName.size()>1){
            recorderNum=new Random().nextInt(recorderName.size()); //随机数的下标
        }
        else {
            recorderNum=0;
        }
        String trueName=recorderName.get(recorderNum);
        if(recorderName.size()>1){
            recorderName.remove(recorderNum);
        }
        return trueName;
   }

    /**
     * 保存处理
     */
    @Override
    @Scheduled(cron = "0 0 9 ? * MON", zone = "Asia/Shanghai")
    public void saveCmsRecorder() {
        Date recorderDate = new Date();
        System.out.println(recorderDate);
        CmsRecorderQuery query = CmsRecorderQuery.singletonQuery();
        query.setDate(recorderDate);
        List<CmsRecorder> cmsRecorders = cmsRecorderDao.selectInfoListByParam(query);
        if (CollectionUtils.isNotEmpty(cmsRecorders)) {//查看当天是否已经有演讲记录
            throw new BusinessException("[" + DateExUtils.formatDate(recorderDate) + "]当天已经存在演讲记录安排");
        }

        recorderName=new ArrayList<String>(){
            {
                this.add("戴嘉润");
                this.add("廉轲轲");
                this.add("彭诗言");
                this.add("谈心");
                this.add("李帅");
                this.add("施游堃");
                this.add("张智博");
                this.add("江喆越");
            }
        };

        CmsRecorder cmsRecorder1 = new CmsRecorder();
        CmsRecorder cmsRecorder2 = new CmsRecorder();
        CmsRecorder cmsRecorder3 = new CmsRecorder();
        CmsRecorder cmsRecorder4 = new CmsRecorder();

        cmsRecorder1.setDate(recorderDate);
        cmsRecorder2.setDate(recorderDate);
        cmsRecorder3.setDate(recorderDate);
        cmsRecorder4.setDate(recorderDate);

        cmsRecorder1.setGroupId(1);
        cmsRecorder1.setIsSummarizerFile(0);
        cmsRecorder1.setIsRecorderFile(0);
        cmsRecorder1.setBaseScore(0);
        cmsRecorder1.setIsBattle(0);
        cmsRecorder1.setRecorder1Name(choseRecorderName(recorderName));
        cmsRecorder1.setRecorder2Name(choseRecorderName(recorderName));

        cmsRecorder2.setGroupId(2);
        cmsRecorder2.setIsSummarizerFile(0);
        cmsRecorder2.setIsRecorderFile(0);
        cmsRecorder2.setBaseScore(0);
        cmsRecorder2.setIsBattle(0);
        cmsRecorder2.setRecorder1Name(choseRecorderName(recorderName));
        cmsRecorder2.setRecorder2Name(choseRecorderName(recorderName));

        cmsRecorder3.setGroupId(3);
        cmsRecorder3.setIsSummarizerFile(0);
        cmsRecorder3.setIsRecorderFile(0);
        cmsRecorder3.setBaseScore(0);
        cmsRecorder3.setIsBattle(0);
        cmsRecorder3.setRecorder1Name(choseRecorderName(recorderName));
        cmsRecorder3.setRecorder2Name(choseRecorderName(recorderName));

        cmsRecorder4.setGroupId(4);
        cmsRecorder4.setIsSummarizerFile(0);
        cmsRecorder4.setIsRecorderFile(0);
        cmsRecorder4.setBaseScore(0);
        cmsRecorder4.setIsBattle(0);
        cmsRecorder4.setRecorder1Name(choseRecorderName(recorderName));
        cmsRecorder4.setRecorder2Name(choseRecorderName(recorderName));

        cmsRecorderDao.insert(cmsRecorder1);
        cmsRecorderDao.insert(cmsRecorder2);
        cmsRecorderDao.insert(cmsRecorder3);
        cmsRecorderDao.insert(cmsRecorder4);
    }

    private final static byte[] EMPTY_BYTE = new byte[0];

    /**
     * 根据id更新处理
     */
    @Override
    public void updateCmsRecorderById(CmsRecorderUpdateVo updateVo) {
        Long recorderId = updateVo.getId();//获得更新的辅读记录id
        CmsRecorderQuery query = CmsRecorderQuery.singletonQuery();
        query.setId(recorderId);
        List<CmsRecorder> cmsRecorders = cmsRecorderDao.selectInfoListByParam(query);
        if (CollectionUtils.isEmpty(cmsRecorders)) {
            throw new BusinessException("演讲记录安排不存在");
        }
        CmsRecorder cmsRecorder = cmsRecorders.get(0);//需要更新的记录

        CmsRecorder updater = new CmsRecorder();

        updater.setId(recorderId);
        updater.setRecorder1Name(ValueUtils.defaultString(updateVo.getRecorder1Name()));
        updater.setRecorder2Name(ValueUtils.defaultString(updateVo.getRecorder2Name()));
        updater.setSummarizerName(ValueUtils.defaultString(updateVo.getSummarizerName()));
        updater.setRecorder(ValueUtils.defaultString(updateVo.getRecorder()));
        updater.setBaseScore(updateVo.getBaseScore());
        updater.setIsBattle(updateVo.getIsBattle());
        updater.setModifyTime(new Date());
        int affect = cmsRecorderDao.updateById(updater);
        logger.info("更新CmsRecorder affect:{}, updater: {}", affect, updater);
    }

    @Override
    public void updateBattle(CmsRecorderUpdateVo updateVo){
        Long recorderId = updateVo.getId();//获得更新的辅读记录id
        CmsRecorderQuery query = CmsRecorderQuery.singletonQuery();
        query.setId(recorderId);
        List<CmsRecorder> cmsRecorders = cmsRecorderDao.selectInfoListByParam(query);
        if (CollectionUtils.isEmpty(cmsRecorders)) {
            throw new BusinessException("演讲记录安排不存在");
        }
        CmsRecorder updater = cmsRecorders.get(0);//需要更新的记录
        //这里只需要改变isBattle的值就可以，一更新就要更新4组
        CmsRecorder cmsRecorder1=new CmsRecorder();
        CmsRecorder cmsRecorder2=new CmsRecorder();
        CmsRecorder cmsRecorder3=new CmsRecorder();
        CmsRecorder cmsRecorder4=new CmsRecorder();

        cmsRecorder1.setIsBattle(1);
        cmsRecorder2.setIsBattle(1);
        cmsRecorder3.setIsBattle(1);
        cmsRecorder4.setIsBattle(1);

        Date date=new Date();
        cmsRecorder1.setModifyTime(date);
        cmsRecorder2.setModifyTime(date);
        cmsRecorder3.setModifyTime(date);
        cmsRecorder4.setModifyTime(date);

        Integer groupId=updater.getGroupId();
        Long id=updater.getId();

        if(groupId==1){
            cmsRecorder1.setId(id);
            cmsRecorder2.setId(id+1);
            cmsRecorder3.setId(id+2);
            cmsRecorder4.setId(id+3);
        }
        else if(groupId==2){
            cmsRecorder1.setId(id-1);
            cmsRecorder2.setId(id);
            cmsRecorder3.setId(id+1);
            cmsRecorder4.setId(id+2);
        }
        else if(groupId==3){
            cmsRecorder1.setId(id-2);
            cmsRecorder2.setId(id-1);
            cmsRecorder3.setId(id);
            cmsRecorder4.setId(id+1);
        }
        else if(groupId==4){
            cmsRecorder1.setId(id-3);
            cmsRecorder2.setId(id-2);
            cmsRecorder3.setId(id-1);
            cmsRecorder4.setId(id);
        }
        cmsRecorderDao.updateById(cmsRecorder1);
        cmsRecorderDao.updateById(cmsRecorder2);
        cmsRecorderDao.updateById(cmsRecorder3);
        cmsRecorderDao.updateById(cmsRecorder4);
    }
//
//    /**
//     * 根据id删除处理
//     */
    @Override
    public void deleteCmsRecorderById(Long id, Integer groupId) {
        if(groupId==1){
            cmsRecorderDao.deleteById(id);
            cmsRecorderDao.deleteById(id+1);
            cmsRecorderDao.deleteById(id+2);
            cmsRecorderDao.deleteById(id+3);
        }
        else if(groupId==2){
            cmsRecorderDao.deleteById(id-1);
            cmsRecorderDao.deleteById(id);
            cmsRecorderDao.deleteById(id+1);
            cmsRecorderDao.deleteById(id+2);
        }
        else if(groupId==3){
            cmsRecorderDao.deleteById(id-2);
            cmsRecorderDao.deleteById(id-1);
            cmsRecorderDao.deleteById(id);
            cmsRecorderDao.deleteById(id+1);
        }
        else if(groupId==4){
            cmsRecorderDao.deleteById(id-3);
            cmsRecorderDao.deleteById(id-2);
            cmsRecorderDao.deleteById(id-1);
            cmsRecorderDao.deleteById(id);
        }
    }
//
    @Override
    public void uploadRecorderFile(CmsRecorderUploadVo uploadVo) {
        Long recorderId = uploadVo.getId();//上传文件所对应的recorder的id
        CmsRecorder cmsRecorder = queryCmsInfoRecorder(recorderId);
        AssertUtils.notNull(cmsRecorder, "当前演讲记录安排已被删除");

        CmsRecorder updater = new CmsRecorder();//保存的数据
        updater.setId(recorderId);

        //上传文件标识，如果没有任何文件抛出错误
        boolean fileUploaded = false;

        MultipartFile recorderMultipartFile = uploadVo.getRecorderFile();//得到上传的文件
        if (recorderMultipartFile != null) {//文件存在
            try {
                byte[] bytes = recorderMultipartFile.getBytes();//文件内容
                String fileName=recorderMultipartFile.getOriginalFilename();//文件名字
                String fileType=recorderMultipartFile.getContentType();//文件类型
                updater.setRecorderFileName(fileName);
                updater.setRecorderFileType(fileType);
                updater.setRecorderFile(bytes);
            } catch (IOException e) {
                throw new BusinessException("上传文件出了错误，请稍后重试");
            }
            fileUploaded = true;
        }

        if (!fileUploaded) {
            throw new BusinessException("未上传任何文件");
        }
        updater.setIsRecorderFile(1);//表示recorderfile已经有文件了
        updater.setModifyTime(new Date());
        int affect = cmsRecorderDao.updateById(updater);
        AssertUtils.state(affect == 1);
    }

    @Override
    public void uploadSummarizerFile(CmsRecorderUploadVo uploadVo){
        Long recorderId = uploadVo.getId();
        CmsRecorder cmsRecorder = queryCmsInfoRecorder(recorderId);
        AssertUtils.notNull(cmsRecorder, "当前演讲记录安排已被删除");

        CmsRecorder updater = new CmsRecorder();//保存的数据
        updater.setId(recorderId);

        //上传文件标识，如果没有任何文件抛出错误
        boolean fileUploaded = false;

        MultipartFile summarizerMultipartFile = uploadVo.getSummarizerFile();//得到上传的文件
        if (summarizerMultipartFile != null) {//文件存在
//            updater.setRecorder1Id(userId);
//            String contentType = recorder1MultipartFile.getContentType();
//            updater.setRecorder1Type(contentType);
//            String recorder1File = uploadVo.getRecorder1File();
//            if (StringUtils.isEmpty(recorder1File)) {
//                updater.setRecorder1File(recorder1MultipartFile.getOriginalFilename());
//            } else {
//                updater.setRecorder1File(recorder1File);
//            }
            try {
                byte[] bytes = summarizerMultipartFile.getBytes();
                String fileName=summarizerMultipartFile.getOriginalFilename();
                String fileType=summarizerMultipartFile.getContentType();
                updater.setSummarizerFileName(fileName);
                updater.setSummarizerFileType(fileType);
                updater.setSummarizerFile(bytes);
            } catch (IOException e) {
                throw new BusinessException("上传文件出了错误，请稍后重试");
            }
            fileUploaded = true;
        }

        if (!fileUploaded) {
            throw new BusinessException("未上传任何文件");
        }
        updater.setIsSummarizerFile(1);//表示recorderfile已经有文件了
        updater.setModifyTime(new Date());
        int affect = cmsRecorderDao.updateById(updater);
        AssertUtils.state(affect == 1);
    }

    private CmsRecorder queryCmsDetailRecorder(Long recorderId) {
        CmsRecorderQuery query = CmsRecorderQuery.singletonQuery();
        query.setId(recorderId);
        List<CmsRecorder> recorders = cmsRecorderDao.selectDetailListByParam(query);
        if (CollectionUtils.isNotEmpty(recorders)) {
            return recorders.get(0);
        }
        return null;
    }

    private CmsRecorder queryCmsInfoRecorder(Long recorderId) {
        CmsRecorderQuery query = CmsRecorderQuery.singletonQuery();
        query.setId(recorderId);
        List<CmsRecorder> recorders = cmsRecorderDao.selectInfoListByParam(query);
        if (CollectionUtils.isNotEmpty(recorders)) {
            return recorders.get(0);
        }
        return null;
    }

    private CmsRecorder queryRecorderFile(Long id){
        CmsRecorderQuery query=CmsRecorderQuery.singletonQuery();
        query.setId(id);
        List<CmsRecorder> recorder = cmsRecorderDao.selectRecorderFile(query);
        CmsRecorder cmsRecorder=recorder.get(0);
        return cmsRecorder;
    }

    private CmsRecorder querySummarizerFile(Long id){
        CmsRecorderQuery query=CmsRecorderQuery.singletonQuery();
        query.setId(id);
        List<CmsRecorder> recorders=cmsRecorderDao.selectSummarizerFile(query);
        CmsRecorder cmsRecorder=recorders.get(0);
        return cmsRecorder;
    }

    /**
     * 分页查询数据列表
     */
    @Override
    public PagingResult<CmsRecorderVo> queryPagingResult(CmsRecorderQueryVo queryVo, Paging paging) {
        PagingResult<CmsRecorderVo> pagingResult = PagingResult.create(paging);

        CmsRecorderQuery query = CmsRecorderQuery.listQuery();
        query.setId(queryVo.getId());
        query.setDate(queryVo.getDate());//这里的setId和setDate都没啥用
        Long count = cmsRecorderDao.selectCountByParam(query);
        pagingResult.setTotal(count);

        if (count > 0L) {
            query.setOffset(paging.getOffset());
            query.setLimit(paging.getLimit());
            query.setSorts(SortColumn.create(CmsConstants.CreatedTimeColumn, SortMode.DESC));
            List<CmsRecorder> cmsRecorderList = cmsRecorderDao.selectInfoListByParam(query);
            //cmsRecorderList是已经得到的记录

            pagingResult.setRows(cmsRecorderList, this::convertCmsRecorderVo);
        }

        return pagingResult;
    }

    private String buildDownloadUrl(Long recorderId, String recorderUser) {
        return "/recorder/download?id=" + recorderId + "&user=" + recorderUser;
    }

    private CmsRecorderVo convertCmsRecorderVo(CmsRecorder cmsRecorder){
        CmsRecorderVo cmsRecorderVo=new CmsRecorderVo();
        cmsRecorderVo.setId(cmsRecorder.getId());
        cmsRecorderVo.setDate(cmsRecorder.getDate());
        cmsRecorderVo.setGroupId(cmsRecorder.getGroupId());
        cmsRecorderVo.setRecorder1Name(cmsRecorder.getRecorder1Name());
        cmsRecorderVo.setRecorder2Name(cmsRecorder.getRecorder2Name());
        cmsRecorderVo.setSummarizerName(cmsRecorder.getSummarizerName());
        cmsRecorderVo.setSummarizerFileName(cmsRecorder.getSummarizerFileName());
        cmsRecorderVo.setIsSummarizerFile(cmsRecorder.getIsSummarizerFile());
        cmsRecorderVo.setRecorder(cmsRecorder.getRecorder());
        cmsRecorderVo.setRecorderFileName(cmsRecorder.getRecorderFileName());
        cmsRecorderVo.setIsRecorderFile(cmsRecorder.getIsRecorderFile());
        cmsRecorderVo.setBaseScore(cmsRecorder.getBaseScore());
        cmsRecorderVo.setIsBattle(cmsRecorder.getIsBattle());
        cmsRecorderVo.setModifyTime(cmsRecorder.getModifyTime());
        return cmsRecorderVo;
    }

    @Override
    public void downloadRecorderFile(Long id,HttpServletResponse response) {
        OutputStream outputStream=null;
        try{
            CmsRecorder cmsRecorder = queryRecorderFile(id);//得到想要的cmsRecorder
            byte[] file=cmsRecorder.getRecorderFile();//从数据库得到的文件
            //file已经成功提取出来
            String fileType=cmsRecorder.getRecorderFileType();//文件类型
            String fileName=cmsRecorder.getRecorderFileName();//文件名称

            response.setContentType(fileType);
            response.setHeader("Content-Disposition", "attachment;filename=\"" + URLEncoder.encode( fileName,"UTF-8") + "\"");

            outputStream=response.getOutputStream();
            outputStream.write(file);
            outputStream.flush();//清空缓存

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

    @Override
    public void downloadSummarizerFile(Long id, HttpServletResponse response){
        OutputStream outputStream=null;
        try{
            CmsRecorder cmsRecorder = querySummarizerFile(id);//得到想要的cmsRecorder
            byte[] file=cmsRecorder.getSummarizerFile();//得到summarize文件
            //file已经成功提取出来
            String fileType=cmsRecorder.getSummarizerFileType();//得到文件类型
            String fileName=cmsRecorder.getSummarizerFileName();//得到文件名称

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
//    @Override
//    public CmsRecorderDownloadResultVo downloadRecorderFile(CmsRecorderDownloadVo downloadVo) {
//        Long recorderId = downloadVo.getId();
//        CmsRecorder cmsRecorder = queryCmsDetailRecorder(recorderId);//得到对应Id的cmsRecorder
//        AssertUtils.notNull(cmsRecorder, "演讲记录安排为空");
//
//        CmsRecorderDownloadResultVo downloadResultVo = new CmsRecorderDownloadResultVo();
//        String user = downloadVo.getUser();
//        if (Objects.equals("recorder1", user)) {
//            byte[] recorder1Content = cmsRecorder.getRecorder1Content();
//            if (recorder1Content == null || recorder1Content.length == 0) {
//                throw new BusinessException("无对应辅读人员1下载的文件");
//            }
//            downloadResultVo.setFileName(cmsRecorder.getRecorder1File());
//            downloadResultVo.setFileType(cmsRecorder.getRecorder1Type());
//            downloadResultVo.setFileContent(recorder1Content);
//        } else if (Objects.equals("recorder2", user)) {
//            byte[] recorder2Content = cmsRecorder.getRecorder2Content();
//            if (recorder2Content == null || recorder2Content.length == 0) {
//                throw new BusinessException("无对应辅读人员2下载的文件");
//            }
//            downloadResultVo.setFileName(cmsRecorder.getRecorder2File());
//            downloadResultVo.setFileType(cmsRecorder.getRecorder2Type());
//            downloadResultVo.setFileContent(recorder2Content);
//        } else if (Objects.equals("summarizer", user)) {
//            byte[] summarizerContent = cmsRecorder.getSummarizerContent();
//            if (summarizerContent == null || summarizerContent.length == 0) {
//                throw new BusinessException("无对应记录人员下载的文件");
//            }
//            downloadResultVo.setFileName(cmsRecorder.getSummarizerFile());
//            downloadResultVo.setFileType(cmsRecorder.getSummarizerType());
//            downloadResultVo.setFileContent(summarizerContent);
//        } else {
//            throw new BusinessException("无对应下载的文件");
//        }
//        return downloadResultVo;
//    }
}

