package com.fudanuniversity.cms.business.service;

import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyScoreDate;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyScoreVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsUserStudyPlanQueryVo;
import com.fudanuniversity.cms.business.vo.user.*;
import com.fudanuniversity.cms.commons.enums.UserRoleEnum;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.repository.entity.CmsScore;
import com.fudanuniversity.cms.repository.entity.CmsUser;
import com.fudanuniversity.cms.repository.query.CmsUserQuery;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * CmsUserService
 * <p>
 * Created by Xinyue.Tang at 2021-05-01
 */
public interface CmsUserService {

    /**
     * 确认用户是否有管理员权限
     */
    void checkManagePrivilege(String stuId);

    /**
     * 确认用户是否存在某种管理员权限
     */

    void checkSystemMng(String stuId);//查看是否拥有系统管理员权限

    void checkTalkMng(String stuId);//查看是否拥有讨论班管理员权限

    void checkLabMng(String stuId);//查看是否拥有Lab管理员权限

    void checkPaperMng(String stuId);//查看是否拥有论文管理员权限

    void checkEducateMng(String stuId);//查看是否拥有培养方案管理员权限

    void checkDeviceMng(String stuId);//查看是否拥有设备管理员权限

    void checkTeacher(String stuId);
    /**
     * 保存处理
     */
    void checkBackbone(String stuId);

    void checkDoc(String stuId); //检查登录用户是否是博士，是否拥有添加专题的权限

    void saveCmsUser(CmsUserAddVo userAddVo);


    /**
     * 管理员更新用户信息
     */
    void updateCmsUserById(CmsUserUpdateMngVo updateVo);

    /**
     * 用户更新自己用户信息
     */
    void updateCmsUserById(Long userId, CmsUserUpdateVo updateVo);

    /**
     * 根据id删除处理
     */
    void deleteCmsUserById(Long userId);

    /**
     * 分页查询数据列表
     */
    List<CmsUserVo> queryUserList(CmsUserQueryVo queryVo, Paging paging);

    /**
     * 分页查询数据列表
     */
    PagingResult<CmsUserVo> queryPagingResult(CmsUserQueryVo queryVo, Paging paging, String name);

    PagingResult<CmsUserVo> queryStudentResult(CmsUserQueryVo queryVo, Paging paging, String name);



    List<CmsUser> forceQueryPagingResult(CmsUserQueryVo queryVo,Paging paging);

    /**
     *
     */
    List<CmsUserVo> queryAvailableAllocationUserList(CmsUserStudyPlanQueryVo queryVo);

    /**
     * 查询用户详细信息
     */
    CmsUserDetailVo queryUserDetail(@NotNull String stuId);

    List<CmsUser> queryStudentList(Integer type);

    List<CmsUser> queryTeacherStudentList(String stuName);//按照一定顺序，返回某个导师名下的所有学生，比较具有通用性

    List<CmsUser> queryLeaderStudentList(String stuName);

    PagingResult<CmsUserVo> getMasterFromMentor(String name, Paging paging);
}