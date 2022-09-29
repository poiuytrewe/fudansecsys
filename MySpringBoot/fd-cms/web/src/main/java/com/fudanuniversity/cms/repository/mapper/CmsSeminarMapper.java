package com.fudanuniversity.cms.repository.mapper;

import com.fudanuniversity.cms.repository.entity.CmsSeminar;
import com.fudanuniversity.cms.repository.query.CmsSeminarQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * CmsSeminarMapper接口
 * <p>
 * Created by Xinyue.Tang at 2021-05-02
 */
@Mapper
public interface CmsSeminarMapper {

    /**
     * 保存处理
     */
    int insert(CmsSeminar cmsSeminar);

    /**
     * 批量upsert
     */
    int bulkUpsert(List<CmsSeminar> cmsSeminarList);

    /**
     * 删除处理
     */
    int deleteById(Long id);

    /**
     * 更新处理
     */
    int updateById(CmsSeminar cmsSeminar);

    /**
     * 根据条件查询信息列表
     */
    List<CmsSeminar> selectListByParam(CmsSeminarQuery query);

    /**
     * 根据条件查询信息总数目
     */
    Long selectCountByParam(CmsSeminarQuery query);

    CmsSeminar selectFile(Long id);

}
