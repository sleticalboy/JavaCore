package com.binlee.annotation;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/9/29
 */
@IntDef({Gender.MALE, Gender.FEMALE})
public @interface Gender {
    int MALE = 0;
    int FEMALE = 1;
}
