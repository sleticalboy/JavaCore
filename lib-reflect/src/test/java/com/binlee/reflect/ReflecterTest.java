package com.binlee.reflect;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/11/9
 */
public class ReflecterTest {

    @Test
    public void testReflekt() {
        Object ret = Reflecter.on(((Object) "hello")).call("toString");
        Assert.assertEquals("hello", ret);

        ret = Reflecter.on(Foo.class).get("sName");
        Assert.assertEquals("Foo", ret);

        Reflecter.on(Foo.class).set("sName", "Foo2");
        ret = Reflecter.on(Foo.class).get("sName");
        Assert.assertEquals("Foo2", ret);

        FooA foo = new Foo(26);
        Reflecter.on(foo).call("say", new Class[]{String.class}, "maria");
        Reflecter.on(foo).set("age", 30);
        Reflecter.on(foo).call("say", new Class[]{String.class}, "john");

        // construct test
        foo = (Foo) Reflecter.on(Foo.class).create();
        Reflecter.on(foo).call("say", new Class[]{String.class}, "tom");
        // foo.say("tom");

        foo = (Foo) Reflecter.on(Foo.class).create(new Class[]{int.class}, 19);
        Reflecter.on(foo).call("say", new Class[]{String.class}, "jack");
        // foo.say("jack");

        // reflect super field
        Assert.assertEquals("FooA", Reflecter.on(foo).get("name"));
        // call super construct
        foo = (FooA) Reflecter.on(Foo.class).create(new Class[]{String.class}, "call super");
        // call super method
        Reflecter.on(foo).call("say");

        // test String
        System.out.println(Reflecter.on(((Object) "string")));

        // test org.junit.Assert
        System.out.println(Reflecter.on("org.junit.Assert"));
        System.out.println(Reflecter.on("org.junit.Assert").create());
        // rest sun.misc.Unsafe
        System.out.println(Reflecter.on("sun.misc.Unsafe"));
        System.out.println(Reflecter.on("sun.misc.Unsafe").create());
    }

    @Test
    public void testForName() {
        Assert.assertEquals(String.class, Reflecter.forName("java.lang.String"));
    }

    @Test
    public void testProx() {
        final Object obj = Reflecter.with(getClass().getClassLoader())
                .proxy(ISay.class).handle((proxy, method, args) -> null);
        Assert.assertEquals(null, obj);
    }

    public static class FooA {

        protected String name;

        protected FooA() {
            name = "FooA";
        }

        protected FooA(String name) {
            this.name = name;
        }

        protected void say() {
            System.out.println("FooA.say() name: " + this.name);
        }

        protected void say(String name) {
            System.out.println("FooA.say() name: " + name);
        }
    }

    public static class Foo extends FooA {

        private static String sName = "Foo";
        private final int age;

        private Foo() {
            age = 99;
        }

        public Foo(int age) {
            this.age = age;
        }

        // @Override
        // protected void say(String name) {
        //     System.out.println("Foo.say() name: " + name + ", age: " + age);
        // }
    }

    public interface ISay {

        void say(Object obj);
    }
}