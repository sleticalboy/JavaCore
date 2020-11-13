package com.binlee.thread;

import com.binlee.thread.practice.ProducerConsumerV1;
import com.binlee.thread.practice.ProducerConsumerV2;
import org.junit.Test;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/11/13
 */
public final class ProducerConsumerTest {

    @Test
    public void producerConsumer1() {
        ProducerConsumerV1.exec();
    }

    @Test
    public void producerConsumer2() {
        ProducerConsumerV2.exec();
    }
}
