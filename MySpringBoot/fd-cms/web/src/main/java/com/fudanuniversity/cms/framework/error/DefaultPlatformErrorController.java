package com.fudanuniversity.cms.framework.error;

import com.fudanuniversity.cms.commons.model.JsonResult;
import com.fudanuniversity.cms.commons.util.BeanExUtils;
import com.fudanuniversity.cms.framework.exception.WebExceptionHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 全局异常处理，未经处理的错误（如404）会转至ErrorController
 *
 * @see ErrorAttributes
 * @see org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController
 * Created by Xinyue.Tang at 2020-07-10 10:19:04
 */
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class DefaultPlatformErrorController implements ErrorController, PlatformErrorController {

    private final static Logger logger = LoggerFactory.getLogger(DefaultPlatformErrorController.class);

    @Resource
    private ErrorAttributes errorAttributes;

    @Resource
    private ServerProperties serverProperties;

    @Override
    public String getErrorPath() {
        return this.serverProperties.getError().getPath();
    }

    @RequestMapping
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        WebRequest webRequest = new ServletWebRequest(request);
        Throwable throwable = errorAttributes.getError(webRequest);
        HttpStatus status = WebExceptionHelper.getStatus(request, throwable);
        String code = WebExceptionHelper.getCode(status, throwable);
        String message = WebExceptionHelper.getMessage(status, throwable);
        Object model = JsonResult.buildFail(code, message);
        Map<String, Object> body = BeanExUtils.introspect(model);
        logger.error("Application occurred error, status: {}, code: {}, message: {}", status, code, message, throwable);
        return new ResponseEntity<>(body, status);
    }
}
