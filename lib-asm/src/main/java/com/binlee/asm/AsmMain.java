package com.binlee.asm;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Arrays;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/10/8
 */
public final class AsmMain {

    private static final String BASE_DIR;

    static {
        String baseDir;
        try {
            baseDir = getBaseDir();
        } catch (Exception e) {
            e.printStackTrace();
            baseDir = null;
        }
        System.out.println("baseDir: " + baseDir);
        BASE_DIR = baseDir;
    }

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

    private static String getBaseDir() throws Exception {
        final InputStream is = Runtime.getRuntime().exec("pwd").getInputStream();
        int len;
        final byte[] buffer = new byte[1024];
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        while ((len = is.read(buffer)) != -1) {
            baos.write(buffer, 0, len);
        }
        is.close();
        baos.flush();
        baos.close();
        return baos.toString().trim();
    }
}
