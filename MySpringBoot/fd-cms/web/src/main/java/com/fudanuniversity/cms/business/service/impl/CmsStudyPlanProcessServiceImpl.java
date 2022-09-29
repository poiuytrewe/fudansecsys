package com.fudanuniversity.cms.business.service.impl;

import com.fudanuniversity.cms.business.service.CmsStudyPlanProcessService;
import com.fudanuniversity.cms.business.vo.study.plan.*;
import com.fudanuniversity.cms.business.vo.user.CmsUserVo;
import com.fudanuniversity.cms.commons.exception.BusinessException;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.repository.dao.CmsBulletinDao;
import com.fudanuniversity.cms.repository.dao.CmsStudyPlanProcessDao;
import com.fudanuniversity.cms.repository.dao.CmsUserDao;
import com.fudanuniversity.cms.repository.entity.*;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanProcessQuery;
import com.fudanuniversity.cms.repository.query.CmsUserQuery;
import org.checkerframework.common.util.report.qual.ReportOverride;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class CmsStudyPlanProcessServiceImpl implements CmsStudyPlanProcessService {
    private static final Logger logger = LoggerFactory.getLogger(CmsStudyPlanProcessServiceImpl.class);

    @Resource
    CmsStudyPlanProcessDao cmsStudyPlanProcessDao;

    @Resource
    CmsUserDao cmsUserDao;

    @Resource
    CmsBulletinDao cmsBulletinDao;

    @Override
    public PagingResult<CmsStudyPlanProcessVo> queryPagingResult(String stuId){
        PagingResult<CmsStudyPlanProcessVo> pagingResult=new PagingResult<>();

        CmsStudyPlanProcessQuery planProcessQuery=CmsStudyPlanProcessQuery.listQuery();
        planProcessQuery.setStuId(stuId);
        Long count =cmsStudyPlanProcessDao.selectCountByParam(planProcessQuery);
        pagingResult.setTotal(count);

        if(count>0L){
            List<CmsStudyPlanProcess> cmsStudyPlanProcessList=cmsStudyPlanProcessDao.selectListByParam(planProcessQuery);
            pagingResult.setRows(cmsStudyPlanProcessList,this::convert);
        }
        return pagingResult;
    }

    @Override
    public void addProcess(CmsStudyPlanProcessAddVo addVo){
        Date date=new Date();
        Integer type1=addVo.getBaseCategoryList().get(0).getType();
        Integer type2=addVo.getAdvCategoryList().get(0).getType();
        for(int i=0;i<addVo.getBaseCategoryList().size();i++){
            CmsStudyPlanProcess process=new CmsStudyPlanProcess();
            process.setStuName(addVo.getStuName());
            process.setStuId(addVo.getStuId());
            process.setType(type1);
            process.setMissionStatus(1);
            process.setIsDelay(0);
            process.setTinyCategory(addVo.getBaseCategoryList().get(i).getProcessCategory());
            process.setCategory(addVo.getBaseCategoryList().get(i).getCategoryMission());
            if(addVo.getBaseCategoryList().get(i).getMonth()>=9 && addVo.getBaseCategoryList().get(i).getMonth()<=12){
                process.setYear(addVo.getEnrollYear()+addVo.getBaseCategoryList().get(i).getYear()-1);
            }
            else{
                process.setYear(addVo.getEnrollYear()+addVo.getBaseCategoryList().get(i).getYear());
            }
            process.setMonth(addVo.getBaseCategoryList().get(i).getMonth());
            process.setCreateTime(date);
            process.setAlert(0);
            cmsStudyPlanProcessDao.insert(process);
        }
        for(int i=0;i<addVo.getAdvCategoryList().size();i++){
            CmsStudyPlanProcess process=new CmsStudyPlanProcess();
            process.setStuName(addVo.getStuName());
            process.setStuId(addVo.getStuId());
            process.setMissionStatus(1);
            process.setType(type2);
            process.setIsDelay(0);
            process.setTinyCategory(addVo.getAdvCategoryList().get(i).getProcessCategory());
            process.setCategory(addVo.getAdvCategoryList().get(i).getCategoryMission());
            if(addVo.getAdvCategoryList().get(i).getMonth()>=9 && addVo.getAdvCategoryList().get(i).getMonth()<=12){
                process.setYear(addVo.getEnrollYear()+addVo.getAdvCategoryList().get(i).getYear()-1);
            }
            else{
                process.setYear(addVo.getEnrollYear()+addVo.getAdvCategoryList().get(i).getYear());
            }
            process.setMonth(addVo.getAdvCategoryList().get(i).getMonth());
            process.setCreateTime(date);
            process.setAlert(0);
            cmsStudyPlanProcessDao.insert(process);
        }
    }

    @Override
    public void resetProcess(CmsStudyPlanProcessAddVo addVo){
        Date date=new Date();
        CmsStudyPlanProcessQuery planProcessQuery=CmsStudyPlanProcessQuery.listQuery();
        planProcessQuery.setStuId(addVo.getStuId());
        List<CmsStudyPlanProcess> cmsStudyPlanProcessList=cmsStudyPlanProcessDao.selectListByParam(planProcessQuery);
        //这里得到了该学生原来的工作
        for(int i=0;i<cmsStudyPlanProcessList.size();i++){//把该删的都删掉
            if(cmsStudyPlanProcessList.get(i).getType()!=0){//说明这个任务不是必须的任务
                if(cmsStudyPlanProcessList.get(i).getMissionStatus()!=2 && cmsStudyPlanProcessList.get(i).getMissionStatus()!=3){
                    cmsStudyPlanProcessDao.deleteById(cmsStudyPlanProcessList.get(i).getId());
                }
            }
        }
        Integer type=addVo.getAdvCategoryList().get(0).getType();
        for(int i=0;i<addVo.getAdvCategoryList().size();i++){
            CmsStudyPlanProcess process=new CmsStudyPlanProcess();
            process.setStuName(addVo.getStuName());
            process.setStuId(addVo.getStuId());
            process.setMissionStatus(1);
            process.setType(type);
            process.setIsDelay(0);
            process.setTinyCategory(addVo.getAdvCategoryList().get(i).getProcessCategory());
            process.setCategory(addVo.getAdvCategoryList().get(i).getCategoryMission());
            if(addVo.getAdvCategoryList().get(i).getMonth()>=9 && addVo.getAdvCategoryList().get(i).getMonth()<=12){
                process.setYear(addVo.getEnrollYear()+addVo.getAdvCategoryList().get(i).getYear()-1);
            }
            else{
                process.setYear(addVo.getEnrollYear()+addVo.getAdvCategoryList().get(i).getYear());
            }
            process.setMonth(addVo.getAdvCategoryList().get(i).getMonth());
            process.setCreateTime(date);
            process.setAlert(0);
            cmsStudyPlanProcessDao.insert(process);
        }
    }

    @Override
    public void updateProcess(CmsStudyPlanProcessVo processVo){
        CmsStudyPlanProcess process=new CmsStudyPlanProcess();
        process.setId(processVo.getId());
        process.setCategory(processVo.getCategory());
        process.setType(processVo.getType());
        process.setTinyCategory(processVo.getTinyCategory());
        process.setYear(processVo.getYear());
        process.setMonth(processVo.getMonth());
        process.setWishYear(processVo.getWishYear());
        process.setWishMonth(processVo.getWishMonth());
        process.setMissionStatus(processVo.getMissionStatus());
        process.setAlert(processVo.getAlert());
        process.setIsDelay(processVo.getIsDelay());
        cmsStudyPlanProcessDao.updateById(process);
    }

    @Override
    public void deleteProcess(Long id){
        cmsStudyPlanProcessDao.deleteById(id);//删除操作
    }

    @Override
    public void reConfigProcess(String stuId){
        CmsStudyPlanProcessQuery planProcessQuery=CmsStudyPlanProcessQuery.listQuery();
        planProcessQuery.setStuId(stuId);
        List<CmsStudyPlanProcess> processList=cmsStudyPlanProcessDao.selectListByParam(planProcessQuery);
        for(int i=0;i<processList.size();i++){
            if(processList.get(i).getMissionStatus()!=2 && processList.get(i).getMissionStatus()!=3){//只要不是已提交或者已完成状态的任务都给删除
                cmsStudyPlanProcessDao.deleteById(processList.get(i).getId());
            }
        }
    }

    @Override
    public void groupUpdateProcess(CmsStudyPlanGroupUpdateVo updateVo){
        CmsStudyPlanProcessQuery planProcessQuery=CmsStudyPlanProcessQuery.listQuery();
        planProcessQuery.setStuId(updateVo.getStuId());
        List<CmsStudyPlanProcess> cmsStudyPlanProcessList=cmsStudyPlanProcessDao.selectListByParam(planProcessQuery);
        //以上的步骤是获取全部的任务
        int location=0;
        for(int i=0;i<cmsStudyPlanProcessList.size();i++){
            if(cmsStudyPlanProcessList.get(i).getId().equals(updateVo.getList().getId())){
                location=i;
                System.out.println(location);
                break;
            }
        }
        //上面的步骤的确定延后任务的第一个下标
        for (int i = Math.toIntExact(location); i<cmsStudyPlanProcessList.size(); i++){
            CmsStudyPlanProcess process=new CmsStudyPlanProcess();
            process.setId(cmsStudyPlanProcessList.get(i).getId());
            int year=cmsStudyPlanProcessList.get(i).getYear();
            int month=cmsStudyPlanProcessList.get(i).getMonth();
            int newYear, newMonth;
            if(12-month-updateVo.getDelayTime()<0){
                newYear=year+1;
                newMonth=month+updateVo.getDelayTime()-12;
            }
            else{
                newYear=year;
                newMonth=month+updateVo.getDelayTime();
            }
            process.setYear(newYear);
            process.setMonth(newMonth);
            process.setIsDelay(1);
            process.setMissionStatus(1);
            cmsStudyPlanProcessDao.updateById(process);
        }
    }

    @Override
    public List<Integer> selectStuMissionStatusList(List<CmsUser> cmsStudentList, Paging paging){
        List<CmsStudyPlanProcessMissionStatus> stuMissionStatusList=statusList(cmsStudentList);
        List<Integer> missionStatusList=new ArrayList<>();
        for(int i=paging.getOffset();(i<stuMissionStatusList.size() && i<paging.getOffset()+10);i++){
            missionStatusList.add(stuMissionStatusList.get(i).getCount());
        }
        return missionStatusList;
    }

    private CmsStudyPlanProcessVo convert(CmsStudyPlanProcess process){
        CmsStudyPlanProcessVo processVo=new CmsStudyPlanProcessVo();
        processVo.setId(process.getId());
        processVo.setStuName(process.getStuName());
        processVo.setStuId(process.getStuId());
        processVo.setMissionStatus(process.getMissionStatus());
        processVo.setCategory(process.getCategory());
        processVo.setTinyCategory(process.getTinyCategory());
        processVo.setIsDelay(process.getIsDelay());
        processVo.setYear(process.getYear());
        processVo.setMonth(process.getMonth());
        processVo.setWishYear(process.getWishYear());
        processVo.setWishMonth(process.getWishMonth());
        processVo.setAlert(process.getAlert());
        return processVo;
    }

    @Override
    public List<CmsStudyPlanProcessMissionStatus> statusList(List<CmsUser> cmsStudentList){
        List<CmsStudyPlanProcessMissionStatus> stuMissionStatusList=new ArrayList<>();
        for(int i=0;i<cmsStudentList.size();i++){//这个导师名下的所有学生
            String stuId=cmsStudentList.get(i).getStuId();//第i个学生的stuId
            Integer count=cmsStudyPlanProcessDao.selectMissionStatus(stuId);
            if(count==null){
                count=0;
            }
            CmsStudyPlanProcessMissionStatus status=new CmsStudyPlanProcessMissionStatus();
            status.setStuId(stuId);
            status.setCount(count);
            stuMissionStatusList.add(status);
        }//以上是获得某个导师的所有学生的所有任务情况
        Collections.sort(stuMissionStatusList,(a,b)->{
            return b.getCount()-a.getCount();
        });//排序
        return stuMissionStatusList;
    }

    @Override
    public List<CmsUser>  selectMissionStudentList(List<CmsUser> cmsStudentList, Paging paging){
        List<CmsStudyPlanProcessMissionStatus> stuMissionStatusList=statusList(cmsStudentList);
        List<CmsUser> studentList=new ArrayList<>();
        for(int i=paging.getOffset();(i<stuMissionStatusList.size() && i<paging.getOffset()+10);i++){
            CmsUserQuery query=CmsUserQuery.singletonQuery();
            query.setStuId(stuMissionStatusList.get(i).getStuId());
            CmsUser cmsUser=cmsUserDao.selectListByParam(query).get(0);
            studentList.add(cmsUser);
        }
        return studentList;
    }

    @Override
    public Integer getAllApplyCount(List<CmsUser> cmsStudentList){
        Integer count=0;
        for(int i=0;i<cmsStudentList.size();i++){//接下来是对每个学生进行搜索
            Integer mount=cmsStudyPlanProcessDao.selectApplyCount(cmsStudentList.get(i).getStuId());
            count=count+mount;
        }
        return count;
    }

    @Override
    public int[] selectCount(List<CmsUser> cmsStudentList){
        List<CmsStudyPlanProcessMissionStatus> stuMissionStatusList=statusList(cmsStudentList);
        int[] count={0,0};
        for(int i=0;i<stuMissionStatusList.size();i++){
            if(stuMissionStatusList.get(i).getCount()>=2){
                count[0]++;
            }
            if(stuMissionStatusList.get(i).getCount()==1){
                count[1]++;
            }
        }
        return count;
    }

    @Override
    public List<Integer> selectStuMissionStatusListMng(PagingResult<CmsUserVo> pagingResult){
        List<Integer> count=new ArrayList<>();
        for(int i=0;i<pagingResult.getRows().size();i++){
            String stuId=pagingResult.getRows().get(i).getStuId();
            Integer c=cmsStudyPlanProcessDao.selectMissionStatus(stuId);
            if(c==null){
                c=0;
            }
            count.add(c);
        }
        return count;
    }

    @Override
    public List<CmsStudyPlanProcess> getAllApprove(List<CmsUser> cmsStudentList){
        List<CmsStudyPlanProcess> allApprove=new ArrayList<>();
        for(int i=0;i<cmsStudentList.size();i++){
            List<CmsStudyPlanProcess> oneApprove=cmsStudyPlanProcessDao.getAllApprove(cmsStudentList.get(i).getStuId());
            allApprove.addAll(oneApprove);
        }
        return allApprove;
    }

    @Override
    public List<CmsStudyPlanProcess> getAllDelay(List<CmsUser> cmsStudentList){//获取某个倒是手下所有的延期
        List<CmsStudyPlanProcess> delayList=new ArrayList<>();
        for(int i=0;i<cmsStudentList.size();i++){
            List<CmsStudyPlanProcess> oneDelay=cmsStudyPlanProcessDao.getAllDelay(cmsStudentList.get(i).getStuId());
            delayList.addAll(oneDelay);
        }
        return delayList;
    }

    @Override
    public void groupDone(List<CmsStudyPlanProcess> groupList){//学生发出的批量完成
        for(int i=0;i<groupList.size();i++){
            CmsStudyPlanProcess process=new CmsStudyPlanProcess();
            process.setId(groupList.get(i).getId());
            process.setMissionStatus(2);
            cmsStudyPlanProcessDao.updateById(process);
        }
    }

    @Override
    public void groupDelay(CmsStudyPlanGroupDelayVo delayVo){//学生发出的批量延期
        for(int i=0;i<delayVo.getGroupList().size();i++){
            CmsStudyPlanProcess process=new CmsStudyPlanProcess();
            process.setId(delayVo.getGroupList().get(i).getId());
            Integer newYear,newMonth;
            if(12-delayVo.getDelayTime()-delayVo.getGroupList().get(i).getMonth()<0){//说明到了新的一年
                newYear=delayVo.getGroupList().get(i).getYear()+1;
                newMonth=delayVo.getDelayTime()+delayVo.getGroupList().get(i).getMonth()-12;
            }
            else{
                newYear=delayVo.getGroupList().get(i).getYear();
                newMonth=delayVo.getDelayTime() + delayVo.getGroupList().get(i).getMonth();
            }
            process.setWishMonth(newMonth);
            process.setWishYear(newYear);
            process.setMissionStatus(4);
            cmsStudyPlanProcessDao.updateById(process);
        }
    }

    @Override
    public void groupApprove(CmsStudyPlanGroupApproveVo approveVo){
        for(int i=0;i<approveVo.getList().size();i++){
            CmsStudyPlanProcess process=new CmsStudyPlanProcess();
            process.setId(approveVo.getList().get(i).getId());
            process.setMissionStatus(approveVo.getStatus());
            process.setIsDelay(approveVo.getIsDelay());
            if(approveVo.getIsDelay()==1){//说明是已经延期的了
                process.setYear(approveVo.getList().get(i).getWishYear());
                process.setMonth(approveVo.getList().get(i).getWishMonth());
            }
            cmsStudyPlanProcessDao.updateById(process);
        }
    }

    @Override
    public List<CmsGraduateInformationVo> selectGraduateInformation(List<CmsUser> cmsUserList){
        List<CmsGraduateInformationVo> voList=new ArrayList<>();
        for (int i=0; i<cmsUserList.size(); i++){
            CmsGraduateInformationVo vo = new CmsGraduateInformationVo();
            vo.setStuName(cmsUserList.get(i).getName());
            vo.setStuId(cmsUserList.get(i).getStuId());
            Integer type = cmsUserList.get(i).getKeshuo();
            if (type==0){
                vo.setStuType("暂未选择硕士类型");
            }
            else if (type==1){
                vo.setStuType("学硕");
            }
            else if (type==2){
                vo.setStuType("专硕");
            }
            else if (type==3){
                vo.setStuType("学工");
            }
            CmsStudyPlanProcessQuery processQuery = CmsStudyPlanProcessQuery.listQuery();
            processQuery.setStuId(cmsUserList.get(i).getStuId());
            List<CmsStudyPlanProcess> processes=cmsStudyPlanProcessDao.selectListByParam(processQuery);

            Integer year=processes.get(processes.size()-1).getYear();
            Integer month=processes.get(processes.size()-1).getMonth();
            vo.setGraduateTime(year+"年"+month+"月");

            processQuery.setMissionStatus(1);
            Long count = cmsStudyPlanProcessDao.selectCountByParam(processQuery);
            List<CmsStudyPlanProcess> undoneList = cmsStudyPlanProcessDao.selectListByParam(processQuery);
            List<String> undone = new ArrayList<>();
            for (int x=0; x<undoneList.size(); x++){
                undone.add(undoneList.get(x).getCategory());
            }
            vo.setUndoneList(undone);
            if (count>0L){
                vo.setIsDone("否");
            }
            else {
                vo.setIsDone("是");
            }

            processQuery.setMissionStatus(3);
            List<CmsStudyPlanProcess> doneProcesses=cmsStudyPlanProcessDao.selectListByParam(processQuery);
            if (doneProcesses.size()==processes.size()){//所有任务全部完成
                vo.setCurrentJob("无");
            }
            else if (doneProcesses.size()==0){//没有一项完成的
                vo.setCurrentJob(processes.get(0).getCategory());
            }
            else{
                for(int x=0;x<processes.size();x++){
                    if(processes.get(x).getCategory()==doneProcesses.get(doneProcesses.size()-1).getCategory()){
                        if (x+1<=processes.size()-1){
                            vo.setCurrentJob(processes.get(x+1).getCategory());
                        }
                    }
                }
            }

            voList.add(vo);
        }
        return voList;
    }

    @Scheduled(cron = "0 0 1 * * ?" ) //定时任务，每天一点钟执行一次
//    @Scheduled(cron = "0/10 * * * * ? ")
    private void checkProcessLate(){//每天检查数据库中的所有process记录，看是否有逾期的现象，如果有，修改missionStatus
        Calendar calendar=Calendar.getInstance();
        Integer nowYear=calendar.get(Calendar.YEAR);//获取当前的年月
        Integer nowMonth=calendar.get(Calendar.MONTH)+1;//newMonth就是当前的月份
        //提醒的条件：1.missionStatus=1；2.记录的月份和当前的月份是一样的
        //逾期的条件：1.missionStatus=1，还没完成；2.记录的月份比当前的月份数值上小1；

        //这样改的要求是，逾期任务在逾期当月就要由导师处理完毕
        CmsStudyPlanProcessQuery query=CmsStudyPlanProcessQuery.listQuery();
        query.setYear(nowYear);
        query.setMonth(nowMonth);
        //lateProcess是已经逾期的任务的集合
        List<CmsStudyPlanProcess> lateProcessList=cmsStudyPlanProcessDao.selectLateList(query);
        //下面要进行修改
        List<Long> processListId=new ArrayList<>();//已经逾期的id集合
        Map<String,Integer> hashMap=new HashMap<>();
        for(int i=0;i<lateProcessList.size();i++){//进行赋值
            String stuId=lateProcessList.get(i).getStuId();//第i个逾期任务的stuId
            if(hashMap.get(stuId)==null){//不等于1表示还没有对这个stuId进行处理
                hashMap.put(stuId,1);

                CmsUserQuery query1=CmsUserQuery.singletonQuery();
                query1.setStuId(stuId);
                CmsUser c=cmsUserDao.selectListByParam(query1).get(0);//得到这个学生的信息

                CmsBulletin bulletin = new CmsBulletin();
                bulletin.setStuId(c.getStuId());
                bulletin.setTitle("你存在任务逾期现象");
                bulletin.setContent("你存在任务逾期现象，请尽快到培养过程模块请求延期");
                bulletin.setIsRead(0);
                bulletin.setCreateTime(new Date());
                cmsBulletinDao.insert(bulletin);//向学生
            }

            processListId.add(lateProcessList.get(i).getId());//已经逾期的任务id
        }
        cmsStudyPlanProcessDao.groupUpdateById(processListId);//更新任务状态，将未完成变为已逾期

        List<CmsStudyPlanProcess> alertProcessList=cmsStudyPlanProcessDao.selectListByParam(query);//查询当月需要完成任务的
        for(int i=0;i<alertProcessList.size();i++){
            CmsStudyPlanProcessVo processVo=new CmsStudyPlanProcessVo();
            processVo.setId(alertProcessList.get(i).getId());
            processVo.setAlert(1);
            updateProcess(processVo);
        }
        System.out.println("处理完成");
    }
}
