package com.binlee.design.factory.impl;

import com.binlee.annotation.Factory;
import com.binlee.design.factory.IShape;

import java.io.IOException;
import java.util.Map;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/10/4
 */
@Factory(type = IShape.class, name = "Circle")
public final class Circle implements IShape {

    static {
        // static init
    }

    {
        // common init
    }

    public Circle() {
        //
    }

    public Circle(int size) {
        //
    }

    @Override
    public String getShape() {
        return "Circle";
    }

    @Override
    public void draw() {
        System.out.println("draw() ->" + getShape());
    }

    public Object foo(String foo, Map<String, String> owners, Object... args) throws IOException {
        return null;
    }

    private static void foo() {}

    class Inner {}

    @Factory(name = "InnerShape", type = IShape.class)
    public static class InnerShape implements IShape {
        @Override
        public String getShape() {
            return null;
        }

        @Override
        public void draw() {

        }
    }
}