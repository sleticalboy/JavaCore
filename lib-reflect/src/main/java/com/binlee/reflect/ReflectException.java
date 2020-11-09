package com.binlee.reflect;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/10/1
 */
public final class ReflectException extends RuntimeException {

    public ReflectException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReflectException(String message) {
        super(message);
    }
}
