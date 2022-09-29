package com.fudanuniversity.cms.commons.validation.internal;

import com.fudanuniversity.cms.commons.validation.constraints.XSS;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

/**
 * Validate that the string does not contain malicious code.
 * <p>
 * It uses <a href="http://www.jsoup.org">JSoup</a> as the underlying parser/sanitizer library.
 * <p>
 * //@see org.hibernate.validator.internal.constraintvalidators.SafeHtmlValidator
 * <p>
 * Created by Xinyue.Tang at 2018-07-23 19:27:47
 */
public class XSSValidatorForCharSequence
        implements ConstraintValidator<XSS, CharSequence> {

    private Whitelist whitelist;

    public void initialize(XSS constraintAnn) {
        this.whitelist = XSSDescriber.mappedWhitelist(constraintAnn);
    }

    public boolean isValid(CharSequence field, ConstraintValidatorContext context) {
        if (field == null) {
            return true;
        }

        if (whitelist == null) {
            return !XSSDescriber.hasEscapeChar(field.toString());
        }

        return Jsoup.isValid(field.toString(), whitelist);
    }
}
