package com.fudanuniversity.cms.business.service.impl;

import com.fudanuniversity.cms.business.component.CmsDeviceComponent;
import com.fudanuniversity.cms.business.component.CmsUserComponent;
import com.fudanuniversity.cms.business.service.CmsDeviceAllocationService;
import com.fudanuniversity.cms.business.vo.device.CmsDeviceAllocationApplyVo;
import com.fudanuniversity.cms.business.vo.device.CmsDeviceAllocationReturnVo;
import com.fudanuniversity.cms.business.vo.device.CmsDeviceAllocationVo;
import com.fudanuniversity.cms.commons.constant.CmsConstants;
import com.fudanuniversity.cms.commons.enums.DeviceAllocationStatusEnum;
import com.fudanuniversity.cms.commons.exception.BusinessException;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.model.query.SortColumn;
import com.fudanuniversity.cms.commons.model.query.SortMode;
import com.fudanuniversity.cms.commons.util.AssertUtils;
import com.fudanuniversity.cms.repository.dao.CmsDeviceAllocationDao;
import com.fudanuniversity.cms.repository.dao.CmsDeviceDao;
import com.fudanuniversity.cms.repository.entity.CmsDevice;
import com.fudanuniversity.cms.repository.entity.CmsDeviceAllocation;
import com.fudanuniversity.cms.repository.entity.CmsUser;
import com.fudanuniversity.cms.repository.query.CmsDeviceAllocationQuery;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * CmsDeviceAllocationService 实现类
 * <p>
 * Created by Xinyue.Tang at 2021-05-03
 */
@Service
public class CmsDeviceAllocationServiceImpl implements CmsDeviceAllocationService {

    @Resource
    private CmsDeviceDao cmsDeviceDao;

    @Resource
    private CmsDeviceAllocationDao cmsDeviceAllocationDao;

    @Resource
    private CmsDeviceComponent cmsDeviceComponent;

    @Resource
    private CmsUserComponent cmsUserComponent;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void applyDeviceAllocation(Long userId, CmsDeviceAllocationApplyVo allocationApplyVo) {
        Long deviceId = allocationApplyVo.getDeviceId();
        //查询后加锁
        CmsDevice cmsDevice = cmsDeviceDao.selectByIdForUpdate(deviceId);//这是需要更新的设备
        CmsUser cmsUser=cmsUserComponent.queryUser(userId);//这是申请人
        String userName=cmsUser.getName();
        cmsDevice.setBorrow(userName);//将申请人填写
        //此时，cmsDevice已经修改完了
        cmsDevice.setModifyTime(new Date());
        cmsDeviceDao.updateById(cmsDevice);//写入数据库
        AssertUtils.notNull(cmsDevice, "设备不存在");
//        Integer inventoryUsage = allocationApplyVo.getInventoryUsage();
//        if (inventoryUsage > cmsDevice.getInventory()) {
//            throw new BusinessException(String.format(
//                    "超过设备可申请使用限制 %s%s", cmsDevice.getInventory(), cmsDevice.getInventoryUnit()));
//        }
        //减库存
//        changeDeviceInventory(cmsDevice, -inventoryUsage);
        //添加申请记录
        CmsDeviceAllocation deviceAllocation = new CmsDeviceAllocation();
        deviceAllocation.setUserId(userId);
        deviceAllocation.setDeviceId(deviceId);
//        deviceAllocation.setInventoryUsage(inventoryUsage);
        deviceAllocation.setCreateTime(new Date());
        deviceAllocation.setStatus(DeviceAllocationStatusEnum.InUse.getCode());
        int allocationAffect = cmsDeviceAllocationDao.insert(deviceAllocation);
        AssertUtils.state(allocationAffect == 1);
    }

    private void changeDeviceInventory(CmsDevice device, Integer inventory) {
        CmsDevice updater = new CmsDevice();
        updater.setId(device.getId());
//        int newInventory = device.getInventory() + inventory;
//        updater.setInventory(newInventory);
        updater.setModifyTime(new Date());
        int affect = cmsDeviceDao.updateById(updater);
        AssertUtils.state(affect == 1);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void returnDeviceAllocation(Long userId, CmsDeviceAllocationReturnVo allocationReturnVo) {
        Long allocationId = allocationReturnVo.getAllocationId();
        //查询分配记录
        CmsDeviceAllocation deviceAllocation = queryCmsDeviceAllocation(userId, allocationId);
        AssertUtils.notNull(deviceAllocation);
        //查询设备
        Long deviceId = deviceAllocation.getDeviceId();
        String empty="";
        CmsDevice cmsDevice = cmsDeviceDao.selectByIdForUpdate(deviceId);//找到相应的设备
        cmsDevice.setBorrow(empty);//设备归还之后，借用人置空
        cmsDevice.setModifyTime(new Date());
        cmsDeviceDao.updateById(cmsDevice);//更新设备信息

        AssertUtils.notNull(cmsDevice);
        //将分配使用的库存返还
//        changeDeviceInventory(cmsDevice, deviceAllocation.getInventoryUsage());
        //更新设备分配状态为已返还
        CmsDeviceAllocation updater = new CmsDeviceAllocation();
        updater.setId(allocationId);
        updater.setStatus(DeviceAllocationStatusEnum.Returned.getCode());
        updater.setModifyTime(new Date());
        int affect = cmsDeviceAllocationDao.updateById(updater);
        AssertUtils.state(affect == 1);
    }

    private CmsDeviceAllocation queryCmsDeviceAllocation(Long userId, Long allocationId) {
        CmsDeviceAllocationQuery query = CmsDeviceAllocationQuery.singletonQuery();
        query.setId(allocationId);
        query.setUserId(userId);
        List<CmsDeviceAllocation> allocations = cmsDeviceAllocationDao.selectListByParam(query);
        if (CollectionUtils.isNotEmpty(allocations)) {
            return allocations.get(0);
        }
        return null;
    }

    /**
     * 分页查询个人设备分配的数据列表
     */
    @Override
    public PagingResult<CmsDeviceAllocationVo> queryPagingResult(Long userId, Paging paging) {
        PagingResult<CmsDeviceAllocationVo> pagingResult = PagingResult.create(paging);

        CmsDeviceAllocationQuery query = CmsDeviceAllocationQuery.listQuery();
        query.setUserId(userId);
        Long count = cmsDeviceAllocationDao.selectCountByParam(query);
        pagingResult.setTotal(count);

        if (count > 0L) {
            query.setOffset(paging.getOffset());
            query.setLimit(paging.getLimit());
            query.setSorts(SortColumn.create(CmsConstants.CreatedTimeColumn, SortMode.DESC));
            List<CmsDeviceAllocation> allocationList = cmsDeviceAllocationDao.selectListByParam(query);

            List<Long> deviceIds = Lists.transform(allocationList, CmsDeviceAllocation::getDeviceId);
            Map<Long, CmsDevice> cmsDeviceMap = cmsDeviceComponent.queryCmsDeviceMap(deviceIds);

            pagingResult.setRows(allocationList, allocation -> {
                CmsDeviceAllocationVo allocationVo = new CmsDeviceAllocationVo();
                allocationVo.setId(allocation.getId());
                allocationVo.setDeviceId(allocation.getDeviceId());
                CmsDevice cmsDevice = cmsDeviceMap.get(allocation.getDeviceId());
                if (cmsDevice != null) {
                    allocationVo.setDeviceType(cmsDevice.getType());
                    allocationVo.setDeviceModel(cmsDevice.getModel());
                    allocationVo.setDeviceName(cmsDevice.getName());
//                    allocationVo.setInventoryUnit(cmsDevice.getInventoryUnit());
                }
//                allocationVo.setInventoryUsage(allocation.getInventoryUsage());
                allocationVo.setStatus(allocation.getStatus());
                allocationVo.setCreateTime(allocation.getCreateTime());
                allocationVo.setModifyTime(allocation.getModifyTime());
                return allocationVo;
            });
        }

        return pagingResult;
    }
}