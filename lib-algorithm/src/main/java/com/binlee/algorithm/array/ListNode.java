package com.binlee.algorithm.array;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2021/7/7
 */
public class ListNode<T> {

    public ListNode<T> next;
    public final T value;

    public ListNode(T value) {
        this.value = value;
    }
}
