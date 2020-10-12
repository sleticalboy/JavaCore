package com.binlee.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/10/12
 */
public final class FileUtil {

    private FileUtil() {
        //no instance
    }

    public static String getRootDir() throws IOException {
        // 默认情况下new File("")代表的目录为：System.getProperty("user.dir")
        return new File("").getCanonicalPath();
    }

    public static String getRootDir0() throws IOException {
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
