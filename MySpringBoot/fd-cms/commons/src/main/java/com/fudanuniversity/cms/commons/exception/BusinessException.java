package com.fudanuniversity.cms.commons.exception;

/**
 * Created by Xinyue.Tang at 2021-04-15 15:19:51
 */
public class BusinessException extends PlatformException {

    private static final long serialVersionUID = 1L;

    public BusinessException(String message) {
        super(null, message);
    }

    public BusinessException(String code, String message) {
        super(code, message);
    }
}
