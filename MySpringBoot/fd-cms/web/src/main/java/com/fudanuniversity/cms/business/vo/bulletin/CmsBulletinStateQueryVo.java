package com.fudanuniversity.cms.business.vo.bulletin;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * 用户通知状态
 * <p>
 * Created by Xinyue.Tang at 2021-05-02
 */
@Data
@NoArgsConstructor
@ToString
public class CmsBulletinStateQueryVo {

    private static final long serialVersionUID = 1L;

    /**
     * 通知id
     */
    private Long id;

    private String stuId;
}