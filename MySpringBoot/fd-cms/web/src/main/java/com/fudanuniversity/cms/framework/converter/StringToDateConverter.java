package com.fudanuniversity.cms.framework.converter;

import com.fudanuniversity.cms.commons.util.DateExUtils;
import org.springframework.core.convert.converter.Converter;

import javax.annotation.Nullable;
import java.util.Date;

/**
 * <p>
 * 日期转换器(处理Controller方法自定义的接收参数对象Date类型Field)
 * <p/>
 * 根据日期字符串长度判断是长日期还是短日期。日期时间格式自定义扩展
 *
 * @see <a href="http://docs.spring.io/spring/docs/current/spring-framework-reference/html/validation.html">9.5.3 GenericConverter</a>
 * <p>
 * Created by Xinyue.Tang at 2020-09-04 17:55:44
 */
public class StringToDateConverter implements Converter<String, Date> {

    public final static StringToDateConverter INSTANCE = new StringToDateConverter();

    @Override
    public Date convert(@Nullable String source) {
        return DateExUtils.parseDynamicFormat(source);
    }
}
