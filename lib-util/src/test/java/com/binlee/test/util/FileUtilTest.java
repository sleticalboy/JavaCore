package com.binlee.test.util;

import com.binlee.util.FileUtil;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/10/12
 */
public final class FileUtilTest {

    @Test
    public void rootDirTest() throws IOException {
        System.out.println("test rootDir: " + FileUtil.getRootDir());
        System.out.println("test rootDir0: " + FileUtil.getRootDir0());
        // $projectDir/lib-util/build/classes/java/test/com/binlee/test/util/
        System.out.println("class file resource'': " + getClass().getResource(""));
        // $projectDir/lib-util/build/classes/java/test/com/binlee/test/
        System.out.println("class file resource'/': " + getClass().getResource("/"));
        // $projectDir/lib-util/build/classes/java/test/com/binlee/test/
        System.out.println("class loader '.': " + getClass().getClassLoader().getResource("."));
        // /
        System.out.println("/: " + new File("/").getCanonicalPath());
        // $projectDir/lib-util
        System.out.println("\"\": " + new File("").getCanonicalPath());
        // $projectDir/lib-util
        System.out.println("user.dir: " + System.getProperty("user.dir"));
        System.out.println("user.name: " + System.getProperty("user.name"));
        System.out.println("user.home: " + System.getProperty("user.home"));
    }
}
