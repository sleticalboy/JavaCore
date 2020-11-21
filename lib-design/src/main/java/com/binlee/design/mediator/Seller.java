package com.binlee.design.mediator;

import com.binlee.util.Logger;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/11/19
 */
public class Seller implements ICustomer {

    private final Logger logger;
    private final String name;
    private IMedium medium;

    public Seller(String name) {
        this.name = name;
        logger = Logger.get(getClass());
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setMedium(IMedium medium) {
        this.medium = medium;
    }

    @Override
    public void send(String info) {
        medium.dispatch(name, info);
    }

    @Override
    public void receive(String from, String info) {
        logger.i("receive() called with: from = [" + from + "], info = [" + info + "]");
    }
}
