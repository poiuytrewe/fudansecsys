package com.fudanuniversity.cms.business.vo.user;

import com.fudanuniversity.cms.commons.enums.BooleanEnum;
import com.fudanuniversity.cms.commons.enums.StudyTypeEnum;
import com.fudanuniversity.cms.commons.enums.UserRoleEnum;
import com.fudanuniversity.cms.commons.enums.UserTypeEnum;
import com.fudanuniversity.cms.commons.validation.constraints.EnumValue;
import com.fudanuniversity.cms.commons.validation.constraints.XSS;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户
 * <p>
 * Created by Xinyue.Tang at 2021-05-01
 */
@Data
@NoArgsConstructor
@ToString
public class CmsUserAddVo implements Serializable {//groupId已改

    private static final long serialVersionUID = 1L;

    /**
     * 用户类型
     */
    @EnumValue(enumClass = UserTypeEnum.class, property = "code", message = "用户类型参数格式错误")
    private Integer type;

    /**
     * 学号
     */
    @NotEmpty(message = "学号/工号不能为空")
    private String stuId;

    /**
     * 用户名
     */
    @NotEmpty
    private String name;

    private String mentor;

    private Integer keshuo;


    @NotNull
    private Integer enrollYear;
}

