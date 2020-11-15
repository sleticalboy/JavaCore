package com.binlee.design.proxy;

import com.binlee.util.Logger;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/11/15
 */
public class Agent implements IStar {

    private final static Logger LOGGER = Logger.get(Agent.class);

    private final IStar bruceLee = new BruceLee();

    @Override
    public void perform() {
        LOGGER.i("perform() first ...");
        bruceLee.perform();
    }

    @Override
    public void act() {
        LOGGER.i("act() first ...");
        bruceLee.act();
    }
}
