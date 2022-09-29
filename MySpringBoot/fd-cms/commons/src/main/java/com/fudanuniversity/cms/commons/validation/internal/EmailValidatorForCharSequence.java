package com.fudanuniversity.cms.commons.validation.internal;

import com.fudanuniversity.cms.commons.util.ValidationUtils;
import com.fudanuniversity.cms.commons.validation.constraints.Email;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Created by Xinyue.Tang at 2018-08-13 12:06:49
 */
public class EmailValidatorForCharSequence
        implements ConstraintValidator<Email, CharSequence> {

    private boolean allowEmpty;

    @Override
    public void initialize(Email annotation) {
        this.allowEmpty = annotation.allowEmpty();
    }

    @Override
    public boolean isValid(CharSequence field, ConstraintValidatorContext ctx) {
        if (field == null || (allowEmpty && "".equals(field.toString()))) {
            return true;
        }

        return ValidationUtils.isValidaEmailAddr(field.toString());
    }
}
