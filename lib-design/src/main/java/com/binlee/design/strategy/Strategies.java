package com.binlee.design.strategy;

import java.util.*;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/11/5
 * <p>
 * 策略模式（Strategy），定义了一组算法，将每个算法都封装起来，并且使它们之间可以互换。
 */
public final class Strategies {

    private final Comparator<Cat> mComparator;

    private Strategies(Comparator<Cat> comparator) {
        mComparator = comparator;
    }

    private void sort(List<Cat> list) {
        list.sort(mComparator);
    }

    public static void main(String[] args) {
        exec();
    }

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
        System.out.println(cats);
        Strategies strategies = null;
        final Scanner scanner = new Scanner(System.in);
        System.out.println("请输入1 或 2：");
        final int mode = scanner.nextInt();
        if (mode == 1) {
            strategies = new Strategies(new SmallToLargeComparator());
        } else if (mode == 2) {
            strategies = new Strategies(new LargeToSmallComparator());
        } else {
            System.err.println("illegal mode: " + mode);
            System.exit(0);
        }
        strategies.sort(cats);
        System.out.println(cats);

        // Comparator 接口
        // 策略一：SmallToLargeComparator
        // 策略二：LargeToSmallComparator
    }

    static class SmallToLargeComparator implements Comparator<Cat> {

        {
            System.out.println("small to large");
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
            System.out.println("large to small");
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
