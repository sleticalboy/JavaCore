package com.binlee.design.facade;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/11/16
 */
public final class Facade {

    private final System1 sys1;
    private final System2 sys2;
    private final System3 sys3;

    public Facade() {
        sys1 = new System1();
        sys2 = new System2();
        sys3 = new System3();
    }

    public void work() {
        sys1.work();
        sys2.work();
        sys3.work();
    }
}
