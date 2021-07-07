package com.binlee.thread;

import com.binlee.thread.practice.ProducerConsumerV1;
import com.binlee.thread.practice.ProducerConsumerV2;
import com.binlee.thread.practice.ProducerConsumerV3;
import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/11/24
 */
public final class Concurrence {

    @Test
    public void countDownLatches() {
        CountDownLatches.exec();
    }

    @Test
    public void multiThreadsDownload() {
        // Downloads.exec();
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
    public void producerConsumer3() {
        ProducerConsumerV3.exec();
    }

    final Obj obj = new Obj();

    // @Test
    public void jolTest() {
        // new Object() 在内存中占用多少字节？16
        // class 对象头：
        // mark word 8 字节：锁信息、gc、hashcode
        // class pointer 4字节：
        // 对齐 4 字节
        // 8 + 4 + 4 = 16
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());
        // OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
        //       0     4        (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
        //       4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
        //       8     4        (object header)                           e5 01 00 20 (11100101 00000001 00000000 00100000) (536871397)
        //      12     4        (loss due to the next object alignment)
        // Instance size: 16 bytes
        // Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
        synchronized (obj) {
            System.out.println("Now, obj has some sync info in its markword.");
        }
        // Now, obj has some sync info in its markword.
        // java.lang.Object object internals:
        //  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
        //       0     4        (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
        //       4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
        //       8     4        (object header)                           e5 01 00 20 (11100101 00000001 00000000 00100000) (536871397)
        //      12     4        (loss due to the next object alignment)
        // Instance size: 16 bytes
        // Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());
        System.out.println("Now, obj has some hash code info in its markword.");
        System.out.println(obj.hashCode());
        // Now, obj has some hash code info in its markword.
        // 803924041
        // java.lang.Object object internals:
        //  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
        //       0     4        (object header)                           01 49 e8 ea (00000001 01001001 11101000 11101010) (-353875711)
        //       4     4        (object header)                           2f 00 00 00 (00101111 00000000 00000000 00000000) (47)
        //       8     4        (object header)                           e5 01 00 20 (11100101 00000001 00000000 00100000) (536871397)
        //      12     4        (loss due to the next object alignment)
        // Instance size: 16 bytes
        // Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());
    }

    static class Obj {
        // markword + classpointer = 8 + 4 = 12
        final String name = "name"; // 4
        byte gender = 1; // 1
        boolean alive; // 1
        char flag = 'f';// 2
        short age = 0; // 2
        int header; // 4
        float salary; // 4
        double volume; // 8
        long time; // 8
    }
}
