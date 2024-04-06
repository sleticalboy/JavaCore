package com.binlee.test;

import com.binlee.algo.arr.Arrays;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/10/20
 */
public final class ArraysTest {

    private final int[] arr = {0, 0, 2, 3, 2, 9, 5, 9, 4, 0};

    @Before
    public void before() {
        System.out.println("original: " + java.util.Arrays.toString(arr));
    }

    @Test
    public void duplicatedTest() {

        for (int i = 0, len = Arrays.removeDuplicated(arr); i < len; i++) {
            System.out.println("arr[" + i + "]: " + arr[i]);
        }

        Arrays.foo(arr);
    }

    @Test
    public void sortTest() {
        Arrays.sort(arr);
        System.out.println("sorted: " + java.util.Arrays.toString(arr));
    }

    @Test
    public void binSearch() {
        int[] arr = {1, 2, 3, 4, 5, 7, 9};
        final int index = Arrays.binSearch(arr, 60);
        System.out.println("index: " + index);
        Assert.assertEquals(-1, index);
    }
}
