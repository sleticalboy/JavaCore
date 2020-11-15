package com.binlee.design.adapter;

import com.binlee.util.Logger;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/11/15
 */
public final class Adapter extends Adaptee implements IHttpClient {

    private static final Logger L = Logger.get(Adapter.class);

    @Override
    public void request(String url, Runnable callback) {
        L.d("request() called with: url = [" + url + "], callback = [" + callback + "]");
        // 实际的业务逻辑交给 Adaptee 去实现
        realRequest(url, callback);
    }

    public static void exec() {
        final Adapter adapter = new Adapter();
        adapter.request("https://www.github.com/sleticalboy/", new Runnable() {
            @Override
            public void run() {
                // do callback stuff
            }
        });
    }
}
