package com.auto.util;

import org.springframework.web.util.UriUtils;

import java.nio.charset.Charset;

public class URLUtils extends UriUtils {
    public URLUtils() {
    }

    public static String encodeURL(String source, Charset charset) {
        return encode(source, charset.name());
    }
}