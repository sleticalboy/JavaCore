package com.binlee.design.decorator;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/11/16
 */
public final class Decorators {

    public static void exec() {
        IMorrigan original = new Morrigan();
        original.display();
        IMorrigan succubus = new Succubus(original);
        succubus.display();
        IMorrigan girl = new Girl(original);
        girl.display();
    }
}
