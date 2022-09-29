package com.fudanuniversity.cms.business.service;

import com.fudanuniversity.cms.business.vo.bulletin.*;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;

/**
 * CmsBulletinService
 * <p>
 * Created by Xinyue.Tang at 2021-05-02
 */
public interface CmsBulletinService {

    /**
     * 保存处理
     */
    void addNewBulletin(CmsBulletinAddVo addVo); //增

    /**
     * 根据id更新处理
     */
    void editBulletin(Long id); //改

    /**
     * 根据id删除处理
     */
    void deleteCmsBulletinById(Long bulletinId); //删

    /**
     * 读取消息
     */

    /**
     * 读取所有消息
     */
    void readAllBulletin(String stuId); //全部已读

    /**
     * 分页查询数据列表
     */
    PagingResult<CmsBulletinVo> queryPagingResult(CmsBulletinQueryVo queryVo, Paging paging);//查


    Long queryCmsBulletinReadState(CmsBulletinStateQueryVo stateQueryVo); //查询未读消息数量
}