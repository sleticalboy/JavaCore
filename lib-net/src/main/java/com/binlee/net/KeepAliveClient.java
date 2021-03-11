package com.binlee.net;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2021/3/11
 */
public final class KeepAliveClient {

    public static void main(String[] args) {
        new KeepAliveSocket("localhost", 9097);
    }
}
