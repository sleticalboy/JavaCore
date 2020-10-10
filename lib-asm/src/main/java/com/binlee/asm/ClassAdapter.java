package com.binlee.asm;

import java.util.Arrays;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/10/8
 */
public final class ClassAdapter {

    private static final String BASE_DIR = "/home/binli/code/github/java/JavaCore";

    public static void run(String... args) {
        System.out.println("run() called with: args = [" + Arrays.toString(args) + "]");
        try {
            DemoGenerator.generate(BASE_DIR + "/lib-asm/build/AutoGenDemo.class");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            // MethodEnhancer.enhance(BASE_DIR);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ServiceGenerator.generate(BASE_DIR + "/app/build/classes/java/main/com/binlee/design/singleton/ServiceManager.class");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
