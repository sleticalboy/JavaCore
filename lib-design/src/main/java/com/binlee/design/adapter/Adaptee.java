package com.binlee.design.adapter;

import com.binlee.util.Logger;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/11/15
 */
public class Adaptee {

    private static final Logger L = Logger.get(Adaptee.class);

    protected void realRequest(String url, Runnable callback) {
        L.d("realRequest() called with: url = [" + url + "], callback = [" + callback + "]");
    }
}
