package com.fudanuniversity.cms.business.service.impl;

import com.fudanuniversity.cms.business.service.CmsUserAccountService;
import com.fudanuniversity.cms.business.vo.user.CmsUserAccountLoginVo;
import com.fudanuniversity.cms.business.vo.user.CmsUserAccountResetPasswordVo;
import com.fudanuniversity.cms.commons.enums.DeletedEnum;
import com.fudanuniversity.cms.commons.exception.BusinessException;
import com.fudanuniversity.cms.commons.util.AssertUtils;
import com.fudanuniversity.cms.repository.dao.CmsUserAccountDao;
import com.fudanuniversity.cms.repository.entity.CmsUserAccount;
import com.fudanuniversity.cms.repository.query.CmsUserAccountQuery;
import com.fudanuniversity.cms.repository.query.CmsUserQuery;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * CmsUserAccountService 实现类
 * <p>
 * Created by Xinyue.Tang at 2021-05-01
 */
@Service
public class CmsUserAccountServiceImpl implements CmsUserAccountService {

    private static final Logger logger = LoggerFactory.getLogger(CmsUserAccountServiceImpl.class);

    @Resource
    private CmsUserAccountDao cmsUserAccountDao;

    @Override
    public CmsUserAccount loginAccount(CmsUserAccountLoginVo accountLoginVo) {
        CmsUserAccount userAccount = queryUserAccount(accountLoginVo.getStuId());
        System.out.println(accountLoginVo.getStuId());
        String saltString = userAccount.getSalt() + accountLoginVo.getPassword();
        String digestPassword = DigestUtils.md5Hex(saltString);
        if (!Objects.equals(userAccount.getPassword(), digestPassword)) {
            throw new BusinessException("密码不正确");
        }
        return userAccount;
    }

    @Override
    public void resetAccountPassword(String stuId, CmsUserAccountResetPasswordVo resetPasswordVo) {
        if (Objects.equals(resetPasswordVo.getOldPassword(), resetPasswordVo.getNewPassword())) {
            throw new BusinessException("新密码和原密码相同");
        }
        CmsUserAccount userAccount = queryUserAccount(stuId);
        String oldSaltString = userAccount.getSalt() + resetPasswordVo.getOldPassword();
        String oldDigestPassword = DigestUtils.md5Hex(oldSaltString);
        if (!Objects.equals(userAccount.getPassword(), oldDigestPassword)) {
            throw new BusinessException("原密码不正确");
        }

        String newSaltString = userAccount.getSalt() + resetPasswordVo.getNewPassword();
        String newDigestPassword = DigestUtils.md5Hex(newSaltString);
        CmsUserAccount updater = new CmsUserAccount();
        updater.setId(userAccount.getId());
        updater.setPassword(newDigestPassword);
        updater.setModifyTime(new Date());
        int affect = cmsUserAccountDao.updateById(updater);
        AssertUtils.state(affect == 1);
    }

    @Override
    public void forceResetAccountPassword(Long id){
        CmsUserAccountQuery query=new CmsUserAccountQuery();
        query.setId(id);

        query.setLimit(1);
        List<CmsUserAccount> cmsUserAccounts=cmsUserAccountDao.selectListByParam(query);
        CmsUserAccount cmsUserAccount=cmsUserAccounts.get(0);
        String stuId=cmsUserAccount.getStuId();
        String saltString=stuId+"fdu".concat(stuId);
        String digestPassword=DigestUtils.md5Hex(saltString);
        cmsUserAccount.setPassword(digestPassword);
        cmsUserAccountDao.updateById(cmsUserAccount);
    }

    private CmsUserAccount queryUserAccount(String stuId) {
        CmsUserAccountQuery accountQuery = new CmsUserAccountQuery();
        accountQuery.setStuId(stuId);
        accountQuery.setLimit(1);
        List<CmsUserAccount> userAccounts = cmsUserAccountDao.selectListByParam(accountQuery);
        if (CollectionUtils.isEmpty(userAccounts)) {
            throw new BusinessException("账号[" + stuId + "]不存在");
        }
        return userAccounts.get(0);
    }

    /**
     * 保存处理
     */
    @Override
    public void saveCmsUserAccount(CmsUserAccount cmsUserAccount) {
        cmsUserAccount.setCreateTime(new Date());
        int affect = cmsUserAccountDao.replace(cmsUserAccount);
        logger.info("保存CmsUserAccount affect:{}, cmsUserAccount: {}", affect, cmsUserAccount);
    }

    /**
     * 根据stuId删除处理
     */
    @Override
    public void deleteCmsUserAccountByStuId(String stuId) {
        int affect = cmsUserAccountDao.deleteByStuId(stuId);
        logger.info("删除CmsUserAccount affect:{}, stuId: {}", affect, stuId);
    }
}