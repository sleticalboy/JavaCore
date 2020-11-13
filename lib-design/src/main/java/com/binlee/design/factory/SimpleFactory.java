package com.binlee.design.factory;

import com.binlee.design.bean.IPerson;
import com.binlee.design.bean.Jack;
import com.binlee.design.bean.Maria;
import com.binlee.design.bean.Tom;
import com.binlee.util.Logger;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/11/13
 * <p>
 * 简单工厂/静态工厂
 */
public final class SimpleFactory {

    private static final Logger LOGGER = Logger.get(SimpleFactory.class);

    private SimpleFactory() {
        //no instance
    }

    public static IPerson newMaria() {
        return new Maria();
    }

    public static IPerson newJack() {
        return new Jack();
    }

    public static IPerson newTom() {
        return new Maria();
    }

    public static IPerson create(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Unexpected id: null");
        }
        if (id.equals("Maria")) {
            return new Maria();
        }
        if (id.equals("Jack")) {
            return new Jack();
        }
        if (id.equals("Tom")) {
            return new Tom();
        }
        throw new IllegalArgumentException("Unknown id: " + id);
    }

    public static void exec() {
        LOGGER.d("simple/static factory run...");
        LOGGER.d("new maria: " + SimpleFactory.newMaria());
        LOGGER.d("new jack: " + SimpleFactory.newJack());
        LOGGER.d("new tom: " + SimpleFactory.newTom());
    }
}
