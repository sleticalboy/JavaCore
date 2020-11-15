package com.binlee.design.proxy;

import com.binlee.util.Logger;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/11/15
 */
public final class BruceLee implements IStar {

    private final static Logger LOGGER = Logger.get(BruceLee.class);

    @Override
    public void perform() {
        LOGGER.i("perform()...");
    }

    @Override
    public void act() {
        LOGGER.i("act()...");
    }
}
