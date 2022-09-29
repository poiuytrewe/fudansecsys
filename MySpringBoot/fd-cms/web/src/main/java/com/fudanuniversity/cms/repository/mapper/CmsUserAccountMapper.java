package com.fudanuniversity.cms.repository.mapper;

import com.fudanuniversity.cms.repository.entity.CmsUserAccount;
import com.fudanuniversity.cms.repository.query.CmsUserAccountQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * CmsUserAccountMapper接口
 * <p>
 * Created by Xinyue.Tang at 2021-05-01
 */
@Mapper
public interface CmsUserAccountMapper {

    /**
     * 保存处理
     */
    int insert(CmsUserAccount cmsUserAccount);

    int replace(CmsUserAccount cmsUserAccount);

    /**
     * 批量upsert
     */
    int bulkUpsert(List<CmsUserAccount> cmsUserAccountList);

    /**
     * 删除处理
     */
    int deleteByStuId(String stuId);

    /**
     * 更新处理
     */
    int updateById(CmsUserAccount cmsUserAccount);

    /**
     * 根据条件查询信息列表
     */
    List<CmsUserAccount> selectListByParam(CmsUserAccountQuery query);

    /**
     * 根据条件查询信息总数目
     */
    Long selectCountByParam(CmsUserAccountQuery query);
}
