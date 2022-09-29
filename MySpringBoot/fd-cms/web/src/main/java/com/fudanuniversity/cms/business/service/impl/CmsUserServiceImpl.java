package com.fudanuniversity.cms.business.service.impl;

import com.fudanuniversity.cms.business.component.CmsUserComponent;
import com.fudanuniversity.cms.business.service.CmsUserAccountService;
import com.fudanuniversity.cms.business.service.CmsUserService;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyScoreDate;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyScoreVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsUserStudyPlanQueryVo;
import com.fudanuniversity.cms.business.vo.user.*;
import com.fudanuniversity.cms.commons.constant.CmsConstants;
import com.fudanuniversity.cms.commons.enums.DeletedEnum;
import com.fudanuniversity.cms.commons.enums.UserRoleEnum;
import com.fudanuniversity.cms.commons.enums.UserTypeEnum;
import com.fudanuniversity.cms.commons.exception.BusinessException;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.model.query.SortColumn;
import com.fudanuniversity.cms.commons.model.query.SortMode;
import com.fudanuniversity.cms.commons.util.AssertUtils;
import com.fudanuniversity.cms.commons.util.DateExUtils;
import com.fudanuniversity.cms.commons.util.JsonUtils;
import com.fudanuniversity.cms.commons.util.ValueUtils;
import com.fudanuniversity.cms.repository.dao.CmsScoreDao;
import com.fudanuniversity.cms.repository.dao.CmsUserDao;
import com.fudanuniversity.cms.repository.entity.CmsScore;
import com.fudanuniversity.cms.repository.entity.CmsUser;
import com.fudanuniversity.cms.repository.entity.CmsUserAccount;
import com.fudanuniversity.cms.repository.query.CmsStudyScoreQuery;
import com.fudanuniversity.cms.repository.query.CmsUserQuery;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * CmsUserService 实现类
 * <p>
 * Created by Xinyue.Tang at 2021-05-01
 */
@Service
public class CmsUserServiceImpl implements CmsUserService {

    private static final Logger logger = LoggerFactory.getLogger(CmsUserServiceImpl.class);

    @Resource
    private CmsUserDao cmsUserDao;

    @Resource
    private CmsUserComponent cmsUserComponent;

    @Resource
    private CmsUserAccountService cmsUserAccountService;

    @Resource
    private CmsScoreDao cmsScoreDao;

    @Override
    public void checkManagePrivilege(String stuId) {
        CmsUser cmsUser = cmsUserComponent.queryUser(stuId);
        AssertUtils.notNull(cmsUser, "用户[" + stuId + "]不存在");
        if (Objects.equals(cmsUser.getIsSystemMng(),1)
                || UserRoleEnum.hasManageRole(cmsUser.getIsTalkMng(),cmsUser.getIsLabMng(), cmsUser.getIsPaperMng(),cmsUser.getIsEducateMng(),cmsUser.getIsDeviceMng())) {
            return;
        }
        throw new BusinessException("用户没有任何管理权限");
    }

    @Override
    public void checkSystemMng(String stuId){
        CmsUser cmsUser = cmsUserComponent.queryUser(stuId);
        AssertUtils.notNull(cmsUser, "用户[" + stuId + "]不存在");
        if(Objects.equals(cmsUser.getIsSystemMng(),1)){
            return;
        }
        throw new BusinessException("用户没有系统管理员权限");
    }

    @Override
    public  void checkTalkMng(String stuId){
        CmsUser cmsUser = cmsUserComponent.queryUser(stuId);
        AssertUtils.notNull(cmsUser, "用户[" + stuId + "]不存在");
        if(Objects.equals(cmsUser.getIsTalkMng(),1)){
            return;
        }
        throw new BusinessException("用户没有讨论班管理员权限");
    }

    @Override
    public void checkLabMng(String stuId){
        CmsUser cmsUser = cmsUserComponent.queryUser(stuId);
        AssertUtils.notNull(cmsUser, "用户[" + stuId + "]不存在");
        if(Objects.equals(cmsUser.getIsLabMng(),1)){
            return;
        }
        throw new BusinessException("用户没有Lab管理员权限");
    }

    @Override
    public void checkPaperMng(String stuId){
        CmsUser cmsUser = cmsUserComponent.queryUser(stuId);
        AssertUtils.notNull(cmsUser, "用户[" + stuId + "]不存在");
        if(Objects.equals(cmsUser.getIsPaperMng(),1)){
            return;
        }
        throw new BusinessException("用户没有论文管理员权限");
    }

    @Override
    public  void checkEducateMng(String stuId){
        CmsUser cmsUser = cmsUserComponent.queryUser(stuId);
        AssertUtils.notNull(cmsUser, "用户[" + stuId + "]不存在");
        if(Objects.equals(cmsUser.getIsEducateMng(),1)){
            return;
        }
        throw new BusinessException("用户没有培养方案管理员权限");
    }

    @Override
    public void checkDeviceMng(String stuId){
        CmsUser cmsUser = cmsUserComponent.queryUser(stuId);
        AssertUtils.notNull(cmsUser, "用户[" + stuId + "]不存在");
        if(Objects.equals(cmsUser.getIsDeviceMng(),1)){
            return;
        }
        throw new BusinessException("用户没有设备管理员权限");
    }

    @Override
    public void checkTeacher(String stuId){
        CmsUser cmsUser=cmsUserComponent.queryUser(stuId);
        AssertUtils.notNull(cmsUser,"用户["+stuId+"]不存在");
        if(Objects.equals(cmsUser.getType(),20)){
            return;
        }
        throw  new BusinessException("用户不是教师");
    }

    @Override
    public void checkBackbone(String stuId){
        CmsUser cmsUser=cmsUserComponent.queryUser(stuId);
        AssertUtils.notNull(cmsUser,"用户["+stuId+"]不存在");
        if(Objects.equals(cmsUser.getStatus(),1)){
            return;
        }
        throw new BusinessException("用户不是骨干学生");
    }

    @Override
    public void checkDoc(String stuId){
        CmsUser cmsUser=cmsUserComponent.queryUser(stuId);
        AssertUtils.notNull(cmsUser,"用户["+stuId+"]不存在");
        if(Objects.equals(cmsUser.getType(),10)){
            return;
        }
        throw new BusinessException("用户不是博士");
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void saveCmsUser(CmsUserAddVo userAddVo) {
        String stuId = userAddVo.getStuId();//stuId是需要加入数据库的Id
        if (StringUtils.isNotEmpty(stuId)) {//如果stuId不为空
            CmsUserQuery query = new CmsUserQuery();//生成query语句
            query.setStuId(stuId);//
            query.setLimit(1);
            List<CmsUser> cmsUsers = cmsUserDao.selectListByParam(query);//查询数据库中是否已经存在stuId
            if (CollectionUtils.isNotEmpty(cmsUsers)) {//如果stuId已经存在
                CmsUser existsUser = cmsUsers.get(0);
                if (Objects.equals(DeletedEnum.Normal.getCode(), existsUser.getDeleted())) {
                    throw new BusinessException("学号为[" + stuId + "]用户已存在");
                } else {
                    cmsUserDao.deleteById(existsUser.getId());
                }
            }
        }

        CmsUser cmsUser = new CmsUser();
        cmsUser.setType(userAddVo.getType());//身份类型
        cmsUser.setStuId(stuId);
        cmsUser.setRoleId(0);
        cmsUser.setStudyType(0);
        if(userAddVo.getKeshuo()==null){
            cmsUser.setKeshuo(0);
        }
        else{
            cmsUser.setKeshuo(userAddVo.getKeshuo());
        }
        cmsUser.setIsSystemMng(0);
        cmsUser.setIsTalkMng(0);
        cmsUser.setIsLabMng(0);
        cmsUser.setIsPaperMng(0);
        cmsUser.setIsEducateMng(0);
        cmsUser.setIsDeviceMng(0);
        cmsUser.setName(userAddVo.getName());
        cmsUser.setMentor(ValueUtils.defaultString(userAddVo.getMentor()));
        Integer enrollYear = userAddVo.getEnrollYear();//获得入学年份
        if (enrollYear != null) {//设定入学日期
            cmsUser.setEnrollYear(enrollYear);
            Calendar c=Calendar.getInstance();
            c.set(enrollYear,8,1);
            Date date=c.getTime();
            cmsUser.setEnrollDate(date);
        }
        cmsUser.setStatus(0);
        cmsUser.setDeleted(DeletedEnum.Normal.getCode());
        cmsUser.setCreateTime(new Date());
        cmsUser.setGroupId(0);

        int affect = cmsUserDao.insert(cmsUser);
        AssertUtils.state(affect == 1);
        logger.info("保存CmsUser affect:{}, cmsUser: {}", affect, cmsUser);

        CmsUserAccount cmsUserAccount = new CmsUserAccount();
        cmsUserAccount.setStuId(stuId);
        String saltString = stuId+"fdu".concat(stuId);
        String digestPassword = DigestUtils.md5Hex(saltString);
        cmsUserAccount.setSalt(stuId);
        cmsUserAccount.setPassword(digestPassword);
        cmsUserAccountService.saveCmsUserAccount(cmsUserAccount);
    }

    /**
     * 根据id更新处理
     */
    @Override
    public void updateCmsUserById(CmsUserUpdateMngVo updateVo) {
        Long userId = updateVo.getId();
        CmsUserQuery existsQuery = CmsUserQuery.singletonQuery();
        existsQuery.setId(userId);
        List<CmsUser> updateUsers = cmsUserDao.selectListByParam(existsQuery);
        if (CollectionUtils.isEmpty(updateUsers)) {
            throw new BusinessException("更新的用户不存在");
        } //以上操作是判断这个id的用户是否存在

        CmsUser updateUser = updateUsers.get(0);//得到需要更新的用户
        //updateUser是已知的需要更改的用户的原信息
        CmsUser updater = new CmsUser();

        updater.setId(updateUser.getId());
        updater.setType(updateVo.getType());
        updater.setIsSystemMng(updateUser.getIsSystemMng());
        updater.setIsTalkMng(updateUser.getIsTalkMng());
        updater.setIsLabMng(updateUser.getIsLabMng());
        updater.setIsPaperMng(updateUser.getIsPaperMng());
        updater.setIsEducateMng(updateUser.getIsEducateMng());
        updater.setIsDeviceMng(updateUser.getIsDeviceMng());
        if(updateVo.getAddRole()!=null){
            if(updateVo.getAddRole()==1){
                updater.setIsTalkMng(1);
            }
            else if(updateVo.getAddRole()==2){
                updater.setIsLabMng(1);
            }
            else if(updateVo.getAddRole()==3){
                updater.setIsPaperMng(1);
            }
            else if(updateVo.getAddRole()==4){
                updater.setIsEducateMng(1);
            }
            else if(updateVo.getAddRole()==5){
                updater.setIsDeviceMng(1);
            }
        }

        if(updateVo.getDeleteRole()!=null){
            if(updateVo.getDeleteRole()==1){
                updater.setIsTalkMng(0);
            }
            else if(updateVo.getDeleteRole()==2){
                updater.setIsLabMng(0);
            }
            else if(updateVo.getDeleteRole()==3){
                updater.setIsPaperMng(0);
            }
            else if(updateVo.getDeleteRole()==4){
                updater.setIsEducateMng(0);
            }
            else if(updateVo.getDeleteRole()==5){
                updater.setIsDeviceMng(0);
            }
        }

        if(updater.getIsTalkMng()==1 || updater.getIsLabMng()==1 || updater.getIsPaperMng()==1 || updater.getIsEducateMng()==1 || updater.getIsDeviceMng()==1 || updater.getIsSystemMng()==1){
            updater.setRoleId(1);
        }
        if(updater.getIsTalkMng()==0 && updater.getIsLabMng()==0 && updater.getIsPaperMng()==0 && updater.getIsEducateMng()==0 && updater.getIsDeviceMng()==0 && updater.getIsSystemMng()==0){
            updater.setRoleId(0);
        }
        updater.setTelephone(updateVo.getTelephone());
        updater.setEmail(updateVo.getEmail());
        updater.setMentor(updateVo.getMentor());
        updater.setStudyType(updateVo.getStudyType());
        updater.setKeshuo(updateVo.getKeshuo());
        Date enrollDate = updateVo.getEnrollDate();
        if (enrollDate != null) {
            updater.setEnrollDate(enrollDate);
            updater.setEnrollYear(DateExUtils.getYear(enrollDate));
        }
        updater.setPapers(updateVo.getPapers());
        updater.setPatents(updateVo.getPatents());
        updater.setServices(updateVo.getServices());
        updater.setProjects(updateVo.getProjects());
        updater.setGroupId(updateVo.getGroupId());
        updater.setModifyTime(new Date());
        logger.info("updater: {}",updater);
        int affect = cmsUserDao.updateById(updater);
        AssertUtils.state(affect == 1);
        logger.info("管理员更新CmsUser affect:{}, updater: {}", affect, updater);
    }


    @Override
    public void updateCmsUserById(Long userId, CmsUserUpdateVo updateVo) {
        CmsUserQuery existsQuery = CmsUserQuery.singletonQuery();
        existsQuery.setId(userId);
        existsQuery.setDeleted(DeletedEnum.Normal.getCode());
        List<CmsUser> updateUsers = cmsUserDao.selectListByParam(existsQuery);
        if (CollectionUtils.isEmpty(updateUsers)) {
            throw new BusinessException("更新的用户不存在");
        }
        CmsUser updateUser = updateUsers.get(0);

        CmsUser updater = new CmsUser();
        updater.setId(updateUser.getId());
        updater.setName(updateVo.getName());
        updater.setTelephone(ValueUtils.defaultString(updateVo.getTelephone()));
        updater.setEmail(ValueUtils.defaultString(updateVo.getEmail()));
        updater.setMentor(ValueUtils.defaultString(updateVo.getMentor()));
        updater.setLeader(ValueUtils.defaultString(updateVo.getLeader()));
        updater.setStudyType(ValueUtils.defaultInteger(updateVo.getStudyType()));
        updater.setKeshuo(ValueUtils.defaultInteger(updateVo.getKeshuo()));
        Date enrollDate = updateVo.getEnrollDate();
        if (enrollDate != null) {
            updater.setEnrollDate(enrollDate);
            updater.setEnrollYear(DateExUtils.getYear(enrollDate));
        }
        updater.setPapers(updateVo.getPapers());
        updater.setPatents(updateVo.getPatents());
        updater.setServices(updateVo.getServices());
        updater.setProjects(updateVo.getProjects());
        updater.setStatus(0);
        updater.setModifyTime(new Date());

        int affect = cmsUserDao.updateById(updater);
        AssertUtils.state(affect == 1);
        logger.info("管理员更新CmsUser affect:{}, updater: {}", affect, updater);
    }


    /**
     * 根据id删除处理
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void deleteCmsUserById(Long userId) {
        CmsUserQuery query = new CmsUserQuery();
        query.setId(userId);
        query.setDeleted(DeletedEnum.Normal.getCode());
        query.setLimit(1);
        List<CmsUser> cmsUsers = cmsUserDao.selectListByParam(query);
        if (CollectionUtils.isEmpty(cmsUsers)) {
            throw new BusinessException("删除的用户不存在");
        }
        CmsUser cmsUser = cmsUsers.get(0);
        int affect = cmsUserDao.deleteById(userId);
        AssertUtils.state(affect == 1);
        logger.info("删除CmsUser affect:{}, id: {}", affect, JsonUtils.toJsonString(userId));
        cmsUserAccountService.deleteCmsUserAccountByStuId(cmsUser.getStuId());
    }

    /**
     * 查询数据列表
     */
    @Override
    public List<CmsUserVo> queryUserList(CmsUserQueryVo queryVo, Paging paging) {
        CmsUserQuery query = CmsUserQuery.listQuery();
        query.setId(queryVo.getId());
        query.setType(queryVo.getType());
        query.setStuId(queryVo.getStuId());
        query.setRoleId(queryVo.getRoleId());
        query.setName(queryVo.getName());
        query.setTelephone(queryVo.getTelephone());
        query.setEmail(queryVo.getEmail());
        query.setMentor(queryVo.getMentor());
        query.setLeader(queryVo.getLeader());
        query.setStudyType(queryVo.getStudyType());
        query.setKeshuo(queryVo.getKeshuo());
        query.setEnrollYear(queryVo.getEnrollYear());
        query.setEnrollDate(queryVo.getEnrollDate());
        query.setStatus(queryVo.getStatus());
        query.setOffset(paging.getOffset());
        query.setLimit(paging.getLimit());
        query.setSorts(SortColumn.create(CmsConstants.CreatedTimeColumn, SortMode.DESC));
        List<CmsUser> cmsUserList = cmsUserDao.selectListByParam(query);

        return cmsUserList.stream()
                .map(this::convertCmsUserVo).collect(Collectors.toList());
    }

    /**
     * 分页查询数据列表
     */
    @Override
    public PagingResult<CmsUserVo> queryPagingResult(CmsUserQueryVo queryVo, Paging paging, String name) {
        PagingResult<CmsUserVo> pagingResult = PagingResult.create(paging);

        CmsUserQuery query = CmsUserQuery.listQuery(); //CmsUserQuery groupId已改
        query.setId(queryVo.getId());
        query.setType(queryVo.getType());
        query.setStuId(queryVo.getStuId());
        query.setRoleId(queryVo.getRoleId());
        query.setIsSystemMng(queryVo.getIsSystemMng());
        query.setIsTalkMng(queryVo.getIsTalkMng());
        query.setIsLabMng(queryVo.getIsLabMng());
        query.setIsPaperMng(queryVo.getIsPaperMng());
        query.setIsEducateMng(queryVo.getIsEducateMng());
        query.setIsDeviceMng(queryVo.getIsDeviceMng());
        if(name != ""){
            query.setName(name);
        }
        query.setTelephone(queryVo.getTelephone());
        query.setEmail(queryVo.getEmail());
        query.setMentor(queryVo.getMentor());
        query.setLeader(queryVo.getLeader());
        query.setStudyType(queryVo.getStudyType());
        query.setKeshuo(queryVo.getKeshuo());
        query.setEnrollYear(queryVo.getEnrollYear());
        query.setEnrollDate(queryVo.getEnrollDate());
        query.setStatus(queryVo.getStatus());
        Long count = cmsUserDao.selectCountByParam(query);
        pagingResult.setTotal(count);

        if (count > 0L) {
            query.setOffset(paging.getOffset());
            query.setLimit(paging.getLimit());
            query.setSorts(SortColumn.create(CmsConstants.CreatedTimeColumn, SortMode.DESC));
            List<CmsUser> cmsUserList = cmsUserDao.selectListByParam(query);
            pagingResult.setRows(cmsUserList, this::convertCmsUserVo);
        }

        return pagingResult;
    }

    @Override
    public PagingResult<CmsUserVo> queryStudentResult(CmsUserQueryVo queryVo, Paging paging, String name) {
        PagingResult<CmsUserVo> pagingResult = PagingResult.create(paging);

        CmsUserQuery query = CmsUserQuery.listQuery();
        query.setId(queryVo.getId());
        query.setType(queryVo.getType());
        query.setStuId(queryVo.getStuId());
        query.setRoleId(queryVo.getRoleId());
        query.setIsSystemMng(queryVo.getIsSystemMng());
        query.setIsTalkMng(queryVo.getIsTalkMng());
        query.setIsLabMng(queryVo.getIsLabMng());
        query.setIsPaperMng(queryVo.getIsPaperMng());
        query.setIsEducateMng(queryVo.getIsEducateMng());
        query.setIsDeviceMng(queryVo.getIsDeviceMng());
        if(name != ""){
            query.setName(name);
        }
        if(queryVo.getGroupId() != 0 && queryVo.getGroupId() != null) { //当请求的groupId的值不为0或空
            query.setGroupId(queryVo.getGroupId());
        }
        query.setTelephone(queryVo.getTelephone());
        query.setEmail(queryVo.getEmail());
        query.setMentor(queryVo.getMentor());
        query.setLeader(queryVo.getLeader());
        query.setStudyType(queryVo.getStudyType());
        query.setKeshuo(queryVo.getKeshuo());
        query.setEnrollYear(queryVo.getEnrollYear());
        query.setEnrollDate(queryVo.getEnrollDate());
        query.setStatus(queryVo.getStatus());
        Long count = cmsUserDao.selectStudentCountByParam(query);
        pagingResult.setTotal(count);

        if (count > 0L) {
            query.setOffset(paging.getOffset());
            query.setLimit(paging.getLimit());
            query.setSorts(SortColumn.create(CmsConstants.CreatedTimeColumn, SortMode.DESC));
            List<CmsUser> cmsUserList = cmsUserDao.selectStudentListByParam(query);
            pagingResult.setRows(cmsUserList, this::convertCmsUserVo);
        }
        return pagingResult;
    }

    @Override
    public List<CmsUserVo> queryAvailableAllocationUserList(CmsUserStudyPlanQueryVo queryVo) {
        CmsUserQuery query = CmsUserQuery.listQuery();
        query.setId(queryVo.getUserId());
        query.setType(UserTypeEnum.Student.getCode());
        query.setStuId(queryVo.getStuId());
        query.setName(queryVo.getName());
        query.setTelephone(queryVo.getTelephone());
        query.setEmail(queryVo.getEmail());
        query.setMentor(queryVo.getMentor());
        query.setLeader(queryVo.getLeader());
        query.setStudyType(queryVo.getStudyType());
        query.setKeshuo(queryVo.getKeshuo());
        query.setEnrollYear(queryVo.getEnrollYear());
        query.setEnrollDate(queryVo.getEnrollDate());
        query.setStatus(queryVo.getStatus());

        List<CmsUser> cmsUserList = cmsUserDao.selectAvailableAllocationUserListByParam(query);
        return cmsUserList.stream().map(this::convertCmsUserVo).collect(Collectors.toList());
    }

    @Override
    public List<CmsUser> forceQueryPagingResult(CmsUserQueryVo queryVo,Paging paging){
        PagingResult<CmsUserVo> pagingResult = PagingResult.create(paging);
        List<CmsUser> cmsUserList=null;

        CmsUserQuery query = CmsUserQuery.listQuery();
        query.setId(queryVo.getId());
        query.setType(queryVo.getType());
        query.setStuId(queryVo.getStuId());
        query.setRoleId(queryVo.getRoleId());
        query.setIsSystemMng(queryVo.getIsSystemMng());
        query.setIsTalkMng(queryVo.getIsTalkMng());
        query.setIsLabMng(queryVo.getIsLabMng());
        query.setIsPaperMng(queryVo.getIsPaperMng());
        query.setIsEducateMng(queryVo.getIsEducateMng());
        query.setIsDeviceMng(queryVo.getIsDeviceMng());
        query.setName(queryVo.getName());
        query.setTelephone(queryVo.getTelephone());
        query.setEmail(queryVo.getEmail());
        query.setMentor(queryVo.getMentor());
        query.setLeader(queryVo.getLeader());
        query.setStudyType(queryVo.getStudyType());
        query.setKeshuo(queryVo.getKeshuo());
        query.setEnrollYear(queryVo.getEnrollYear());
        query.setEnrollDate(queryVo.getEnrollDate());
        query.setStatus(queryVo.getStatus());
        query.setGroupId(queryVo.getGroupId());
        Long count = cmsUserDao.selectCountByParam(query);
        pagingResult.setTotal(count);

        if (count > 0L) {
            query.setOffset(paging.getOffset());
            query.setLimit(paging.getLimit());
            query.setSorts(SortColumn.create(CmsConstants.CreatedTimeColumn, SortMode.DESC));
            cmsUserList = cmsUserDao.selectListByParam(query);
        }
        return cmsUserList;
    }

    private CmsUserVo convertCmsUserVo(CmsUser cmsUser) {
        CmsUserVo userVo = new CmsUserVo();
        userVo.setGroupId(cmsUser.getGroupId());
        userVo.setId(cmsUser.getId());
        userVo.setType(cmsUser.getType());
        userVo.setStuId(cmsUser.getStuId());
        userVo.setRoleId(cmsUser.getRoleId());
        userVo.setName(cmsUser.getName());
        userVo.setMentor(cmsUser.getMentor());
        userVo.setIsTalkMng(cmsUser.getIsTalkMng());
        return userVo;
    }

    @Override
    public CmsUserDetailVo queryUserDetail(String stuId) {
        AssertUtils.hasText(stuId, "学号/工号不能为空");
        CmsUser cmsUser = cmsUserComponent.queryUser(stuId);
        AssertUtils.notNull(cmsUser, "用户[" + stuId + "]不存在");

        CmsUserDetailVo detailVo = new CmsUserDetailVo();
        detailVo.setId(cmsUser.getId());
        detailVo.setType(cmsUser.getType());
        detailVo.setStuId(cmsUser.getStuId());
        detailVo.setRoleId(cmsUser.getRoleId());
        detailVo.setIsSystemMng(cmsUser.getIsSystemMng());
        detailVo.setIsTalkMng(cmsUser.getIsTalkMng());
        detailVo.setIsLabMng(cmsUser.getIsLabMng());
        detailVo.setIsPaperMng(cmsUser.getIsPaperMng());
        detailVo.setIsEducateMng(cmsUser.getIsEducateMng());
        detailVo.setIsDeviceMng(cmsUser.getIsDeviceMng());
        detailVo.setName(cmsUser.getName());
        detailVo.setTelephone(cmsUser.getTelephone());
        detailVo.setEmail(cmsUser.getEmail());
        detailVo.setMentor(cmsUser.getMentor());
        detailVo.setLeader(cmsUser.getLeader());
        detailVo.setStudyType(cmsUser.getStudyType());
        detailVo.setKeshuo(cmsUser.getKeshuo());
        detailVo.setEnrollYear(cmsUser.getEnrollYear());
        detailVo.setPapers(cmsUser.getPapers());
        detailVo.setPatents(cmsUser.getPatents());
        detailVo.setServices(cmsUser.getServices());
        detailVo.setProjects(cmsUser.getProjects());
        detailVo.setGroupId(cmsUser.getGroupId());
        detailVo.setStatus(cmsUser.getStatus());
        detailVo.setCreateTime(cmsUser.getCreateTime());
        detailVo.setModifyTime(cmsUser.getModifyTime());
        return detailVo;
    }

    @Override
    public List<CmsUser> queryStudentList(Integer type){
        CmsUserQuery cmsUserQuery =CmsUserQuery.listQuery();
        cmsUserQuery.setType(type);
        List<CmsUser> studentList = cmsUserDao.selectStudentList(cmsUserQuery);
        return studentList;
    }

    @Override
    public List<CmsUser> queryTeacherStudentList(String stuName){
        List<CmsUser> cmsUserList=cmsUserDao.queryTeacherStudentList(stuName);
        return cmsUserList;
    }

    @Override
    public List<CmsUser> queryLeaderStudentList(String stuName){
        List<CmsUser> cmsUserList=cmsUserDao.queryLeaderStudentList(stuName);
        return cmsUserList;
    }

    @Override
    public PagingResult<CmsUserVo> getMasterFromMentor(String name, Paging paging){
        PagingResult<CmsUserVo> pagingResult = new PagingResult<>();
        CmsUserQuery query=CmsUserQuery.listQuery();
        query.setMentor(name);
        query.setType(0);
        Long count = cmsUserDao.selectMentorStudentCountByParam(query);
        pagingResult.setTotal(count);

        if(count>0L){
            query.setLimit(paging.getLimit());
            query.setOffset(paging.getOffset());
            List<CmsUser> cmsUserList=cmsUserDao.selectMentorStudentListByParam(query);
            pagingResult.setRows(cmsUserList,this::convertCmsUserVo);
        }

        return pagingResult;
    }
}