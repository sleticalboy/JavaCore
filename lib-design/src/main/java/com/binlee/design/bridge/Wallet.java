package com.binlee.design.bridge;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/11/16
 */
public final class Wallet extends AbstractBag {

    @Override
    public String getName() {
        return getColor().getColor() + " Wallet";
    }
}
