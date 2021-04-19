package com.binlee.design.singleton;

import com.binlee.util.Logger;

import java.io.Serializable;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/10/31
 */
public final class Singles {

    private static final Logger sLogger = Logger.get(Singles.class);

    // 单例（Singleton）模式：某个类只能生成一个实例，该类提供了一个全局访问点供外部获取该实例，其拓展是有限多例模式。
    // 单例模式有 8 种
    // 饿汉式 2 种(静态常量懒、静态代码块)
    // 懒汉式 3 种(单线程、线程安全、同步代码块)
    // 其他 3 种（双检锁、静态内部类、枚举）

    // public static void main(String[] args) {
    //     exec();
    // }

    public static void exec() {
        Single_1.getInstance().dump();
        Single_2.getInstance().dump();
        Single_3.getInstance().dump();
        Single_4.getInstance().dump();
        Single_5.getInstance().dump();
        Single_6.getInstance().dump();
        Single_7.getInstance().dump();
        Single_8.getInstance().dump();
    }

    //////////////////// 饿汉式 2 种
    public static class Single_1 implements Serializable {

        /**
         * 饿汉式，静态常量，多线程安全
         * 类一加载就初始化了，如果在构造器中做大量初始化工作就不适用了
         */
        private static final Single_1 SINGLE_1 = new Single_1();

        private Single_1() {
            // do stuff
        }

        public static Single_1 getInstance() {
            return SINGLE_1;
        }

        public void dump() {
            sLogger.d("Single 1 run... " + hashCode());
        }

        // 防止序列化破坏单例
        private Object readResolve() {
            return SINGLE_1;
        }
    }

    public static class Single_6 {

        private static final Single_6 sInstance;

        static {
            sInstance = new Single_6();
        }

        private Single_6() {
            // do stuff
        }

        /**
         * 饿汉式，静态代码块，使用的时候才实例化，多线程安全
         */
        public static Single_6 getInstance() {
            return sInstance;
        }

        public void dump() {
            sLogger.d("Single 6 run... " + hashCode());
        }
    }

    ///////////////// 懒汉式 3 种

    public static class Single_4 {

        private static Single_4 sInstance;

        /**
         * 懒汉式单例，单线程环境下
         */
        public static Single_4 getInstance() {
            if (sInstance == null) {
                sInstance = new Single_4();
            }
            return sInstance;
        }

        public void dump() {
            sLogger.d("Single 4 run... " + hashCode());
        }
    }

    public static class Single_7 {

        private static Single_7 sInstance;

        /**
         * 懒汉式单例，多线程环境下，效率低
         */
        public static synchronized Single_7 getInstance() {
            if (sInstance == null) {
                sInstance = new Single_7();
            }
            return sInstance;
        }

        public void dump() {
            sLogger.d("Single 7 run... " + hashCode());
        }
    }

    public static class Single_8 {

        private static Single_8 sInstance;

        /**
         * 懒汉式单例，线程不安全，效率低
         */
        public static Single_8 getInstance() {
            if (sInstance == null) {
                synchronized (Single_8.class) {
                    sInstance = new Single_8();
                }
            }
            return sInstance;
        }

        public void dump() {
            sLogger.d("Single 8 run... " + hashCode());
        }
    }

    /////////////// 其他 3 种

    public static class Single_5 {

        private volatile static Single_5 sInstance;

        private Single_5() {
            // do stuff
        }

        /**
         * 懒汉式，双检锁，volatile，线程安全，效率较高
         */
        public static Single_5 getInstance() {
            if (sInstance == null) {
                synchronized (Single_5.class) {
                    if (sInstance == null) {
                        sInstance = new Single_5();
                    }
                }
            }
            return sInstance;
        }

        public void dump() {
            sLogger.d("Single 5 run... " + hashCode());
        }
    }

    public static class Single_2 {

        private static class Holder {
            private static final Single_2 SINGLE_2 = new Single_2();
        }

        private Single_2() {
            // do stuff
        }

        /**
         * 内部类单例
         * 多线程安全
         */
        public static Single_2 getInstance() {
            return Holder.SINGLE_2;
        }

        public void dump() {
            sLogger.d("Single 2 run... " + hashCode());
        }
    }

    public enum Single_3 {

        SINGLE_3;

        /**
         * 枚举单例
         * 防止反序列化
         */
        public static Single_3 getInstance() {
            return SINGLE_3;
        }

        public void dump() {
            sLogger.d("Single 3 run... " + hashCode());
        }
    }
}

