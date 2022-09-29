package com.fudanuniversity.cms.repository.mapper;

import com.fudanuniversity.cms.repository.entity.CmsBulletin;
import com.fudanuniversity.cms.repository.query.CmsBulletinQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * CmsBulletinMapper接口
 * <p>
 * Created by Xinyue.Tang at 2021-05-02
 */
@Mapper
public interface CmsBulletinMapper {

    /**
     * 保存处理
     */
    int insert(CmsBulletin cmsBulletin);

    /**
     * 批量upsert
     */
    int bulkUpsert(List<CmsBulletin> cmsBulletinList);

    /**
     * 删除处理
     */
    int deleteById(Long id);

    /**
     * 更新处理
     */
    int updateById(CmsBulletin cmsBulletin);

    /**
     * 根据条件查询信息列表
     */
    List<Long> selectUnreadBulletinIdList(CmsBulletinQuery query);

    /**
     * 根据条件查询信息列表
     */
    List<CmsBulletin> selectListByParam(CmsBulletinQuery query);

    /**
     * 根据条件查询信息总数目
     */
    Long selectCountByParam(CmsBulletinQuery query);

    void readAllBulletin(CmsBulletin bulletin);

}
