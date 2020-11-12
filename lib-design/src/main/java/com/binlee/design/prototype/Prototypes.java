package com.binlee.design.prototype;

import com.binlee.design.bean.IPerson;
import com.binlee.util.Logger;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/11/12
 */
public class Prototypes {

    private static final Logger LOGGER = Logger.get(Prototypes.class);

    private Prototypes() {
    }

    public static void exec() {
        final Student tom = new Student("tom");
        tom.sayHello();
        final Student jack = tom.clone();
        jack.setName("jack");
        jack.sayHello();
        final Student maria = jack.clone();
        maria.setName("maria");
        maria.sayHello();
    }

    private static class Student implements IPerson, Cloneable {

        private String mName;

        private Student(String name) {
            mName = name;
        }

        @Override
        public String getName() {
            return mName;
        }

        public void setName(String name) {
            mName = name;
        }

        @Override
        public void sayHello() {
            LOGGER.i("good student " + getName());
        }

        @Override
        public Student clone() {
            try {
                return (Student) super.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
                return this;
            }
        }
    }
}
