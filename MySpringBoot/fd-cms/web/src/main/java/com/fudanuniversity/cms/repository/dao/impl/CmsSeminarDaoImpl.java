package com.fudanuniversity.cms.repository.dao.impl;

import com.fudanuniversity.cms.repository.dao.CmsSeminarDao;
import com.fudanuniversity.cms.repository.mapper.CmsSeminarMapper;
import com.fudanuniversity.cms.repository.entity.CmsSeminar;
import com.fudanuniversity.cms.repository.query.CmsSeminarQuery;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * CmsSeminarDao 实现类
 * <p>
 * Created by Xinyue.Tang at 2021-05-02
 */
@Repository
public class CmsSeminarDaoImpl implements CmsSeminarDao {

    @Resource
    private CmsSeminarMapper cmsSeminarMapper;

    @Override
    public int insert(CmsSeminar cmsSeminar) {
        Assert.notNull(cmsSeminar, "保存对象不能为空");

        validateEntity(cmsSeminar);

        return cmsSeminarMapper.insert(cmsSeminar);
    }

    @Override
    public int bulkUpsert(List<CmsSeminar> cmsSeminarList){
        Assert.notEmpty(cmsSeminarList, "保存对象列表不能为空");

        for(CmsSeminar cmsSeminar : cmsSeminarList){
            validateEntity(cmsSeminar);
        }

        return cmsSeminarMapper.bulkUpsert(cmsSeminarList);
    }

    private void validateEntity(CmsSeminar cmsSeminar) {
        Assert.notNull(cmsSeminar.getDate(), "演讲时间不能为空");
    }

    @Override
    public int updateById(CmsSeminar cmsSeminar) {
        Assert.notNull(cmsSeminar, "更新对象不能为空");
        Assert.notNull(cmsSeminar.getId(), "更新对象id不能为空");
        Assert.notNull(cmsSeminar.getModifyTime(), "更新时间不能为空");

        return cmsSeminarMapper.updateById(cmsSeminar);
    }

    @Override
    public int deleteById(Long id) {
        Assert.notNull(id, "删除记录id不能为空");

        return cmsSeminarMapper.deleteById(id);
    }

    @Override
    public List<CmsSeminar> selectListByParam(CmsSeminarQuery query) {
        Assert.notNull(query, "查询参数不能为空");

        validateQueryParameter(query);

        return cmsSeminarMapper.selectListByParam(query);
    }

    @Override
    public Long selectCountByParam(CmsSeminarQuery query) {
        Assert.notNull(query, "查询参数不能为空");

        validateQueryParameter(query);

        return cmsSeminarMapper.selectCountByParam(query);
    }

    @Override
    public CmsSeminar selectFile(Long id){
        Assert.notNull(id, "id不能为空");
        return cmsSeminarMapper.selectFile(id);
    }

    private void validateQueryParameter(CmsSeminarQuery query) {
        query.validateBaseArgument();

        /*if (query.getId() == null
                && query.getGtId() == null) {
            throw new UnsupportedOperationException("请通过索引查询！");
        }*/
    }
}