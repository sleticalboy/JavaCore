package com.binlee.thread;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/9/28
 */
public final class Conceptions {

    // JMM(Java Memory Model) 规定：
    // 1、所有变量都存储在主内存中；
    // 2、每个线程都有自己的工作内存，用来保存该线程用到的主内存中的变量的拷贝副本；
    // 3、线程对变量的所有操作都必须在工作内存中进行，不能直接操作主内存；
    // 4、不同的线程无法直接访问彼此的工作内存，线程间变量的传递需要自己的工作内存和主内存之间进行数据同步
    // 总结：JMM 是一种规范，目的是解决由于多线程通过共享内存进行通信时，存在本地的内存数据不一致、编译器会对代码指令重排序、
    // 处理器会对代码乱序执行等带来的问题
    //
    // 主内存：类比计算机内存模型的主存
    // JMM: 作用于工作内存和主内存之间数据同步过程
    // 工作内存：类比计算机模型的缓存
    //
    // 计算机硬件内存架构：
    // CPU、高速缓存、缓存一致性协议、主内存
    //
    // JVM: JVM 栈、native 栈、堆、方法区、程序计数器

    public static void main() {
        // 原子操作：不会被线程调度机制打断的操作（从操作开始到操作结束，不会被人打扰）[atomic operation]
        // 多线程并发：各个线程轮流获得 CPU 使用权，分别执行各自的任务
        // 线程调度：指操作系统按照特定机制为多个线程分配 CPU 使用权
        //  分时调度：让所有的线程轮流获得 CPU 的使用权，并且平均分配每个线程占用 CPU 的时间片
        //  抢占式调度：优先让优先级高的线程使用 CPU，若优先级相同则随机分配（JVM 采用此调度模型）

        // 原子性: synchronized
        //    1. monitorenter
        //    2. monitorexit
        // 可见性: volatile
        //   1. 被其修饰的变量被修改后要立即同步到主内存中、每次使用都从主内存中刷新、保证多线程操作时变量的可见性;
        // 有序性: synchronized & volatile
        //   1. volatile 禁止指令重排;
        //   2. synchronized 保证同一时刻只允许一条线程操作

        // 自旋锁和阻塞锁最大的区别就是：到底要不要放弃 CPU 的执行时间
        // 自旋锁会不停地查看共享资源是否可用，阻塞锁会等待操作系统唤醒且在等待期间可以做自己的事儿

        // 锁消除、锁粗化、偏向锁、适应性自旋锁
        final Conceptions conceptions = new Conceptions();
        conceptions.lockCoarse();
        conceptions.lockWipe();
    }

    // flags: ACC_PUBLIC, ACC_SYNCHRONIZED
    public synchronized void sync() {
        // synchronized 可重入原理: 一个线程访问会引起计数器 +1，释放时计数器会 -1，计数器为 0 时其他线程可访问
    }

    public void foo() {
        // monitorenter
        synchronized (this) {
            // code block
            // 偏向锁 > 轻量锁 > 重量锁
        }
        // monitorexit
    }

    // 锁消除
    private void lockWipe() {
        final Object lock = new Object();
        synchronized (lock) {
            doSomething(lock);
        }
        // 消除之后
        doSomething(lock);
    }

    // 锁粗化
    private void lockCoarse() {
        for (int i = 0; i < 100; i++) {
            // 锁细化
            synchronized (this) {
                doSomething(i);
            }
        }
        // javac 编译后: 锁粗化（扩大锁的范围）
        synchronized (this) {
            for (int i = 0; i < 100; i++) {
                doSomething(i);
            }
        }
    }

    private void doSomething(Object obj) {
        // code block
    }
}
