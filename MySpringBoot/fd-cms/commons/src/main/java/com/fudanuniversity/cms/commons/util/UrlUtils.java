package com.fudanuniversity.cms.commons.util;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Xinyue.Tang at 2020-05-15 18:39:42
 */
public final class UrlUtils {

    public static String encode(String url) {
        if (url == null) {
            return null;
        }
        try {
            return URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    public static String decode(String content) {
        if (content == null) {
            return null;
        }
        try {
            return URLDecoder.decode(content, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Parse a URI String into Name-Value Collection
     * (https://stackoverflow.com/questions/13592236/parse-a-uri-string-into-name-value-collection)
     * <pre>
     *     https://stackoverflow.com/?param1=value1&param2=&param3=value3&param3=%E4%B8%AD%E6%96%87&param3
     *     -->
     *     {param1=[value1], param2=[null], param3=[value3, 中文, null]}
     * </pre>
     */
    public static Map<String, List<String>> parseParameters(String urlStr) {
        URL url;
        try {
            url = new URL(urlStr);
        } catch (MalformedURLException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex.getCause());
        }
        return parseParameters(url);
    }

    public static Map<String, List<String>> parseParameters(URL url) {
        if (StringUtils.isEmpty(url.getQuery())) {
            return Collections.emptyMap();
        }
        return Arrays.stream(url.getQuery().split("&"))
                .map(UrlUtils::splitQueryParameter)
                .collect(Collectors.groupingBy(SimpleImmutableEntry::getKey, LinkedHashMap::new,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())));
    }

    public static SimpleImmutableEntry<String, String> splitQueryParameter(String it) {
        final int idx = it.indexOf("=");
        final String key = idx > 0 ? it.substring(0, idx) : it;
        final String value = idx > 0 && it.length() > idx + 1 ? it.substring(idx + 1) : null;
        return new SimpleImmutableEntry<>(decode(key), decode(value));
    }
}
