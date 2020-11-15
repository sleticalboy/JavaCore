package com.binlee.design.builder;

import com.binlee.util.Logger;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/11/15
 */
public final class Builders {

    private static final Logger LOGGER = Logger.get(Builders.class);

    public static void exec() {
        final HttpUrl httpUrl = new HttpUrl.Builder()
                .schema("https")
                .host("www.github.com")
                .port(443)
                .path("/sleticalboy/JavaCore")
                .build();
        LOGGER.i(httpUrl.toString());
    }
}
