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
public class CmsUserAccountResetPasswordVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "原密码不能为空")
    @Length(min = 6, message = "原密码长度不正确")
    private String oldPassword;

    @NotEmpty(message = "新密码不能为空")
    @Length(min = 6, message = "新密码长度不正确")
    private String newPassword;
}

