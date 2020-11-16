package com.binlee.design.bridge;

import com.binlee.util.Logger;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/11/16
 */
public final class Bridges {

    private static final Logger L = Logger.get(Bridges.class);

    public static void exec() {
        AbstractBag bag = new Wallet();
        bag.setColor(new Red());
        showBag(bag.getName());
    }

    private static void showBag(String name) {
        L.i("showBag() called with: name = [" + name + "]");
    }
}
