package com.binlee.thread;

import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/11/5
 */
public final class JolTest {

    @Test
    public void foo() {
        final Object obj = new Object();
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());
        synchronized (obj) {
            System.out.println("Now, obj has some sync info in its markword.");
        }
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());
        System.out.println(obj.hashCode());
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());
    }
}
