package com.fudanuniversity.cms.commons.enums;

import com.fudanuniversity.cms.commons.validation.constraints.In;

import java.util.Objects;


/**
 * Created by Xinyue.Tang at 2021-05-01 18:11:32
 */
public class UserRoleEnum {

    public static boolean hasManageRole(Integer isTalkMng, Integer isLabMng, Integer isPaperMng, Integer isEducateMng, Integer isDeviceMng) {
        return Objects.equals(isTalkMng,1)
                || Objects.equals(isLabMng,1)
                || Objects.equals(isPaperMng,1)
                || Objects.equals(isEducateMng,1)
                || Objects.equals(isDeviceMng,1);
    }//检查用户是否拥有任一管理员权限
}
