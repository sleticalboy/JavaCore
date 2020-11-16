package com.binlee.design.composite;

import com.binlee.util.Logger;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/11/16
 */
public final class Composites {

    private static final Logger L = Logger.get(Composites.class);

    public static void exec() {
        final ShoppingCart cart = new ShoppingCart();
        cart.addGood(new PersonalComputer("Dell PC"));
        cart.addGood(new Screen("Dell screen"));
        cart.addGood(new Mouse("Dell wireless mouse"));
        cart.addGood(new Keyboard("Dell wireless keyboard"));
        final float sum = cart.calculate();
        L.i("exec() sum: ￥" + sum);
    }
}
