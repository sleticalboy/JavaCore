package com.binlee.design.interpreter;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/11/25
 */
public class Dispatcher implements IExpression {

    private final IExpression city;
    private final IExpression people;

    public Dispatcher(IExpression city, IExpression people) {
        this.city = city;
        this.people = people;
    }

    @Override
    public boolean interpret(String info) {
        if (info == null || !info.contains("'s")) {
            return false;
        }
        final String[] pieces = info.replace(" ", "").split("'s");
        return pieces.length >= 2 && city.interpret(pieces[0]) && people.interpret(pieces[1]);
    }
}
