package com.fudanuniversity.cms.commons.validation.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Xinyue.Tang at 2018-07-23 19:27:47
 */
@Documented
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Repeatable(Numeric.List.class)
public @interface Numeric {

    /**
     * 最小值（默认：0）
     */
    String min() default "0";

    /**
     * 最大值（默认：Long.MAX_VALUE）
     */
    String max() default "9223372036854775807";

    /**
     * 小数部分位数最大限制（该值小于0表示对数字小数无限制）
     */
    int scale() default -1;

    String message() default "{com.fudanuniversity.cms.commons.validation.constraints.Numeric.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * Defines several annotation constraints on the same element.
     */
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
    @Retention(RUNTIME)
    @Documented
    @interface List {

        Numeric[] value();
    }
}
