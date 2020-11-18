package com.binlee.design.observer;

import com.binlee.util.Logger;

import java.util.Observable;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/11/18
 */
public class Observer implements java.util.Observer {

    private final Logger logger;

    public Observer() {
        logger = Logger.get(getClass());
    }

    @Override
    public void update(Observable o, Object arg) {
        logger.i("update() called with: o = [" + o + "], arg = [" + arg + "]");
    }
}
