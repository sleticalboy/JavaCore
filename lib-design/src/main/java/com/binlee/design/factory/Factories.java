package com.binlee.design.factory;

import com.binlee.design.bean.Jack;
import com.binlee.design.bean.Tom;
import com.binlee.design.bean.Maria;
import com.binlee.design.bean.IPerson;
import com.binlee.util.Logger;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/10/13
 */
public class Factories {

    private static final Logger sLogger = Logger.get(Factories.class);

    // 任何可以产生对象的方法或类，都可以称之为工厂
    // 简单工厂：如果要创建的产品不多，只要一个工厂类就可以完成，这种模式叫“简单工厂模式”。
    // 在简单工厂模式中创建实例的方法通常为静态（static）方法，因此简单工厂模式又叫作静态工厂方法模式。
    // 简单工厂模式每增加一个产品就要增加一个具体产品类和一个对应的具体工厂类，这增加了系统的复杂度，违背了“开闭原则”。
    //    可扩展性差

    // 工厂方法：是对简单工厂的进一步抽象化, 其好处是可以使系统在不修改原来代码的情况下引进新的产品，即满足开闭原则。
    // 抽象工厂：一种为访问类提供一个创建一组相关或相互依赖对象的接口，且访问类无须指定所要产品的具体类就能得到同族的不同等级的产品的模式结构。
    // 抽象工厂模式是工厂方法模式的升级版本，工厂方法模式只生产一个等级的产品，而抽象工厂模式可生产多个等级的产品。

    // public static void main(String[] args) {
    //     exec();
    // }

    public static void exec() {
        sLogger.d("simple/static factory run...");
        sLogger.d("new maria: " + SimpleFactory.newMaria());
        sLogger.d("new jack: " + SimpleFactory.newJack());
        sLogger.d("new tom: " + SimpleFactory.newTom());

        sLogger.d("factory method run...");
        // 根据配置生成具体的工厂
        MyFactory factory = new TeacherFactory();
        Teacher teacher = (Teacher) factory.create();
        teacher.setName("John");
        teacher.sayHello();
        factory = new StudentFactory();
        final Student student = (Student) factory.create();
        student.setName("student Jordan");
        student.sayHello();

        sLogger.d("abstract factory run...");
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

    // 简单工厂/静态工厂
    public static class SimpleFactory {

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
    }

    // 抽象工厂：提供了产品的生成方法
    public interface MyFactory {

        // 生成产品
        IPerson create();
    }

    // 具体产品 Student
    private static class Student implements IPerson {

        private String mName;

        public Student() {
            this(null);
        }

        public Student(String name) {
            mName = name;
        }

        public void setName(String name) {
            mName = name;
        }

        @Override
        public String getName() {
            return mName;
        }

        @Override
        public void sayHello() {
            sLogger.i("student " + getName() + " say hello");
        }
    }

    // 具体产品 Teacher
    public static class Teacher implements IPerson {

        private String mName;

        public Teacher() {
            this(null);
        }

        public Teacher(String name) {
            mName = name;
        }

        public void setName(String name) {
            mName = name;
        }

        @Override
        public String getName() {
            return mName;
        }

        @Override
        public void sayHello() {
            sLogger.i("teacher " + getName() + " say hello");
        }
    }

    // Student 工厂
    public static class StudentFactory implements MyFactory {

        @Override
        public IPerson create() {
            sLogger.i("student factory create a student");
            return new Student();
        }
    }

    // Teacher 工厂
    public static class TeacherFactory implements MyFactory {
        @Override
        public IPerson create() {
            sLogger.i("teacher factory create a teacher");
            return new Teacher();
        }
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
            sLogger.d(mName + " has its duty");
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
            sLogger.d(mName + " has its duty");
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
            sLogger.d(mName + " has its usage");
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
            sLogger.d(mName + " has its usage");
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

    public static class StaffFactory implements IFactory<IStaff>  {
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
