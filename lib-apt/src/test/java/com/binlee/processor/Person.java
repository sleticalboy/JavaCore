package com.binlee.processor;

import com.binlee.annotation.Gender;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/9/28
 */
public final class Person {

    private final String mName;
    @Gender
    private final int mGender;

    public Person(String name, @Gender int gender) {
        mName = name;
        mGender = gender;
    }

    public String getName() {
        return mName;
    }

    @Gender
    public int getGender() {
        return mGender;
    }

    @Override
    public String toString() {
        return "Person{" +
                "mName='" + getName() + '\'' +
                ", mGender=" + getGender() +
                '}';
    }

    public void say() {
        System.out.println(toString());
    }
}
