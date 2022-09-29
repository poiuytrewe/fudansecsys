package com.fudanuniversity.cms.business.component;

import com.fudanuniversity.cms.commons.enums.DeletedEnum;
import com.fudanuniversity.cms.commons.util.AssertUtils;
import com.fudanuniversity.cms.repository.dao.CmsDeviceDao;
import com.fudanuniversity.cms.repository.entity.CmsDevice;
import com.fudanuniversity.cms.repository.query.CmsDeviceQuery;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Xinyue.Tang at 2021-05-03 23:45:56
 */
@Component
public class CmsDeviceComponent {

    @Resource
    private CmsDeviceDao cmsDeviceDao;

    public CmsDevice queryCmsDevice(Long deviceId) {
        AssertUtils.notNull(deviceId);
        CmsDeviceQuery query = CmsDeviceQuery.singletonQuery();
        query.setId(deviceId);
        query.setDeleted(DeletedEnum.Normal.getCode());
        List<CmsDevice> devices = cmsDeviceDao.selectListByParam(query);
        if (CollectionUtils.isNotEmpty(devices)) {
            return devices.get(0);
        }
        return null;
    }

    public Map<Long, CmsDevice> queryCmsDeviceMap(List<Long> deviceIds) {
        if (CollectionUtils.isNotEmpty(deviceIds)) {
            CmsDeviceQuery query = CmsDeviceQuery.listQuery();
            query.setIdList(deviceIds);
            List<CmsDevice> devices = cmsDeviceDao.selectListByParam(query);
            if (CollectionUtils.isNotEmpty(devices)) {
                return devices.stream().collect(Collectors.toMap(CmsDevice::getId, Function.identity()));
            }
        }
        return Collections.emptyMap();
    }
}
