package com.fudanuniversity.cms.commons.util;

import com.fudanuniversity.cms.commons.exception.BusinessException;
import com.fudanuniversity.cms.commons.exception.ErrorCode;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 简单校验支持，断言失败抛出业务异常
 * <p>
 * Created by Xinyue.Tang at 2021-04-16 16:08:00
 */
public final class AssertUtils {

    public static void state(boolean expression) {
        if (!expression) {
            throw new BusinessException(ErrorCode.BusinessErrorMsg);
        }
    }

    public static void state(boolean expression, String message) {
        if (!expression) {
            throw new BusinessException(message);
        }
    }

    public static void state(boolean expression, Supplier<String> messageSupplier) {
        if (!expression) {
            throw new BusinessException(nullSafeGet(messageSupplier));
        }
    }

    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new BusinessException(message);
        }
    }

    public static void isTrue(boolean expression, Supplier<String> messageSupplier) {
        if (!expression) {
            throw new BusinessException(nullSafeGet(messageSupplier));
        }
    }

    public static void isNull(@Nullable Object object, String message) {
        if (object != null) {
            throw new BusinessException(message);
        }
    }

    public static void isNull(@Nullable Object object, Supplier<String> messageSupplier) {
        if (object != null) {
            throw new BusinessException(nullSafeGet(messageSupplier));
        }
    }

    public static void notNull(@Nullable Object object) {
        if (object == null) {
            throw new BusinessException(ErrorCode.BusinessErrorMsg);
        }
    }

    public static void notNull(@Nullable Object object, String message) {
        if (object == null) {
            throw new BusinessException(message);
        }
    }

    public static void notNull(@Nullable Object object, Supplier<String> messageSupplier) {
        if (object == null) {
            throw new BusinessException(nullSafeGet(messageSupplier));
        }
    }

    public static void hasLength(@Nullable String text, String message) {
        if (!StringUtils.isNotEmpty(text)) {
            throw new BusinessException(message);
        }
    }

    public static void hasLength(@Nullable String text, Supplier<String> messageSupplier) {
        if (!StringUtils.isNotEmpty(text)) {
            throw new BusinessException(nullSafeGet(messageSupplier));
        }
    }

    public static void hasText(@Nullable String text, String message) {
        if (!StringUtils.isNotBlank(text)) {
            throw new BusinessException(message);
        }
    }

    public static void hasText(@Nullable String text, Supplier<String> messageSupplier) {
        if (!StringUtils.isNotBlank(text)) {
            throw new BusinessException(nullSafeGet(messageSupplier));
        }
    }

    public static void notEmpty(@Nullable Object[] array, String message) {
        if (isEmpty(array)) {
            throw new BusinessException(message);
        }
    }

    public static void notEmpty(@Nullable Object[] array, Supplier<String> messageSupplier) {
        if (isEmpty(array)) {
            throw new BusinessException(nullSafeGet(messageSupplier));
        }
    }

    public static boolean isEmpty(@Nullable Object[] array) {
        return (array == null || array.length == 0);
    }

    public static void noNullElements(@Nullable Object[] array, String message) {
        if (array != null) {
            for (Object element : array) {
                if (element == null) {
                    throw new BusinessException(message);
                }
            }
        }
    }

    public static void noNullElements(@Nullable Object[] array, Supplier<String> messageSupplier) {
        if (array != null) {
            for (Object element : array) {
                if (element == null) {
                    throw new BusinessException(nullSafeGet(messageSupplier));
                }
            }
        }
    }

    public static void notEmpty(@Nullable Collection<?> collection) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BusinessException(ErrorCode.BusinessErrorMsg);
        }
    }

    public static void notEmpty(@Nullable Collection<?> collection, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BusinessException(message);
        }
    }

    public static void notEmpty(@Nullable Collection<?> collection, Supplier<String> messageSupplier) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BusinessException(nullSafeGet(messageSupplier));
        }
    }

    public static void noNullElements(@Nullable Collection<?> collection, String message) {
        if (collection != null) {
            for (Object element : collection) {
                if (element == null) {
                    throw new BusinessException(message);
                }
            }
        }
    }

    public static void noNullElements(@Nullable Collection<?> collection, Supplier<String> messageSupplier) {
        if (collection != null) {
            for (Object element : collection) {
                if (element == null) {
                    throw new BusinessException(nullSafeGet(messageSupplier));
                }
            }
        }
    }

    public static void notEmpty(@Nullable Map<?, ?> map, String message) {
        if (MapUtils.isEmpty(map)) {
            throw new BusinessException(message);
        }
    }

    public static void notEmpty(@Nullable Map<?, ?> map, Supplier<String> messageSupplier) {
        if (MapUtils.isEmpty(map)) {
            throw new BusinessException(nullSafeGet(messageSupplier));
        }
    }

    @Nullable
    private static String nullSafeGet(@Nullable Supplier<String> messageSupplier) {
        return (messageSupplier != null ? messageSupplier.get() : null);
    }

    private AssertUtils() {
    }
}
