package com.binlee.util;

import org.junit.Test;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2021/10/24
 */
public class InstanceofTest {

    @Test
    public void testInstanceof() {
        String name = "Ben";
        // jvm 对应也有一个 instanceof 关键字
        boolean is = name instanceof CharSequence;
        System.out.println(is);
    }
}
