package com.fudanuniversity.cms.framework.exception;

import com.fudanuniversity.cms.commons.exception.ErrorCode;
import com.fudanuniversity.cms.commons.exception.PlatformException;
import org.springframework.http.HttpStatus;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Xinyue.Tang at 2021-04-23 13:07:13
 */
public final class WebExceptionHelper {
    /**
     * 有httpStatus状态码返回默认的错误Code
     */
    public static String getCode(HttpStatus httpStatus) {
        return getCode(httpStatus, null);
    }

    public static String getCode(HttpStatus httpStatus, @Nullable Throwable throwable) {
        boolean isPlatformEx = throwable instanceof PlatformException;
        if (isPlatformEx) {
            return ((PlatformException) throwable).getCode();
        }

        if (httpStatus.is4xxClientError()) {
            return ErrorCode.Http4xxCode;
        } else if (httpStatus.is5xxServerError()) {
            return ErrorCode.Http5xxCode;
        } else {
            return httpStatus.getReasonPhrase();
        }
    }

    /**
     * 有httpStatus状态码返回默认的错误信息
     */
    public static String getMessage(HttpStatus httpStatus) {
        return getMessage(httpStatus, null);
    }

    public static String getMessage(HttpStatus httpStatus, @Nullable Throwable throwable) {
        boolean isPlatformEx = throwable instanceof PlatformException;
        if (isPlatformEx) {
            return throwable.getMessage();
        }

        if (httpStatus.is4xxClientError()) {
            return ErrorCode.Http4xxMessage;
        } else if (httpStatus.is5xxServerError()) {
            return ErrorCode.Http5xxMessage;
        } else {
            return httpStatus.getReasonPhrase();
        }
    }

    /**
     * 由当前request和异常返回HttpStatus
     */
    public static HttpStatus getStatus(HttpServletRequest request, Throwable throwable) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (throwable instanceof PlatformException) {//平台异常理解为返回了正常的业务状态
            return HttpStatus.OK;
        }
        return getStatus(statusCode);
    }

    public static HttpStatus getStatus(Integer statusCode) {
        try {
            if (statusCode != null) {
                return HttpStatus.valueOf(statusCode);
            } else {
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }
        } catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    private WebExceptionHelper() {
    }
}
