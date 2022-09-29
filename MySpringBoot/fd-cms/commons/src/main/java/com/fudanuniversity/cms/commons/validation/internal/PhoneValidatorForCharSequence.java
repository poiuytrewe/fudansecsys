package com.fudanuniversity.cms.commons.validation.internal;

import com.fudanuniversity.cms.commons.util.ValidationUtils;
import com.fudanuniversity.cms.commons.validation.constraints.Phone;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Created by Xinyue.Tang at 2018-08-13 12:06:49
 */
public class PhoneValidatorForCharSequence
        implements ConstraintValidator<Phone, CharSequence> {

    private boolean allowEmpty;

    @Override
    public void initialize(Phone annotation) {
        this.allowEmpty = annotation.allowEmpty();
    }

    @Override
    public boolean isValid(CharSequence field, ConstraintValidatorContext ctx) {
        if (field == null || (allowEmpty && "".equals(field.toString()))) {
            return true;
        }

        return ValidationUtils.isValidPhoneNo(field.toString());
    }
}
