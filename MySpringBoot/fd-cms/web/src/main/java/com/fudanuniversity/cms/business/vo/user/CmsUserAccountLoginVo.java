package com.fudanuniversity.cms.business.vo.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * 用户帐户
 * <p>
 * Created by Xinyue.Tang at 2021-05-01
 */
@Data
@NoArgsConstructor

@ToString
public class CmsUserAccountLoginVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 学号
     */
    @NotEmpty(message = "学号不能为空")
    private String stuId;

    /**
     * 密码
     */
    @NotEmpty(message = "密码不能为空")
    @Length(min = 6, message = "密码长度不正确")
    private String password;
}

