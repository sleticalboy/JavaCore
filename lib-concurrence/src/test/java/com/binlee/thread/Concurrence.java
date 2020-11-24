package com.binlee.thread;

import com.binlee.thread.practice.Downloads;
import com.binlee.thread.practice.ProducerConsumerV1;
import com.binlee.thread.practice.ProducerConsumerV2;
import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/11/24
 */
public final class Concurrence {

    @Test
    public void multiThreadsDownload() {
        Downloads.exec();
    }

    @Test
    public void producerConsumer1() {
        ProducerConsumerV1.exec();
    }

    @Test
    public void producerConsumer2() {
        ProducerConsumerV2.exec();
    }

    @Test
    public void jol() {
        final Object obj = new Object();
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());
        synchronized (obj) {
            System.out.println("Now, obj has some sync info in its markword.");
        }
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());
        System.out.println(obj.hashCode());
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());
    }
}
