package com.binlee.design.interpreter;

import com.binlee.util.Logger;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/11/25
 */
public final class Interpreters {

    // 用解释器模式设计一个“韶粵通”公交车卡的读卡器程序
    // 假如“韶粵通”公交车读卡器可以判断乘客的身份，如果是“韶关”或者“广州”的“老人”
    // “妇女”“儿童”就可以免费乘车，其他人员乘车一次扣 2 元
    // 1. 定义一个抽象表达式（Expression）接口，它包含了解释方法 interpret(String info)
    // 2. 定义一个终结符表达式（TerminalExpression）类，它用集合（Set）类来保存满足条件的城市或人， 并实现抽象表达式
    //    接口中的解释方法 interpret(Stringinfo)，用来判断被分析的字符串是否是集合中的终结符。
    // 3. 定义一个非终结符表达式（AndExpression）类，它也是抽象表达式的子类，它包含满足条件的城市的终结符表达式对象和
    //    满足条件的人员的终结符表达式对象，并实现 interpret(String info) 方法，用来判断被分析的字符串是否是满足条件
    //    的城市中的满足条件的人员。
    // 4. 最后，定义一个环境（Context）类，它包含解释器需要的数据，完成对终结符表达式的初始化，并定义一个方法
    //    freeRide(String info) 调用表达式对象的解释方法来对被分析的字符串进行解释

    private Interpreters() {
    }

    public static void exec() {
        final Context context = new Context(Logger.get(Interpreters.class));
        context.handle("Hangzhou's elder");
        context.handle("Hangzhou's women");
        context.handle("Hangzhou's children");

        context.handle("Jinhua's elder");
        context.handle("Jinhua's women");
        context.handle("Jinhua's children");

        context.handle("Hangzhou's men");
        context.handle("Jinhua's men");

        context.handle("Beijing's people");
    }
}
