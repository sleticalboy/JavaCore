package com.binlee.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/10/9
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface Singleton {

    String value() default "getInstance";
}
