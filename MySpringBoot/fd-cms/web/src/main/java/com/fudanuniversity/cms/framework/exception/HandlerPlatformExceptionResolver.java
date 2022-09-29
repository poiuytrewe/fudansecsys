package com.fudanuniversity.cms.framework.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fudanuniversity.cms.commons.exception.ErrorCode;
import com.fudanuniversity.cms.commons.exception.PlatformException;
import com.fudanuniversity.cms.commons.model.JsonResult;
import com.fudanuniversity.cms.commons.util.BeanExUtils;
import com.fudanuniversity.cms.commons.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import static javax.servlet.RequestDispatcher.ERROR_STATUS_CODE;

/**
 * DefaultHandlerExceptionResolver优先级为Ordered.LOWEST_PRECEDENCE，是最后一个异常处理，这里将spring自带的异常处理类替换掉
 * <p>
 * Created by Xinyue.Tang at 2021-04-16 13:33:36
 */
public class HandlerPlatformExceptionResolver extends DefaultHandlerExceptionResolver {

    private final static Logger logger = LoggerFactory.getLogger(HandlerPlatformExceptionResolver.class);

    private final ObjectMapper objectMapper = JsonUtils.createGeneralObjectMapper();

    @Override
    protected ModelAndView doResolveException(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        HttpStatus httpStatus;
        String code = null;
        String message;

        boolean isPlatformEx = ex instanceof PlatformException;
        NonSendErrorHttpServletResponse wrapper = new NonSendErrorHttpServletResponse(response);
        ModelAndView modelAndView = super.doResolveException(request, wrapper, handler, ex);
        if (modelAndView != null) {
            httpStatus = WebExceptionHelper.getStatus(wrapper.getStatusCode());
            modelAndView.setStatus(httpStatus);
            request.setAttribute(ERROR_STATUS_CODE, httpStatus.value());
            JsonResult<Object> errorInfo = JsonResult.buildFail(
                    WebExceptionHelper.getCode(httpStatus, null), WebExceptionHelper.getMessage(httpStatus));
            modelAndView.addAllObjects(BeanExUtils.introspect(errorInfo));
        } else {
            httpStatus = WebExceptionHelper.getStatus(request, ex);
            code = isPlatformEx ? ((PlatformException) ex).getCode() : ErrorCode.DefaultErrorCode;
            message = isPlatformEx ? ex.getMessage() : WebExceptionHelper.getMessage(httpStatus);
            Object model = JsonResult.buildFail(code, message);
            request.setAttribute(ERROR_STATUS_CODE, httpStatus.value());
            modelAndView = new ModelAndView();
            modelAndView.setStatus(httpStatus);
            modelAndView.addAllObjects(BeanExUtils.introspect(model));
        }

        modelAndView.setView(new MappingJackson2JsonView(objectMapper));

        if (isPlatformEx) {//平台定义的异常记录WARN日志
            logger.warn("Application occurred platform exception, code: {}, message: {}", code, ex.getMessage(), ex);
        } else {//其他意外错误记录ERROR日志，出现了该日志意味着需要优化代码设计
            logger.error("Application occurred unexpected exception, message: {}", ex.getMessage(), ex);
        }

        return modelAndView;
    }


    /**
     * disable sendError operation（不要执行sendError方法）
     */
    @SuppressWarnings("unused")
    private static class NonSendErrorHttpServletResponse extends HttpServletResponseWrapper {

        private int statusCode;
        private String message;

        public NonSendErrorHttpServletResponse(HttpServletResponse response) {
            super(response);
        }

        @Override
        public void sendError(int sc) {
            this.statusCode = sc;
            //super.sendError(sc);  //do not invoke this method
        }

        @Override
        public void sendError(int sc, String msg) {
            this.statusCode = sc;
            //super.sendError(sc, msg); //do not invoke this method
        }

        public int getStatusCode() {
            return statusCode;
        }

        public String getMessage() {
            return message;
        }
    }
}
