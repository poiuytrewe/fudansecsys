package com.fudanuniversity.cms.commons.validation.internal;

import com.fudanuniversity.cms.commons.validation.constraints.EnumValue;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;

/**
 * Created by Xinyue.Tang at 2018-08-13 12:06:49
 */
public class EnumValueValidatorForObject
        implements ConstraintValidator<EnumValue, Object> {

    private boolean allowEmptyString;
    private Set<Object> enumValues;

    @SuppressWarnings("Duplicates")
    @Override
    public void initialize(EnumValue annotation) {
        this.allowEmptyString = annotation.allowEmptyString();

        String property = annotation.property();
        String method = annotation.method();

        if ((property.length() == 0 && method.length() == 0)
                || (property.length() > 0 && method.length() > 0)) {
            throw new IllegalStateException("Error configuration of "
                    + annotation + ": select one from 'property' or 'method'");
        }

        Class<? extends Enum<?>> enumClass = annotation.enumClass();
        if (property.length() > 0) {
            enumValues = Util.getEnumPropertyValues(enumClass, property);
        } else {
            enumValues = Util.getEnumMethodValues(enumClass, method);
        }

    }

    @Override
    public boolean isValid(Object field, ConstraintValidatorContext ctx) {
        if (field == null || (allowEmptyString && "".equals(field))) {
            return true;
        }

        return enumValues.contains(field);
    }
}
