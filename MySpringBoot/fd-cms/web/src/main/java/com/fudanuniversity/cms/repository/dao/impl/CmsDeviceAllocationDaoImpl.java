package com.fudanuniversity.cms.repository.dao.impl;

import com.fudanuniversity.cms.repository.dao.CmsDeviceAllocationDao;
import com.fudanuniversity.cms.repository.mapper.CmsDeviceAllocationMapper;
import com.fudanuniversity.cms.repository.entity.CmsDeviceAllocation;
import com.fudanuniversity.cms.repository.query.CmsDeviceAllocationQuery;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * CmsDeviceAllocationDao 实现类
 * <p>
 * Created by Xinyue.Tang at 2021-05-04
 */
@Repository
public class CmsDeviceAllocationDaoImpl implements CmsDeviceAllocationDao {

    @Resource
    private CmsDeviceAllocationMapper cmsDeviceAllocationMapper;

    @Override
    public int insert(CmsDeviceAllocation cmsDeviceAllocation) {
        Assert.notNull(cmsDeviceAllocation, "保存对象不能为空");

        validateEntity(cmsDeviceAllocation);

        return cmsDeviceAllocationMapper.insert(cmsDeviceAllocation);
    }

    @Override
    public int bulkUpsert(List<CmsDeviceAllocation> cmsDeviceAllocationList){
        Assert.notEmpty(cmsDeviceAllocationList, "保存对象列表不能为空");

        for(CmsDeviceAllocation cmsDeviceAllocation : cmsDeviceAllocationList){
            validateEntity(cmsDeviceAllocation);
        }

        return cmsDeviceAllocationMapper.bulkUpsert(cmsDeviceAllocationList);
    }

    private void validateEntity(CmsDeviceAllocation cmsDeviceAllocation) {
        Assert.notNull(cmsDeviceAllocation.getUserId(), "用户id不能为空");
        Assert.notNull(cmsDeviceAllocation.getDeviceId(), "设备id不能为空");
        Assert.notNull(cmsDeviceAllocation.getStatus(), "状态不能为空");
        Assert.notNull(cmsDeviceAllocation.getCreateTime(), "创建时间不能为空");
    }

    @Override
    public int updateById(CmsDeviceAllocation cmsDeviceAllocation) {
        Assert.notNull(cmsDeviceAllocation, "更新对象不能为空");
        Assert.notNull(cmsDeviceAllocation.getId(), "更新对象id不能为空");
        Assert.notNull(cmsDeviceAllocation.getModifyTime(), "更新时间不能为空");

        return cmsDeviceAllocationMapper.updateById(cmsDeviceAllocation);
    }

    @Override
    public int deleteById(Long id) {
        Assert.notNull(id, "删除记录id不能为空");

        return cmsDeviceAllocationMapper.deleteById(id);
    }

    @Override
    public List<CmsDeviceAllocation> selectListByParam(CmsDeviceAllocationQuery query) {
        Assert.notNull(query, "查询参数不能为空");

        validateQueryParameter(query);

        return cmsDeviceAllocationMapper.selectListByParam(query);
    }

    @Override
    public Long selectCountByParam(CmsDeviceAllocationQuery query) {
        Assert.notNull(query, "查询参数不能为空");

        validateQueryParameter(query);

        return cmsDeviceAllocationMapper.selectCountByParam(query);
    }

    private void validateQueryParameter(CmsDeviceAllocationQuery query) {
        query.validateBaseArgument();

        /*if (query.getId() == null
                && query.getGtId() == null
               && query.getDeviceId() == null
               && query.getUserId() == null) {
            throw new UnsupportedOperationException("请通过索引查询！");
        }*/
    }
}