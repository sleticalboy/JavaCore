package com.binlee.test.util;

import com.binlee.util.FileUtil;
import org.junit.Test;

import java.io.IOException;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/10/12
 */
public class FileUtilTest {

    @Test
    public void rootDirTest() throws IOException {
        System.out.println("test rootDir: " + FileUtil.getRootDir());
        System.out.println("test rootDir0: " + FileUtil.getRootDir0());
    }
}
