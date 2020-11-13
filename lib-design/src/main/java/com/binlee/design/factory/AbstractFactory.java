package com.binlee.design.factory;

import com.binlee.util.Logger;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/11/13
 */
public final class AbstractFactory {

    private static final Logger LOGGER = Logger.get(Factories.class);

    public static void exec() {
        LOGGER.d("abstract factory run...");
        ICompany company = new Alibaba(new StaffFactory(), new StuffFactory());
        for (int i = 0; i < 10; i++) {
            company.createStaff().duty();
            company.createStuff().usage();
        }

        company = new ByteDance(new StaffFactory(), new StuffFactory());
        for (int i = 0; i < 10; i++) {
            company.createStaff().duty();
            company.createStuff().usage();
        }
    }

    /**
     * @author binlee sleticalboy@gmail.com
     * created by IDEA on 2020/10/13
     */
    public interface IFactory<F> {

        /**
         * 简单工厂
         *
         * @param id 产品标识
         * @return id 对应的产品
         */
        F create(String id);
    }

    ////////////////////////// abstract factory start
    // 抽象工厂：公司
    public interface ICompany {

        // 由子工厂 StaffFactory 生产
        IStaff createStaff();

        // 由子工厂 StuffFactory 生产
        IStuff createStuff();
    }

    // 抽象产品：员工类
    public interface IStaff {

        void duty();
    }

    // 抽象产品：物体
    public interface IStuff {

        void usage();
    }

    // 具体产品 Employee -> IStaff
    public static class Employee implements IStaff {

        private final String mName;

        public Employee(String name) {
            mName = name;
        }

        @Override
        public void duty() {
            LOGGER.d(mName + " has its duty");
        }
    }

    // 具体产品 Manager -> IStaff
    public static class Manager implements IStaff {

        private final String mName;

        public Manager(String name) {
            mName = name;
        }

        @Override
        public void duty() {
            LOGGER.d(mName + " has its duty");
        }
    }

    // 具体产品 Desk -> IStuff
    public static class Desk implements IStuff {

        private final String mName;

        public Desk(String name) {
            mName = name;
        }

        @Override
        public void usage() {
            LOGGER.d(mName + " has its usage");
        }
    }

    // 具体产品 Table -> IStuff
    public static class Table implements IStuff {

        private final String mName;

        public Table(String name) {
            mName = name;
        }

        @Override
        public void usage() {
            LOGGER.d(mName + " has its usage");
        }
    }

    // 具体公司：Alibaba
    public static class Alibaba implements ICompany {

        private final AtomicInteger mStaffSequence = new AtomicInteger(0);
        private final AtomicInteger mStuffSequence = new AtomicInteger(0);
        private final IFactory<IStaff> mStaffFactory;
        private final IFactory<IStuff> mStuffFactory;

        public Alibaba(IFactory<IStaff> staffFactory, IFactory<IStuff> stuffFactory) {
            mStaffFactory = staffFactory;
            mStuffFactory = stuffFactory;
        }

        @Override
        public IStaff createStaff() {
            return mStaffFactory.create("Alibaba staff#" + mStaffSequence.getAndIncrement());
        }

        @Override
        public IStuff createStuff() {
            return mStuffFactory.create("Alibaba stuff#" + mStuffSequence.getAndIncrement());
        }
    }

    // 具体公司：字节跳动
    public static class ByteDance implements ICompany {

        private final AtomicInteger mStaffSequence = new AtomicInteger(0);
        private final AtomicInteger mStuffSequence = new AtomicInteger(0);
        private final IFactory<IStaff> mStaffFactory;
        private final IFactory<IStuff> mStuffFactory;

        public ByteDance(IFactory<IStaff> staffFactory, IFactory<IStuff> stuffFactory) {
            mStaffFactory = staffFactory;
            mStuffFactory = stuffFactory;
        }

        @Override
        public IStaff createStaff() {
            return mStaffFactory.create("ByteDance staff#" + mStaffSequence.getAndIncrement());
        }

        @Override
        public IStuff createStuff() {
            return mStuffFactory.create("ByteDance stuff#" + mStuffSequence.getAndIncrement());
        }
    }

    public static class StaffFactory implements IFactory<IStaff> {
        @Override
        public IStaff create(String id) {
            if (id == null || id.trim().length() == 0) {
                throw new IllegalArgumentException("id: " + id);
            }
            int seq;
            try {
                seq = Integer.parseInt(id.substring(id.indexOf('#') + 1));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("id: " + id, e);
            }
            if (seq % 2 == 0) {
                return new Employee(id);
            }
            return new Manager(id);
            // if (id.equals("employee")) {
            //     return new Employee();
            // } else if ("manager".equals(id)) {
            //     return new Manager();
            // } else {
            //     throw new IllegalArgumentException("id: " + id);
            // }
        }
    }

    public static class StuffFactory implements IFactory<IStuff> {
        @Override
        public IStuff create(String id) {
            int seq;
            try {
                seq = Integer.parseInt(id.substring(id.indexOf('#') + 1));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("id: " + id, e);
            }
            if (seq % 2 == 0) {
                return new Desk(id);
            }
            return new Table(id);
            // if ("desk".equals(id)) {
            //     return new Desk();
            // } else if ("table".equals(id)) {
            //     return new Table();
            // } else {
            //     throw new IllegalArgumentException("id: " + id);
            // }
        }
    }
    ////////////////////////// abstract factory stop
}
