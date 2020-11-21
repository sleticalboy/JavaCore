package com.binlee.design.chain;

import com.binlee.util.Logger;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/11/20
 */
public abstract class AbstractHandler {

    protected final AbstractHandler next;
    protected final Logger logger;

    public AbstractHandler(AbstractHandler next) {
        this.next = next;
        logger = Logger.get(getClass());
    }

    public abstract boolean handle(String message);
}
