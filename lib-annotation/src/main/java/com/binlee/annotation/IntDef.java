package com.binlee.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/9/28
 */
// 生命周期只源代码级别，最终会被 javac 丢弃
@Retention(RetentionPolicy.SOURCE)
// 作用域：类、接口
@Target(ElementType.ANNOTATION_TYPE)
public @interface IntDef {

    // 自定义 IntDef 注解(元注解，作用在其他注解类型上)
    int[] value() default {};
}
