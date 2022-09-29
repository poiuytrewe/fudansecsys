package com.fudanuniversity.cms.commons.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Xinyue.Tang at 2020-07-21 10:53:13
 */
@Target({METHOD})
@Retention(RUNTIME)
@Documented
public @interface ValidGroup {

    /**
     * Specify one or more validation groups to apply to the validation step
     * kicked off by this annotation.
     * <p>JSR-303 defines validation groups as custom annotations which an application declares
     * for the sole purpose of using them as type-safe group arguments
     */
    Class<?>[] value() default {};

    /**
     * 执行校验时是否包括Default组
     */
    boolean includeDefault() default true;
}
