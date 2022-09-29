package com.fudanuniversity.cms.business.service.impl;

import com.fudanuniversity.cms.business.service.CmsWeeklyPublicationService;
import com.fudanuniversity.cms.business.vo.weeklyPublication.CmsWeeklyPublicationForBossVo;
import com.fudanuniversity.cms.business.vo.weeklyPublication.CmsWeeklyPublicationManagerVo;
import com.fudanuniversity.cms.business.vo.weeklyPublication.CmsWeeklyPublicationUploadVo;
import com.fudanuniversity.cms.business.vo.weeklyPublication.CmsWeeklyPublicationVo;
import com.fudanuniversity.cms.commons.exception.BusinessException;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.model.paging.WeeklyPublicationPaging;
import com.fudanuniversity.cms.repository.dao.CmsUserDao;
import com.fudanuniversity.cms.repository.dao.CmsWeeklyPublicationDao;
import com.fudanuniversity.cms.repository.entity.CmsUser;
import com.fudanuniversity.cms.repository.entity.CmsWeeklyPublication;
import com.fudanuniversity.cms.repository.query.CmsUserQuery;
import com.fudanuniversity.cms.repository.query.CmsWeeklyPublicationQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class CmsWeeklyPublicationServiceImpl implements CmsWeeklyPublicationService {

    private static final Logger logger = LoggerFactory.getLogger(CmsWeeklyPublicationService.class);

    @Resource
    CmsWeeklyPublicationDao cmsWeeklyPublicationDao;

    @Resource
    CmsUserDao cmsUserDao;

    @Override
    public PagingResult<CmsWeeklyPublicationVo> queryPagingResult(String stuId, Paging paging){
        PagingResult<CmsWeeklyPublicationVo> pagingResult=new PagingResult<>();

        CmsWeeklyPublicationQuery cmsWeeklyPublicationQuery=CmsWeeklyPublicationQuery.listQuery();
        cmsWeeklyPublicationQuery.setStuId(stuId);
        Long count=cmsWeeklyPublicationDao.selectCountByParam(stuId);
        pagingResult.setTotal(count);

        if(count>0L){
            cmsWeeklyPublicationQuery.setLimit(paging.getLimit());
            cmsWeeklyPublicationQuery.setOffset(paging.getOffset());
            List<CmsWeeklyPublication> list=cmsWeeklyPublicationDao.selectListByParam(cmsWeeklyPublicationQuery);
            pagingResult.setRows(list,this::convertCmsWeeklyPublicationVo);
        }
        return pagingResult;
    }

    @Override
    public void uploadPublication(CmsWeeklyPublicationUploadVo uploadVo){
        CmsWeeklyPublicationQuery query=CmsWeeklyPublicationQuery.singletonQuery();
        query.setStuId(uploadVo.getStuId());
        query.setWeek(uploadVo.getWeek());
        Long count=cmsWeeklyPublicationDao.selectWeekCountByParam(query);
        if(count > 0L){
            throw new BusinessException("该周周报已经提交过，请重新选择周数");
        }

        CmsWeeklyPublication weeklyPublication=new CmsWeeklyPublication();
        weeklyPublication.setStuName(uploadVo.getStuName());
        weeklyPublication.setStuId(uploadVo.getStuId());
        weeklyPublication.setDate(new Date());
        weeklyPublication.setTarget(uploadVo.getTarget());
        weeklyPublication.setOutlinedAchievements(uploadVo.getOutlinedAchievements());
        weeklyPublication.setAchievements(uploadVo.getAchievements());
        weeklyPublication.setPlan(uploadVo.getPlan());
        weeklyPublication.setIsRead(uploadVo.getIsRead());
        weeklyPublication.setWeek(uploadVo.getWeek());
        cmsWeeklyPublicationDao.insertPublication(weeklyPublication);
    }

    @Override
    public void deletePublication(Long id){
        cmsWeeklyPublicationDao.deletePublication(id);
    }

    @Override
    public PagingResult<CmsWeeklyPublicationVo> queryTeacherPagingResult(List<CmsUser> userList, Paging paging, String week){
        PagingResult<CmsWeeklyPublicationVo> pagingResult=new PagingResult<>();

        CmsWeeklyPublicationQuery query=CmsWeeklyPublicationQuery.listQuery();
        query.setWeek(week);
        List<CmsWeeklyPublication> publicationList=new ArrayList<>();
        for(int i=paging.getOffset();i<Math.min(paging.getOffset()+paging.getLimit(),userList.size()) ;i++){
            query.setStuId(userList.get(i).getStuId());
            CmsWeeklyPublication cmsWeeklyPublication=cmsWeeklyPublicationDao.selectLastByParam(query);
            if(cmsWeeklyPublication == null){
                CmsWeeklyPublication newPublication=new CmsWeeklyPublication();
                newPublication.setStuName(userList.get(i).getName());
                newPublication.setStuId(userList.get(i).getStuId());
                publicationList.add(newPublication);
            }
            else {
                publicationList.add(cmsWeeklyPublication);
            }
        }

        Integer countInteger=new Integer(userList.size());
        pagingResult.setTotal(countInteger.longValue());
        pagingResult.setRows(publicationList,this::convertCmsWeeklyPublicationVo);
        return pagingResult;
    }

    @Override
    public WeeklyPublicationPaging<CmsUser> mentorStatistics(List<CmsUser> userList, String week){
        WeeklyPublicationPaging<CmsUser> publicationPaging=new WeeklyPublicationPaging<>();
        CmsWeeklyPublicationQuery query=CmsWeeklyPublicationQuery.listQuery();
        int unDoneCount=0;
        List<CmsUser> stuList=new ArrayList<>();
        for(int i=0; i<userList.size(); i++){
                    query.setStuId(userList.get(i).getStuId());
                    query.setWeek(week);
                    Long count = cmsWeeklyPublicationDao.selectWeekCountByParam(query);
                    if(count.equals(0L)){
                        unDoneCount++;
                        stuList.add(userList.get(i));
                    }
        }
        publicationPaging.setUnDoneCount(unDoneCount);
        publicationPaging.setUnDoneStudentList(stuList);
        return publicationPaging;
    }

    @Override
    public void updateById(CmsWeeklyPublicationVo publicationVo){//这里的update只能是修改评分和评语
        CmsWeeklyPublication publication=new CmsWeeklyPublication();
        publication.setId(publicationVo.getId());
        publication.setTarget(publicationVo.getTarget());
        publication.setOutlinedAchievements(publicationVo.getOutlinedAchievements());
        publication.setAchievements(publicationVo.getAchievements());
        publication.setPlan(publicationVo.getPlan());
        if (publicationVo.getTarget() != null){
            publication.setDate(new Date());
        }
        publication.setComment(publicationVo.getComment());
        publication.setTempComment(publicationVo.getTempComment());
        publication.setScore(publicationVo.getScore());
        publication.setIsRead(publicationVo.getIsRead());
        cmsWeeklyPublicationDao.updateById(publication);
    }

    @Override
    public PagingResult<CmsWeeklyPublicationForBossVo> queryBossPaging(Paging paging, String week){
        PagingResult<CmsWeeklyPublicationForBossVo> pagingResult=new PagingResult<>();

        CmsUserQuery cmsUserQuery=new CmsUserQuery();
        List<CmsUser> userList=cmsUserDao.selectStudentListByParam(cmsUserQuery);//所有的学生列表

        CmsWeeklyPublicationQuery query=CmsWeeklyPublicationQuery.listQuery();
        List<CmsWeeklyPublicationForBossVo> bossVoList=new ArrayList<>();
        for(int i=paging.getOffset();i<Math.min(paging.getOffset()+paging.getLimit(),userList.size()); i++){
            query.setStuId(userList.get(i).getStuId());
            query.setWeek(week);
            CmsWeeklyPublication cmsWeeklyPublication=cmsWeeklyPublicationDao.selectLastByParam(query);
            //上面是查询学生提交的周报
            CmsWeeklyPublicationForBossVo bossVo=new CmsWeeklyPublicationForBossVo();
            if(cmsWeeklyPublication == null){//这个学生没有提交周报
                bossVo.setStuId(userList.get(i).getStuId());
                bossVo.setStuName(userList.get(i).getName());
                if(userList.get(i).getMentor()!=null && userList.get(i).getMentor().equals("杨珉")){//是导师
                    bossVo.setIsMentor(1);
                }
                else {
                    bossVo.setIsMentor(0);
                }
                bossVo.setType(userList.get(i).getType());
            }
            else{
                bossVo.setId(cmsWeeklyPublication.getId());
                bossVo.setStuName(cmsWeeklyPublication.getStuName());
                bossVo.setStuId(cmsWeeklyPublication.getStuId());
                bossVo.setType(userList.get(i).getType());
                if(userList.get(i).getMentor()!= null && userList.get(i).getMentor().equals("杨珉")){//是导师
                    bossVo.setIsMentor(1);
                }
                else {
                    bossVo.setIsMentor(0);
                }
                bossVo.setTarget(cmsWeeklyPublication.getTarget());
                bossVo.setOutlinedAchievements(cmsWeeklyPublication.getOutlinedAchievements());
                bossVo.setAchievements(cmsWeeklyPublication.getAchievements());
                bossVo.setPlan(cmsWeeklyPublication.getPlan());
                bossVo.setScore(cmsWeeklyPublication.getScore());
                bossVo.setComment(cmsWeeklyPublication.getComment());
                bossVo.setDate(cmsWeeklyPublication.getDate());
            }
            bossVoList.add(bossVo);
        }

        Integer countInteger=new Integer(userList.size());
        pagingResult.setTotal(countInteger.longValue());
        pagingResult.setRows(bossVoList);
        return pagingResult;
    }

    @Override
    public PagingResult<CmsWeeklyPublicationManagerVo> queryManagerPaging(Paging paging, String week){
        PagingResult<CmsWeeklyPublicationManagerVo> pagingResult=new PagingResult<>();

        CmsUserQuery cmsUserQuery=new CmsUserQuery();
        List<CmsUser> userList=cmsUserDao.selectStudentListByParam(cmsUserQuery);//所有的学生列表

        List<CmsWeeklyPublicationManagerVo> managerVoList=new ArrayList<>();
        CmsWeeklyPublicationQuery query=CmsWeeklyPublicationQuery.listQuery();
        for(int i=paging.getOffset();i<Math.min(paging.getOffset()+paging.getLimit(),userList.size()); i++) {
            query.setStuId(userList.get(i).getStuId());
            query.setWeek(week);
            CmsWeeklyPublication cmsWeeklyPublication = cmsWeeklyPublicationDao.selectLastByParam(query);

            CmsWeeklyPublicationManagerVo managerVo = new CmsWeeklyPublicationManagerVo();
            if (cmsWeeklyPublication == null) {
                managerVo.setStuId(userList.get(i).getStuId());
                managerVo.setStuName(userList.get(i).getName());
                managerVo.setType(userList.get(i).getType());
            } else {
                managerVo.setId(cmsWeeklyPublication.getId());
                managerVo.setStuId(cmsWeeklyPublication.getStuId());
                managerVo.setStuName(cmsWeeklyPublication.getStuName());
                managerVo.setType(userList.get(i).getType());
                managerVo.setTarget(cmsWeeklyPublication.getTarget());
                managerVo.setOutlinedAchievements(cmsWeeklyPublication.getOutlinedAchievements());
                managerVo.setAchievements(cmsWeeklyPublication.getAchievements());
                managerVo.setPlan(cmsWeeklyPublication.getPlan());
                managerVo.setDate(cmsWeeklyPublication.getDate());
            }
            managerVoList.add(managerVo);
        }

        Integer countInteger=new Integer(userList.size());
        pagingResult.setTotal(countInteger.longValue());
        pagingResult.setRows(managerVoList);
        return pagingResult;
    }

    private CmsWeeklyPublicationVo convertCmsWeeklyPublicationVo(CmsWeeklyPublication cmsWeeklyPublication){
        CmsWeeklyPublicationVo vo=new CmsWeeklyPublicationVo();
        vo.setId(cmsWeeklyPublication.getId());
        vo.setStuName(cmsWeeklyPublication.getStuName());
        vo.setStuId(cmsWeeklyPublication.getStuId());
        vo.setTarget(cmsWeeklyPublication.getTarget());
        vo.setOutlinedAchievements(cmsWeeklyPublication.getOutlinedAchievements());
        vo.setAchievements(cmsWeeklyPublication.getAchievements());
        vo.setPlan(cmsWeeklyPublication.getPlan());
        vo.setWeek(cmsWeeklyPublication.getWeek());
        vo.setScore(cmsWeeklyPublication.getScore());
        vo.setComment(cmsWeeklyPublication.getComment());
        vo.setTempComment(cmsWeeklyPublication.getTempComment());
        vo.setIsRead(cmsWeeklyPublication.getIsRead());
        vo.setDate(cmsWeeklyPublication.getDate());
        return vo;
    }
}
