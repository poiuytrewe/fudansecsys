package com.fudanuniversity.cms.config;

import com.fudanuniversity.cms.framework.validation.ControllerValidationPostProcessor;
import com.fudanuniversity.cms.framework.validation.LocaleContextMessageInterpolator;
import jakarta.validation.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置web组件等
 * <p>
 * Created by Xinyue.Tang at 2021-04-19 14:06:43
 */
@Configuration
public class WebComponentConfig {

    @Bean
    public Validator validator() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        ValidatorContext validatorContext = validatorFactory.usingContext();
        MessageInterpolator targetInterpolator = validatorFactory.getMessageInterpolator();
        LocaleContextMessageInterpolator localeContextMessageInterpolator = new LocaleContextMessageInterpolator(targetInterpolator);
        validatorContext.messageInterpolator(localeContextMessageInterpolator);
        return validatorContext.getValidator();
    }

    /**
     * 为Controller补充方法执行时校验
     */
    @Bean
    public ControllerValidationPostProcessor controllerValidationPostProcessor(Validator validator) {
        return new ControllerValidationPostProcessor(validator);
    }

}
