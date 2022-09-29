package com.fudanuniversity.cms.business.vo.bulletin;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;


/**
 * 用户通知状态
 * <p>
 * Created by Xinyue.Tang at 2021-05-02
 */
@Data
@NoArgsConstructor
@ToString
public class CmsBulletinStateVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 消息总数
     */
    private Long totalCount;

    /**
     * 已读数量
     */
    private Long readCount;

    /**
     * 未读数量
     */
    private Long unreadCount;
}

