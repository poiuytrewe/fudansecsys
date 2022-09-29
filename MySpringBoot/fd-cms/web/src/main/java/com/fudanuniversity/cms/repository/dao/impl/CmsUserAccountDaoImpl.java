package com.fudanuniversity.cms.repository.dao.impl;

import com.fudanuniversity.cms.repository.dao.CmsUserAccountDao;
import com.fudanuniversity.cms.repository.mapper.CmsUserAccountMapper;
import com.fudanuniversity.cms.repository.entity.CmsUserAccount;
import com.fudanuniversity.cms.repository.query.CmsUserAccountQuery;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * CmsUserAccountDao 实现类
 * <p>
 * Created by Xinyue.Tang at 2021-05-01
 */
@Repository
public class CmsUserAccountDaoImpl implements CmsUserAccountDao {

    @Resource
    private CmsUserAccountMapper cmsUserAccountMapper;

    @Override
    public int insert(CmsUserAccount cmsUserAccount) {
        Assert.notNull(cmsUserAccount, "保存对象不能为空");

        validateEntity(cmsUserAccount);

        return cmsUserAccountMapper.insert(cmsUserAccount);
    }

    @Override
    public int replace(CmsUserAccount cmsUserAccount) {
        Assert.notNull(cmsUserAccount, "保存对象不能为空");

        validateEntity(cmsUserAccount);

        return cmsUserAccountMapper.replace(cmsUserAccount);
    }

    @Override
    public int bulkUpsert(List<CmsUserAccount> cmsUserAccountList){
        Assert.notEmpty(cmsUserAccountList, "保存对象列表不能为空");

        for(CmsUserAccount cmsUserAccount : cmsUserAccountList){
            validateEntity(cmsUserAccount);
        }

        return cmsUserAccountMapper.bulkUpsert(cmsUserAccountList);
    }

    private void validateEntity(CmsUserAccount cmsUserAccount) {
        Assert.notNull(cmsUserAccount.getStuId(), "学号不能为空");
        Assert.notNull(cmsUserAccount.getSalt(), "盐不能为空");
        Assert.notNull(cmsUserAccount.getPassword(), "密码不能为空");
        Assert.notNull(cmsUserAccount.getCreateTime(), "创建时间不能为空");
    }

    @Override
    public int updateById(CmsUserAccount cmsUserAccount) {
        Assert.notNull(cmsUserAccount, "更新对象不能为空");
        Assert.notNull(cmsUserAccount.getId(), "更新对象id不能为空");

        return cmsUserAccountMapper.updateById(cmsUserAccount);
    }

    @Override
    public int deleteByStuId(String stuId) {
        Assert.notNull(stuId, "删除记录stuId不能为空");

        return cmsUserAccountMapper.deleteByStuId(stuId);
    }

    @Override
    public List<CmsUserAccount> selectListByParam(CmsUserAccountQuery query) {
        Assert.notNull(query, "查询参数不能为空");

        validateQueryParameter(query);

        return cmsUserAccountMapper.selectListByParam(query);
    }

    @Override
    public Long selectCountByParam(CmsUserAccountQuery query) {
        Assert.notNull(query, "查询参数不能为空");

        validateQueryParameter(query);

        return cmsUserAccountMapper.selectCountByParam(query);
    }

    private void validateQueryParameter(CmsUserAccountQuery query) {
        query.validateBaseArgument();

        /*if (query.getId() == null
                && query.getGtId() == null
               && query.getStuId() == null) {
            throw new UnsupportedOperationException("请通过索引查询！");
        }*/
    }
}