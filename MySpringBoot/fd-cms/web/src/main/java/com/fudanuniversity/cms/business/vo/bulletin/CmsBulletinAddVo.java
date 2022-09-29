package com.fudanuniversity.cms.business.vo.bulletin;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;


/**
 * 通知
 * <p>
 * Created by Xinyue.Tang at 2021-05-02
 */
@Data
@NoArgsConstructor
@ToString
public class CmsBulletinAddVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    @NotEmpty(message = "标题不能为空")
    private String title;

    private String stuId;

    /**
     * 内容
     */
    @NotEmpty(message = "内容不能为空")
    private String content;
}

