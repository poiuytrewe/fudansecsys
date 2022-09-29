package com.fudanuniversity.cms.business.component;

import com.fudanuniversity.cms.commons.enums.DeletedEnum;
import com.fudanuniversity.cms.commons.exception.BusinessException;
import com.fudanuniversity.cms.commons.util.AssertUtils;
import com.fudanuniversity.cms.repository.dao.CmsUserDao;
import com.fudanuniversity.cms.repository.entity.CmsUser;
import com.fudanuniversity.cms.repository.query.CmsUserQuery;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Xinyue.Tang at 2021-05-02 21:42:45
 */
@Component
public class CmsUserComponent {

    @Resource
    private CmsUserDao cmsUserDao;

    public CmsUser queryUser(Long userId) {
        AssertUtils.notNull(userId, "用户id不能为空");
        CmsUserQuery query = CmsUserQuery.singletonQuery();
        query.setId(userId);
        query.setDeleted(DeletedEnum.Normal.getCode());
        List<CmsUser> users = cmsUserDao.selectListByParam(query);
        if (CollectionUtils.isNotEmpty(users)) {
            return users.get(0);
        }
        return null;
    }

    public CmsUser queryUser(String stuId) {
        AssertUtils.hasText(stuId, "学号/工号不能为空");
        CmsUserQuery query = CmsUserQuery.singletonQuery();
        query.setStuId(stuId);
        query.setDeleted(DeletedEnum.Normal.getCode());
        List<CmsUser> users = cmsUserDao.selectListByParam(query);
        if (CollectionUtils.isNotEmpty(users)) {
            return users.get(0);
        }
        return null;
    }

    public Map<Long, CmsUser> queryUserMap(Long... userIdArray) {
        List<Long> userIds = Arrays.stream(userIdArray)
                .filter(Objects::nonNull).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(userIds)) {
            CmsUserQuery query = CmsUserQuery.listQuery();
            query.setDeleted(DeletedEnum.Normal.getCode());
            List<CmsUser> users = cmsUserDao.selectListByParam(query);
            if (CollectionUtils.isNotEmpty(users)) {
                return users.stream().collect(Collectors.toMap(CmsUser::getId, Function.identity()));
            }
        }
        return Collections.emptyMap();
    }

    public Map<Long, CmsUser> queryUserMap(List<Long> userIds) {
        if (CollectionUtils.isNotEmpty(userIds)) {
            CmsUserQuery query = CmsUserQuery.listQuery();
            query.setLimit(userIds.size());//总共的size
            query.setIdList(userIds);
            List<CmsUser> users = cmsUserDao.selectListByParam(query);
            if (CollectionUtils.isNotEmpty(users)) {
                return users.stream().collect(Collectors.toMap(CmsUser::getId, Function.identity()));
            }
        }
        return Collections.emptyMap();
    }

    public Map<Long, CmsUser> queryExitsUserMap(List<Long> userIds) {
        if (CollectionUtils.isNotEmpty(userIds)) {
            CmsUserQuery query = CmsUserQuery.listQuery();
            query.setDeleted(DeletedEnum.Normal.getCode());
            query.setLimit(userIds.size());
            List<CmsUser> users = cmsUserDao.selectListByParam(query);
            if (CollectionUtils.isNotEmpty(users)) {
                Map<Long, CmsUser> retMap
                        = users.stream().collect(Collectors.toMap(CmsUser::getId, Function.identity()));
                for (Long userId : userIds) {
                    if (!retMap.containsKey(userId)) {
                        throw new BusinessException("用户[" + userId + "]不存在");
                    }
                }
                return retMap;
            }
        }
        return Collections.emptyMap();
    }
}
