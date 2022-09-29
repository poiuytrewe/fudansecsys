package com.fudanuniversity.cms.repository.dao.impl;

import com.fudanuniversity.cms.repository.dao.CmsRecorderDao;
import com.fudanuniversity.cms.repository.mapper.CmsRecorderMapper;
import com.fudanuniversity.cms.repository.entity.CmsRecorder;
import com.fudanuniversity.cms.repository.query.CmsRecorderQuery;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * CmsRecorderDao 实现类
 * <p>
 * Created by Xinyue.Tang at 2021-05-02
 */
@Repository
public class CmsRecorderDaoImpl implements CmsRecorderDao {

    @Resource
    private CmsRecorderMapper cmsRecorderMapper;

    @Override
    public int insert(CmsRecorder cmsRecorder) {
        Assert.notNull(cmsRecorder, "保存对象不能为空");


        return cmsRecorderMapper.insert(cmsRecorder);
    }

    @Override
    public int bulkUpsert(List<CmsRecorder> cmsRecorderList){
        Assert.notEmpty(cmsRecorderList, "保存对象列表不能为空");

        for(CmsRecorder cmsRecorder : cmsRecorderList){

        }

        return cmsRecorderMapper.bulkUpsert(cmsRecorderList);
    }


    @Override
    public int updateById(CmsRecorder cmsRecorder) {
        Assert.notNull(cmsRecorder, "更新对象不能为空");
        Assert.notNull(cmsRecorder.getId(), "更新对象id不能为空");
        Assert.notNull(cmsRecorder.getModifyTime(), "更新时间不能为空");

        return cmsRecorderMapper.updateById(cmsRecorder);
    }

    @Override
    public int deleteById(Long id) {
        Assert.notNull(id, "删除记录id不能为空");

        return cmsRecorderMapper.deleteById(id);
    }

    @Override
    public List<CmsRecorder> selectDetailListByParam(CmsRecorderQuery query) {
        Assert.notNull(query, "查询参数不能为空");

        validateQueryParameter(query);

        return cmsRecorderMapper.selectDetailListByParam(query);
    }

    @Override
    public List<CmsRecorder> selectInfoListByParam(CmsRecorderQuery query) {
        Assert.notNull(query, "查询参数不能为空");

        validateQueryParameter(query);

        return cmsRecorderMapper.selectInfoListByParam(query);
    }

    @Override
    public List<CmsRecorder> selectRecorderFile(CmsRecorderQuery query){
        Assert.notNull(query, "查询参数不能为空");
        validateQueryParameter(query);
        return cmsRecorderMapper.selectRecorderFile(query);
    }
    @Override
    public List<CmsRecorder> selectSummarizerFile(CmsRecorderQuery query){
        Assert.notNull(query, "查询参数不能为空");
        validateQueryParameter(query);
        return cmsRecorderMapper.selectSummarizerFile(query);
    }

    @Override
    public Long selectCountByParam(CmsRecorderQuery query) {
        Assert.notNull(query, "查询参数不能为空");

        validateQueryParameter(query);

        return cmsRecorderMapper.selectCountByParam(query);
    }

    private void validateQueryParameter(CmsRecorderQuery query) {
        query.validateBaseArgument();

        /*if (query.getId() == null
                && query.getGtId() == null
               && query.getDate() == null) {
            throw new UnsupportedOperationException("请通过索引查询！");
        }*/
    }
}