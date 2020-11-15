package com.binlee.design.adapter;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/11/15
 */
public interface IHttpClient {

    void request(String url, Runnable callback);
}
