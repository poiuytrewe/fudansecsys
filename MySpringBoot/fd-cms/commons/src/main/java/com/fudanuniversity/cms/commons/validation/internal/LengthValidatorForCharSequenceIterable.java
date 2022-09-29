package com.fudanuniversity.cms.commons.validation.internal;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * Check that the character sequence length is between min and max.
 * <p>
 * Created by Xinyue.Tang at 2018-07-23 19:27:47
 */
public class LengthValidatorForCharSequenceIterable
        implements ConstraintValidator<Length, Iterable<? extends CharSequence>> {

    private static final Log log = LoggerFactory.make(MethodHandles.lookup());

    private int min;
    private int max;

    @Override
    public void initialize(Length parameters) {
        min = parameters.min();
        max = parameters.max();
        validateParameters();
    }

    @Override
    public boolean isValid(Iterable<? extends CharSequence> value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true;
        }

        for (CharSequence str : value) {
            int length = str.length();
            if (length < min || length > max) {
                return false;
            }
        }
        return true;
    }

    private void validateParameters() {
        if (min < 0) {
            throw log.getMinCannotBeNegativeException();
        }
        if (max < 0) {
            throw log.getMaxCannotBeNegativeException();
        }
        if (max < min) {
            throw log.getLengthCannotBeNegativeException();
        }
    }
}
