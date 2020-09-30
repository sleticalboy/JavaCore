package com.binlee.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/9/30
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface Factory {

    // 子类类名
    String name();

    // 父类或接口类型
    Class<?> type();
}
