package com.fudanuniversity.cms.repository.dao.impl;

import com.fudanuniversity.cms.repository.dao.CmsDeviceDao;
import com.fudanuniversity.cms.repository.entity.CmsDevice;
import com.fudanuniversity.cms.repository.mapper.CmsDeviceMapper;
import com.fudanuniversity.cms.repository.query.CmsDeviceQuery;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * CmsDeviceDao 实现类
 * <p>
 * Created by Xinyue.Tang at 2021-05-03
 */
@Repository
public class CmsDeviceDaoImpl implements CmsDeviceDao {

    @Resource
    CmsDeviceMapper cmsDeviceMapper;

    @Override
    public int insert(CmsDevice cmsDevice) {
        Assert.notNull(cmsDevice, "保存对象不能为空");

        validateEntity(cmsDevice);

        return cmsDeviceMapper.insert(cmsDevice);
    }

    @Override
    public int bulkUpsert(List<CmsDevice> cmsDeviceList) {
        Assert.notEmpty(cmsDeviceList, "保存对象列表不能为空");

        for (CmsDevice cmsDevice : cmsDeviceList) {
            validateEntity(cmsDevice);
        }

        return cmsDeviceMapper.bulkUpsert(cmsDeviceList);
    }

    private void validateEntity(CmsDevice cmsDevice) {
        Assert.notNull(cmsDevice.getType(), "设备类型不能为空");
        Assert.notNull(cmsDevice.getModel(), "设备型号不能为空");
        Assert.notNull(cmsDevice.getName(), "设备名称不能为空");
        Assert.notNull(cmsDevice.getPrincipal(), "负责人姓名不能为空");
        Assert.notNull(cmsDevice.getDeleted(), "删除状态不能为空");
        Assert.notNull(cmsDevice.getCreateTime(), "创建时间不能为空");
    }

    @Override
    public int updateById(CmsDevice cmsDevice) {
        Assert.notNull(cmsDevice, "更新对象不能为空");
        Assert.notNull(cmsDevice.getId(), "更新对象id不能为空");
        Assert.notNull(cmsDevice.getModifyTime(), "更新时间不能为空");

        return cmsDeviceMapper.updateById(cmsDevice);
    }

    @Override
    public int deleteById(Long id) {
        Assert.notNull(id, "删除记录id不能为空");

        return cmsDeviceMapper.deleteById(id);
    }

    @Override
    public CmsDevice selectByIdForUpdate(Long id) {
        Assert.notNull(id, "查询记录id不能为空");

        return cmsDeviceMapper.selectByIdForUpdate(id);
    }

    @Override
    public List<CmsDevice> selectListByParam(CmsDeviceQuery query) {
        Assert.notNull(query, "查询参数不能为空");

        validateQueryParameter(query);

        return cmsDeviceMapper.selectListByParam(query);
    }

    @Override
    public Long selectCountByParam(CmsDeviceQuery query) {
        Assert.notNull(query, "查询参数不能为空");

        validateQueryParameter(query);

        return cmsDeviceMapper.selectCountByParam(query);
    }

    private void validateQueryParameter(CmsDeviceQuery query) {
        query.validateBaseArgument();

        /*if (query.getId() == null
                && query.getGtId() == null) {
            throw new UnsupportedOperationException("请通过索引查询！");
        }*/
    }
}