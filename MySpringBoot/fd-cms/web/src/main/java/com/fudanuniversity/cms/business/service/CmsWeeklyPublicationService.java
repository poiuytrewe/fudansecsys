package com.fudanuniversity.cms.business.service;

import com.fudanuniversity.cms.business.vo.weeklyPublication.CmsWeeklyPublicationForBossVo;
import com.fudanuniversity.cms.business.vo.weeklyPublication.CmsWeeklyPublicationManagerVo;
import com.fudanuniversity.cms.business.vo.weeklyPublication.CmsWeeklyPublicationUploadVo;
import com.fudanuniversity.cms.business.vo.weeklyPublication.CmsWeeklyPublicationVo;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.model.paging.WeeklyPublicationPaging;
import com.fudanuniversity.cms.repository.entity.CmsUser;

import java.util.List;

public interface CmsWeeklyPublicationService {
    PagingResult<CmsWeeklyPublicationVo> queryPagingResult(String stuId, Paging paging);

    void uploadPublication(CmsWeeklyPublicationUploadVo uploadVo);

    void deletePublication(Long id);

    /**以上是学生的**/

    PagingResult<CmsWeeklyPublicationVo> queryTeacherPagingResult(List<CmsUser> userList, Paging paging, String week);

    WeeklyPublicationPaging<CmsUser> mentorStatistics(List<CmsUser> userList, String week);

    void updateById(CmsWeeklyPublicationVo publicationVo);

    PagingResult<CmsWeeklyPublicationForBossVo> queryBossPaging(Paging paging, String week);

    PagingResult<CmsWeeklyPublicationManagerVo> queryManagerPaging(Paging paging, String week);

}
