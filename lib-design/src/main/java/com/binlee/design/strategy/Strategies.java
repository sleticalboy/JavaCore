package com.binlee.design.strategy;

import com.binlee.util.Logger;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/11/5
 * <p>
 * 策略模式（Strategy），定义了一组算法，将每个算法都封装起来，并且使它们之间可以互换。
 */
public final class Strategies {

    private static final Logger LOGGER = Logger.get(Strategies.class);
    public static boolean sTest = false;

    private final Comparator<Cat> mComparator;

    private Strategies(Comparator<Cat> comparator) {
        mComparator = comparator;
    }

    private void sort(List<Cat> list) {
        list.sort(mComparator);
    }

    // public static void main(String[] args) {
    //     exec();
    // }

    public static void exec() {
        List<Cat> cats = new ArrayList<>(4);
        final Random random = new Random();
        Cat cat;
        for (int i = 0; i < 4; i++) {
            cat = new Cat();
            cat.name = "cat " + (i + 1);
            cat.age = random.nextInt();
            cats.add(cat);
        }
        LOGGER.d(String.valueOf(cats));
        Comparator<Cat> comparator = null;

        final Scanner scanner = new Scanner(sTest ? new ByteArrayInputStream(new byte[]{'2'}) : System.in);
        LOGGER.i("请输入1 或 2：");
        final int mode = scanner.nextInt();
        if (mode == 1) {
            comparator = new SmallToLargeComparator();
        } else if (mode == 2) {
            comparator = new LargeToSmallComparator();
        } else {
            LOGGER.e("illegal mode: " + mode);
            // System.exit(0);
        }
        new Strategies(comparator).sort(cats);
        LOGGER.d(String.valueOf(cats));

        // Comparator 策略接口
        // 策略实现一：SmallToLargeComparator
        // 策略实现二：LargeToSmallComparator
    }

    static class SmallToLargeComparator implements Comparator<Cat> {

        {
            LOGGER.v("small to large");
        }

        @Override
        public int compare(Cat o1, Cat o2) {
            if (o1 == null || o2 == null) {
                return 0;
            }
            // 从小到大排序
            // public static int compare(int x, int y) {
            //    return (x < y) ? -1 : ((x == y) ? 0 : 1);
            // }
            return Integer.compare(o1.age, o2.age);
        }
    }

    static class LargeToSmallComparator implements Comparator<Cat> {

        {
            LOGGER.v("large to small");
        }

        @Override
        public int compare(Cat o1, Cat o2) {
            if (o1 == null || o2 == null) {
                return 0;
            }
            // 从大到小排序
            return -Integer.compare(o1.age, o2.age);
        }
    }

    static class Cat {
        int age;
        String name;

        @Override
        public String toString() {
            return "Cat{" +
                    "age=" + age +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

}
