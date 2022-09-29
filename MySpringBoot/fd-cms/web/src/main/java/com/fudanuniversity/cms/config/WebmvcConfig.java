package com.fudanuniversity.cms.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fudanuniversity.cms.commons.util.JsonUtils;
import com.fudanuniversity.cms.framework.converter.StringToDateConverter;
import com.fudanuniversity.cms.framework.exception.HandlerPlatformExceptionResolver;
import com.fudanuniversity.cms.framework.interceptor.LoginInterceptor;
import com.fudanuniversity.cms.framework.validation.NoOpSmartValidator;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import java.util.List;

/**
 * Created by Xinyue.Tang at 2021-04-16 10:16:47
 */
@Configuration
public class WebmvcConfig implements WebMvcConfigurer {

    @Override
    public Validator getValidator() {
        return new NoOpSmartValidator();
    }

    /**
     * 转换Bean参数
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(StringToDateConverter.INSTANCE);
    }

    /**
     * 拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                //默认所有请求都需要登录，如果有需要请调整
                .addPathPatterns("/**")
                //排除登录的地址
                .excludePathPatterns(
                        "/",
                        "/docs/**",  //API
                        "/error",   //默认错误路径
                        "/u/user/login",  //用户登录入口不要拦截
                        "/static"   //静态资源，如果有请约定 /static 前缀访问
                );
    }

    /**
     * 统一异常处理
     */
    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        //DefaultHandlerExceptionResolver优先级为Ordered.LOWEST_PRECEDENCE，是最后一个异常处理，这里将该异常处理替换掉
        exceptionResolvers.removeIf(
                exceptionResolver -> exceptionResolver instanceof DefaultHandlerExceptionResolver);
        exceptionResolvers.add(new HandlerPlatformExceptionResolver());
    }

    /*@Override
    public void addCorsMappings(CorsRegistry registry) {
        String[] allowedHeaders = new String[]{
                "Content-Type",
                "Access-Control-Allow-Origin",
                "Access-Control-Allow-Headers",
                "Access-Control-Max-Age",
                "Access-Control-Allow-Headers",
                "Authorization", "Accept-Language",
                "Accept-ApiKey", "Accept-ApiSign", "Accept-ApiTime",
                "X-Requested-With", "X-Client-Ip"
        };

        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD")
                .allowedHeaders(allowedHeaders)
                .allowCredentials(true)
                .maxAge(31536000);
    }*/

    /**
     * HttpMessageConverter注册
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        //替换默认Jackson2配置
        converters.removeIf(converter -> converter instanceof MappingJackson2HttpMessageConverter);
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = JsonUtils.createGeneralObjectMapper();
        jsonConverter.setObjectMapper(objectMapper);
        converters.add(0, jsonConverter);
    }
}
