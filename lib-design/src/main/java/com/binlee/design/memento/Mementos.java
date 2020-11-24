package com.binlee.design.memento;

import com.binlee.util.Logger;

import java.util.Scanner;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/11/24
 */
public final class Mementos {

    private static final byte[] FAKE_COMMANDS = {0, 1, 1, 1, 2, 1, 3, 1, 0, 0};
    private static final String[] GIRLS = {"貂蝉", "昭君", "西施", "贵妃"};

    private static boolean sTest = false;
    private int index = 0;

    private final Logger logger;
    private final GirlStack stack;
    private final PlayBoy boy;

    private Mementos() {
        logger = Logger.get(getClass());
        stack = new GirlStack();
        boy = new PlayBoy();
        boy.setLover("single");
        stack.push(boy.createMemento());
    }

    public static void setTest(boolean test) {
        sTest = test;
    }

    public static void exec() {
        final Mementos mementos = new Mementos();
        final Scanner scanner = new Scanner(System.in);
        int cmd;
        while (true) {
            mementos.logger.i("There are five beauties, 0: Diaochan, 1: Zhaojun, 2: Xishi and 3: Guifei,");
            mementos.logger.i("Which one do you like best?(0, 1, 2 or 3)");
            cmd = mementos.readNext(scanner);
            if (cmd < 0 || cmd > 3) {
                throw new RuntimeException("invalid cmd: " + cmd);
            }
            if (mementos.stack.push(new Girl(GIRLS[cmd]))) {
                mementos.logger.i("you choose: " + GIRLS[cmd]);
            } else {
                throw new RuntimeException("such a play boy!");
            }
            mementos.logger.i("confirm(0) or change(1) another one?");
            cmd = mementos.readNext(scanner);
            if (cmd == 0) {
                mementos.logger.i("boy's final lover: " + mementos.boy.getLover());
                break;
            }
            if (cmd != 1) {
                throw new RuntimeException("invalid cmd: " + cmd);
            }
            // 反悔
            mementos.boy.restoreMemento(mementos.stack.pop());
            mementos.logger.i("boy's lover: " + mementos.boy.getLover());
        }
    }

    public int readNext(Scanner scanner) {
        if (!sTest) {
            return scanner.nextInt();
        }
        int index = this.index;
        this.index = index + 1;
        return FAKE_COMMANDS[index];
    }
}
