package com.fudanuniversity.cms.repository.dao.impl;

import com.fudanuniversity.cms.repository.dao.CmsBulletinDao;
import com.fudanuniversity.cms.repository.entity.CmsBulletin;
import com.fudanuniversity.cms.repository.mapper.CmsBulletinMapper;
import com.fudanuniversity.cms.repository.query.CmsBulletinQuery;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * CmsBulletinDao 实现类
 * <p>
 * Created by Xinyue.Tang at 2021-05-02
 */
@Repository
public class CmsBulletinDaoImpl implements CmsBulletinDao {

    @Resource
    private CmsBulletinMapper cmsBulletinMapper;

    @Override
    public int insert(CmsBulletin cmsBulletin) {
        Assert.notNull(cmsBulletin, "保存对象不能为空");

        validateEntity(cmsBulletin);

        return cmsBulletinMapper.insert(cmsBulletin);
    }

    @Override
    public int bulkUpsert(List<CmsBulletin> cmsBulletinList) {
        Assert.notEmpty(cmsBulletinList, "保存对象列表不能为空");

        for (CmsBulletin cmsBulletin : cmsBulletinList) {
            validateEntity(cmsBulletin);
        }

        return cmsBulletinMapper.bulkUpsert(cmsBulletinList);
    }

    private void validateEntity(CmsBulletin cmsBulletin) {
        Assert.notNull(cmsBulletin.getTitle(), "名称不能为空");
        Assert.notNull(cmsBulletin.getContent(), "内容不能为空");
        Assert.notNull(cmsBulletin.getCreateTime(), "创建时间不能为空");
    }

    @Override
    public int updateById(CmsBulletin cmsBulletin) {
        Assert.notNull(cmsBulletin, "更新对象不能为空");
        Assert.notNull(cmsBulletin.getId(), "更新对象id不能为空");
        Assert.notNull(cmsBulletin.getModifyTime(), "更新时间不能为空");

        return cmsBulletinMapper.updateById(cmsBulletin);
    }

    @Override
    public int deleteById(Long id) {
        Assert.notNull(id, "删除记录id不能为空");

        return cmsBulletinMapper.deleteById(id);
    }

    @Override
    public List<CmsBulletin> selectListByParam(CmsBulletinQuery query) {
        Assert.notNull(query, "查询参数不能为空");

        validateQueryParameter(query);

        return cmsBulletinMapper.selectListByParam(query);
    }

    @Override
    public Long selectCountByParam(CmsBulletinQuery query) {
        Assert.notNull(query, "查询参数不能为空");

        return cmsBulletinMapper.selectCountByParam(query);
    }

    @Override
    public void readAllBulletin(CmsBulletin bulletin){
        Assert.notNull(bulletin, "更新对象不能为空");
        Assert.notNull(bulletin.getModifyTime(), "更新时间不能为空");
        cmsBulletinMapper.readAllBulletin(bulletin);
    }

    private void validateQueryParameter(CmsBulletinQuery query) {
        query.validateBaseArgument();
    }
}