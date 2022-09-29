package com.fudanuniversity.cms.commons.exception;

/**
 * Created by Xinyue.Tang at 2021-04-19 13:10:27
 */
public class ParameterException extends PlatformException {

    private static final long serialVersionUID = -1L;

    public ParameterException(String message) {
        super(ErrorCode.IllegalParameterCode, message);
    }

    public ParameterException(String code, String message) {
        super(code, message);
    }
}
