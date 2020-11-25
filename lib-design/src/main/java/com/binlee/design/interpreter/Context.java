package com.binlee.design.interpreter;

import com.binlee.util.Logger;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/11/25
 */
public final class Context {

    private final Logger logger;
    private final IExpression interpreter;

    public Context(Logger logger) {
        this.logger = logger;
        String[] cities = {"Hangzhou", "Jinhua"};
        String[] people = {"elder", "women", "children"};
        interpreter = new Dispatcher(new Terminal(cities), new Terminal(people));
    }

    public void handle(String info) {
        if (interpreter.interpret(info)) {
            logger.i("You're " + info + ", so it's free to take this bus!");
        } else {
            logger.i(info + " should pay 2 Yuan for this bus.");
        }
    }
}
