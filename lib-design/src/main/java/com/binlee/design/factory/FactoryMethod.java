package com.binlee.design.factory;

import com.binlee.design.bean.IPerson;
import com.binlee.util.Logger;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/11/13
 */
public final class FactoryMethod {

    private static final Logger LOGGER = Logger.get(FactoryMethod.class);

    public static void exec() {
        LOGGER.d("factory method run...");
        // 根据配置生成具体的工厂
        MyFactory factory = new TeacherFactory();
        Teacher teacher = (Teacher) factory.create();
        teacher.setName("John");
        teacher.sayHello();
        factory = new StudentFactory();
        final Student student = (Student) factory.create();
        student.setName("student Jordan");
        student.sayHello();
    }

    // 抽象工厂：提供了产品的生成方法
    public interface MyFactory {

        // 生成产品
        IPerson create();
    }

    // 具体产品 Student
    public static class Student implements IPerson {

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
            LOGGER.i("student " + getName() + " say hello");
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
            LOGGER.i("teacher " + getName() + " say hello");
        }
    }

    // Student 工厂
    public static class StudentFactory implements MyFactory {

        @Override
        public IPerson create() {
            LOGGER.i("student factory create a student");
            return new Student();
        }
    }

    // Teacher 工厂
    public static class TeacherFactory implements MyFactory {
        @Override
        public IPerson create() {
            LOGGER.i("teacher factory create a teacher");
            return new Teacher();
        }
    }
}
