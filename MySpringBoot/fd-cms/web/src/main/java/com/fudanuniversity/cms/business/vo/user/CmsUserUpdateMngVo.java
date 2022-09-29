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
public class CmsUserUpdateMngVo implements Serializable {//groupId已改

    private static final long serialVersionUID = 1L;

    @NotNull
    private Long id;

    private String stuId;

    private Integer type;

    private Integer addRole;

    private Integer deleteRole;

    private String name;

    private String telephone;

    @Email(message = "邮箱格式不正确")
    private String email;

    private String mentor;

    private String leader;

    private Integer keshuo;

    private Integer studyType;

    private Date enrollDate;

    private String papers;

    private String patents;

    private String services;

    private String projects;

    private Integer groupId;
}

