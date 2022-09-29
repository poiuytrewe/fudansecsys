package com.fudanuniversity.cms.commons.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;

/**
 * This class adds back functionality that was removed in Guava v16.0.
 */
public class IOExUtils {

    private static final Logger logger = LoggerFactory.getLogger(IOExUtils.class);

    public static void closeQuietly(Closeable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (Exception e) {
            logger.error("[{}] close occur an unexpected exception.", closeable.getClass(), e);
        }
    }
}
