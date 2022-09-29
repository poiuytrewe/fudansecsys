package com.fudanuniversity.cms.repository.dao.impl;

import com.fudanuniversity.cms.commons.util.AssertUtils;
import com.fudanuniversity.cms.repository.dao.CmsUserDao;
import com.fudanuniversity.cms.repository.entity.CmsScore;
import com.fudanuniversity.cms.repository.entity.CmsUser;
import com.fudanuniversity.cms.repository.mapper.CmsUserMapper;
import com.fudanuniversity.cms.repository.query.CmsUserQuery;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
//Dao层和数据库进行交互
/**
 * CmsUserDao 实现类
 * <p>
 * Created by Xinyue.Tang at 2021-05-02
 */
@Repository
public class CmsUserDaoImpl implements CmsUserDao {

    @Resource
    private CmsUserMapper cmsUserMapper;

    @Override
    public int insert(CmsUser cmsUser) {
        Assert.notNull(cmsUser, "保存对象不能为空");


        return cmsUserMapper.insert(cmsUser);
    }

    @Override
    public int bulkUpsert(List<CmsUser> cmsUserList) {
        Assert.notEmpty(cmsUserList, "保存对象列表不能为空");

        for (CmsUser cmsUser : cmsUserList) {
            validateEntity(cmsUser);
        }

        return cmsUserMapper.bulkUpsert(cmsUserList);
    }

    private void validateEntity(CmsUser cmsUser) {
        Assert.notNull(cmsUser.getType(), "用户类型不能为空");
        Assert.notNull(cmsUser.getStuId(), "学号不能为空");
        Assert.notNull(cmsUser.getRoleId(), "权限身份不能为空");
        Assert.notNull(cmsUser.getName(), "用户名不能为空");
        Assert.notNull(cmsUser.getTelephone(), "手机不能为空");
        Assert.notNull(cmsUser.getEmail(), "邮箱不能为空");
        Assert.notNull(cmsUser.getMentor(), "导师不能为空");
        Assert.notNull(cmsUser.getLeader(), "汇报人不能为空");
        Assert.notNull(cmsUser.getStudyType(), "就读类型不能为空");
        Assert.notNull(cmsUser.getKeshuo(), "论文不能为空");
        Assert.notNull(cmsUser.getStatus(), "状态不能为空");
        Assert.notNull(cmsUser.getDeleted(), "删除状态不能为空");
        Assert.notNull(cmsUser.getCreateTime(), "创建时间不能为空");
    }

    @Override
    public int updateById(CmsUser cmsUser) {
        Assert.notNull(cmsUser, "更新对象不能为空");
        Assert.notNull(cmsUser.getId(), "更新对象id不能为空");
        Assert.notNull(cmsUser.getModifyTime(), "更新时间不能为空");

        return cmsUserMapper.updateById(cmsUser);
    }

    @Override
    public int deleteById(Long id) {
        Assert.notNull(id, "删除记录id不能为空");

        return cmsUserMapper.deleteById(id);
    }

    @Override
    public List<CmsUser> selectListByParam(CmsUserQuery query) {
        Assert.notNull(query, "查询参数不能为空");

        validateQueryParameter(query);

        return cmsUserMapper.selectListByParam(query);
    }

    @Override
    public List<CmsUser> selectStudentListByParam(CmsUserQuery query){
        Assert.notNull(query, "查询参数不能为空");

        validateQueryParameter(query);

        return cmsUserMapper.selectStudentListByParam(query);
    }

    @Override
    public Long selectCountByParam(CmsUserQuery query) {
        Assert.notNull(query, "查询参数不能为空");

        validateQueryParameter(query);

        return cmsUserMapper.selectCountByParam(query);
    }

    @Override
    public Long selectStudentCountByParam(CmsUserQuery query){
        Assert.notNull(query,"参数不能为空");
        return cmsUserMapper.selectStudentCountByParam(query);
    }

    @Override
    public Long selectMentorStudentCountByParam(CmsUserQuery query){
        Assert.notNull(query,"参数不能为空");
        return cmsUserMapper.selectMentorStudentCountByParam(query);
    }

    @Override
    public List<CmsUser> selectMentorStudentListByParam(CmsUserQuery query){
        Assert.notNull(query,"参数不能为空");
        return cmsUserMapper.selectMentorStudentListByParam(query);
    }

    @Override
    public List<CmsUser> selectAvailableAllocationUserListByParam(CmsUserQuery query) {
        Assert.notNull(query, "查询参数不能为空");

        validateQueryParameter(query);

        return cmsUserMapper.selectAvailableAllocationUserListByParam(query);
    }

    @Override
    public void resetGroupId(){
        cmsUserMapper.resetGroupId();
    }

    @Override
    public List<CmsUser> selectStudentList(CmsUserQuery cmsUserQuery){
        return cmsUserMapper.selectStudentList(cmsUserQuery);
    }

    private void validateQueryParameter(CmsUserQuery query) {
        query.validateBaseArgument();

        /*if (query.getId() == null
                && query.getGtId() == null
               && query.getStuId() == null) {
            throw new UnsupportedOperationException("请通过索引查询！");
        }*/
    }

    @Override
    public List<CmsUser> queryTeacherStudentList(String stuName){
        Assert.notNull(stuName,"导师姓名不能为空");
        return cmsUserMapper.queryTeacherStudentList(stuName);
    }

    @Override
    public List<CmsUser> queryLeaderStudentList(String stuName){
        Assert.notNull(stuName,"带组博士姓名不能为空");
        return cmsUserMapper.queryLeaderStudentList(stuName);
    }
}