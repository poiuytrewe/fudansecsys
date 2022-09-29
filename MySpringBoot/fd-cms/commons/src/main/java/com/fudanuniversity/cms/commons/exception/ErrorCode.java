package com.fudanuniversity.cms.commons.exception;

/**
 * Created by Xinyue.Tang at 2021-04-14 15:39:54
 */
public final class ErrorCode {
    public final static String DefaultErrorCode = "error.default";
    public final static String DefaultErrorMsg = "系统开了会小差，请稍后重试";

    public final static String Http4xxCode = "http.4xx";
    public final static String Http4xxMessage = "操作请求出错了，请刷新重试";

    public final static String Http5xxCode = "http.5xx";
    public final static String Http5xxMessage = DefaultErrorMsg;

    public final static String LoginErrorCode = "error.login";

    public final static String BusinessErrorCode = "error.business";
    public final static String BusinessErrorMsg = "业务处理出错了，请稍后重试";

    public final static String IllegalParameterCode = "error.parameter";
    public final static String IllegalParameterMessage = "参数出错了";
}
