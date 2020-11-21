package com.binlee.design.state;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/11/21
 */
public final class States {

    public static void exec() {
        Context context = new Context();
        context.addScore(30);
        context.addScore(40);
        context.addScore(25);
        context.addScore(-15);
        context.addScore(-25);
    }
}
