package com.fudanuniversity.cms.repository.dao;

import com.fudanuniversity.cms.repository.entity.CmsScore;
import com.fudanuniversity.cms.repository.entity.CmsUser;
import com.fudanuniversity.cms.repository.query.CmsUserQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * CmsUserDao
 * <p>
 * Created by Xinyue.Tang at 2021-05-01
 */
public interface CmsUserDao {

    /**
     * 保存/更新处理
     */
    int insert(CmsUser cmsUser);

    /**
     * 批量upsert
     */
    int bulkUpsert(List<CmsUser> cmsUserList);

    /**
     * 根据id更新处理
     */
    int updateById(CmsUser cmsUser);

    /**
     * 根据id删除
     */
    int deleteById(Long id);

    /**
     * 查询数据
     */
    List<CmsUser> selectListByParam(CmsUserQuery query);

    List<CmsUser> selectStudentListByParam(CmsUserQuery query);

    /**
     * 查询数量
     */
    Long selectCountByParam(CmsUserQuery query);

    Long selectStudentCountByParam(CmsUserQuery query);

    Long selectMentorStudentCountByParam(CmsUserQuery query);

    List<CmsUser> selectMentorStudentListByParam(CmsUserQuery query);

    /**
     *
     */
    List<CmsUser> selectAvailableAllocationUserListByParam(CmsUserQuery query);

    void resetGroupId();

    List<CmsUser> selectStudentList(CmsUserQuery cmsUserQuery);

    List<CmsUser> queryTeacherStudentList(String stuName);

    List<CmsUser> queryLeaderStudentList(String stuName);
}
