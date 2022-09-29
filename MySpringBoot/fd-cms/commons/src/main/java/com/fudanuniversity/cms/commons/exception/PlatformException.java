package com.fudanuniversity.cms.commons.exception;

import java.util.ArrayList;
import java.util.List;

import static com.fudanuniversity.cms.commons.exception.ErrorCode.DefaultErrorCode;

/**
 * 平台相关的异常，平台相关的业务或功能抛出的异常继承此类
 * <p>
 * Created by Xinyue.Tang at 2021-04-14 15:23:20
 */
public class PlatformException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 业务异常编码
     */
    private final String code;

    public PlatformException() {
        super();
        this.code = DefaultErrorCode;
    }

    public PlatformException(Throwable cause) {
        super(cause);
        this.code = DefaultErrorCode;
    }

    public PlatformException(String code, String message) {
        super(message);
        this.code = code;
    }

    public PlatformException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    /**
     * 堆栈信息过多不利于业务问题排查，这里
     */
    public boolean isEssentialStackTrace(StackTraceElement stackTraceElement) {
        return stackTraceElement.getClassName().startsWith("com.fudanuniversity.");
    }

    /**
     * 业务异常类型精简stack trace，方便追踪错误信息
     *
     * @see <a href='https://www.zhihu.com/question/21405047'>重载Throwable.fillInStackTrace方法已提高Java性能这样的做法对法？</a>
     */
    @Override
    public synchronized Throwable fillInStackTrace() {
        super.fillInStackTrace();

        StackTraceElement[] stackTrace = super.getStackTrace();

        //异常过滤，只打印关键的信息 我们需要堆栈信息定位异常
        List<StackTraceElement> newStackTrace = new ArrayList<>(stackTrace.length);
        for (StackTraceElement stackTraceElement : stackTrace) {
            if (isEssentialStackTrace(stackTraceElement)) {
                newStackTrace.add(stackTraceElement);
            }
        }
        this.setStackTrace(newStackTrace.toArray(new StackTraceElement[0]));

        return this;
    }

}
