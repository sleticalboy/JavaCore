package com.binlee.design.chain;

import com.binlee.util.Logger;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/11/20
 */
public final class Chains {

    private static final Logger LOGGER = Logger.get(Chains.class);

    public static void exec() {
        final ChatHandler chatHandler = new ChatHandler(null);
        final NotifyHandler notifyHandler = new NotifyHandler(chatHandler);
        boolean handled = notifyHandler.handle("there comes a chat message.");
        LOGGER.i("exec() chat message handled: " + handled);
        handled = notifyHandler.handle("there comes a notify message.");
        LOGGER.i("exec() notify message handled: " + handled);
    }
}
