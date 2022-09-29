package com.fudanuniversity.cms.commons.validation.internal;

import com.fudanuniversity.cms.commons.validation.constraints.Numeric;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

/**
 * Created by Xinyue.Tang at 2018-12-27 17:11:33
 */
public class NumericValidatorForBigDecimal implements ConstraintValidator<Numeric, BigDecimal> {

    private BigDecimal min;
    private BigDecimal max;
    private int scale;

    @Override
    public void initialize(Numeric constraintAnnotation) {
        this.min = convert(constraintAnnotation.min());
        this.max = convert(constraintAnnotation.max());
        this.scale = constraintAnnotation.scale();
    }

    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        if (min != null) {
            if (value.compareTo(min) < 0) {
                return false;
            }
        }

        if (max != null) {
            if (value.compareTo(max) > 0) {
                return false;
            }
        }

        if (scale > -1 && value.scale() > scale) {
            return false;
        }

        return true;
    }

    private BigDecimal convert(String number) {
        if (number == null || number.length() == 0) {
            return null;
        }
        return new BigDecimal(number);
    }
}
