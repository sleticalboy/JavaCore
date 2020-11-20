package com.binlee.design.chain;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/11/20
 */
public class NotifyHandler extends AbstractHandler {

    public NotifyHandler(AbstractHandler next) {
        super(next);
    }

    @Override
    public boolean handle(String message) {
        if (message.contains("notify")) {
            logger.i("handle() message: " + message);
            return true;
        }
        return next != null && next.handle(message);
    }
}
