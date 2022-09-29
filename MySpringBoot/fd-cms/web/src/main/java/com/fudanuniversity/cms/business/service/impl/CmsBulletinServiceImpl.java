package com.fudanuniversity.cms.business.service.impl;

import com.fudanuniversity.cms.business.service.CmsBulletinService;
import com.fudanuniversity.cms.business.vo.bulletin.*;
import com.fudanuniversity.cms.commons.constant.CmsConstants;
import com.fudanuniversity.cms.commons.enums.BooleanEnum;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.model.query.SortColumn;
import com.fudanuniversity.cms.commons.model.query.SortMode;
import com.fudanuniversity.cms.commons.util.AssertUtils;
import com.fudanuniversity.cms.repository.dao.CmsBulletinDao;
import com.fudanuniversity.cms.repository.entity.CmsBulletin;
import com.fudanuniversity.cms.repository.query.CmsBulletinQuery;
import com.fudanuniversity.cms.repository.query.CmsBulletinStateQuery;
import org.apache.commons.collections4.CollectionUtils;
import org.checkerframework.checker.units.qual.C;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * CmsBulletinService 实现类
 * <p>
 * Created by Xinyue.Tang at 2021-05-02
 */
@Service
public class CmsBulletinServiceImpl implements CmsBulletinService {

    private static final Logger logger = LoggerFactory.getLogger(CmsBulletinServiceImpl.class);

    @Resource
    private CmsBulletinDao cmsBulletinDao;

    /**
     * 保存处理
     */
    @Override
    public void addNewBulletin(CmsBulletinAddVo addVo) { //增
        CmsBulletin bulletin = new CmsBulletin();
        bulletin.setStuId(addVo.getStuId());
        bulletin.setTitle(addVo.getTitle());
        bulletin.setContent(addVo.getContent());
        bulletin.setIsRead(0);
        bulletin.setCreateTime(new Date());
        int affect = cmsBulletinDao.insert(bulletin);
        logger.info("保存CmsBulletin affect:{}, cmsBulletin: {}", affect, addVo);
        AssertUtils.state(affect == 1);
    }

    /**
     * 根据id更新处理
     */
    @Override
    public void editBulletin(Long id) {//只是修改通知的isRead状态，其余不变
        //TODO 补充状态检测业务逻辑
        CmsBulletin updater = new CmsBulletin();
        updater.setId(id);
        updater.setIsRead(1);
        updater.setModifyTime(new Date());
        int affect = cmsBulletinDao.updateById(updater);
        logger.info("更新CmsBulletin affect:{}, updater: {}", affect, updater);
        AssertUtils.state(affect == 1);
    }

    /**
     * 根据id删除处理
     */
    @Override
    public void deleteCmsBulletinById(Long bulletinId) { //删
        //TODO 补充状态检测业务逻辑
        int affect = cmsBulletinDao.deleteById(bulletinId);
        logger.info("删除CmsBulletin affect:{}, id: {}", affect, bulletinId);
        AssertUtils.state(affect == 1);
    }

    @Override
    public void readAllBulletin(String stuId) { //全部已读
        CmsBulletin bulletin=new CmsBulletin();
        bulletin.setStuId(stuId);
        bulletin.setIsRead(1);
        bulletin.setModifyTime(new Date());
        cmsBulletinDao.readAllBulletin(bulletin);
    }

    @Override
    public PagingResult<CmsBulletinVo> queryPagingResult(CmsBulletinQueryVo queryVo, Paging paging) { //查
        PagingResult<CmsBulletinVo> pagingResult = PagingResult.create(paging);

        CmsBulletinQuery query = new CmsBulletinQuery();
        query.setStuId(queryVo.getStuId());
        query.setId(queryVo.getId());
        query.setTitle(queryVo.getTitle());

        Long count = cmsBulletinDao.selectCountByParam(query);
        pagingResult.setTotal(count);

        if (count > 0L) {
            query.setOffset(paging.getOffset());
            query.setLimit(paging.getLimit());
            query.setSorts(SortColumn.create(CmsConstants.CreatedTimeColumn, SortMode.DESC));
            List<CmsBulletin> bulletins = cmsBulletinDao.selectListByParam(query);

            pagingResult.setRows(bulletins, bulletin -> {
                CmsBulletinVo bulletinVo = new CmsBulletinVo();
                bulletinVo.setId(bulletin.getId());
                bulletinVo.setStuId(bulletin.getStuId());
                bulletinVo.setTitle(bulletin.getTitle());
                bulletinVo.setContent(bulletin.getContent());
                bulletinVo.setIsReadYey(bulletin.getIsRead());
                bulletinVo.setCreateTime(bulletin.getCreateTime());
                bulletinVo.setModifyTime(bulletin.getModifyTime());
                return bulletinVo;
            });
        }

        return pagingResult;
    }

    @Override
    public Long queryCmsBulletinReadState(CmsBulletinStateQueryVo stateQueryVo) { //查看未读数量
        CmsBulletinQuery query=CmsBulletinQuery.singletonQuery();
        query.setStuId(stateQueryVo.getStuId());
        query.setIsRead(0); //选取没读过的数量，度过的就不要了
        Long count=cmsBulletinDao.selectCountByParam(query);
        return count;
    }
}