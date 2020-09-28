package com.binlee.annotation;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/9/28
 */
public class Person {

    public static final int MALE = 0;
    public static final int FEMALE = 1;

    @Gender
    private int mGender;

    public void setGender(@Gender int gender) {
        mGender = gender;
    }

    @Gender
    public int getGender() {
        return mGender;
    }

    @IntDef({MALE, FEMALE})
    public @interface Gender {
    }
}
