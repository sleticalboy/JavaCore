package com.binlee.thread.concurrent;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/9/27
 */
public final class Mutex {

    private final Sync mSync = new Sync();

    private Mutex() {
        // no instance
    }

    public static Mutex create() {
        return new Mutex();
    }

    public void lock() {
        mSync.acquire(1);
    }

    public boolean tryLock() {
        return mSync.tryAcquire(1);
    }

    public void unlock() {
        mSync.release(1);
    }

    public boolean isLocked() {
        return mSync.isHeldExclusively();
    }

    private static final class Sync extends JdkAQS {

        protected Sync() {
            super();
        }

        @Override
        protected boolean tryAcquire(int acquires) {
            throwEarly(acquires);
            // 当 expect 为 UNLOCKED 时才能设置，不可重入
            if (compareAndSetState(0, 1)) {
                // 设置当前线程为独占资源
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int acquires) {
            throwEarly(acquires);
            if (getState() == 0) {
                throw new IllegalMonitorStateException("lock has been released.");
            }
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        @Override
        protected int tryAcquireShared(int acquires) {
            // 不需实现
            return super.tryAcquireShared(acquires);
        }

        @Override
        protected boolean tryReleaseShared(int acquires) {
            // 不需实现
            return super.tryReleaseShared(acquires);
        }

        // 判断是否是锁定状态
        @Override
        protected boolean isHeldExclusively() {
            // 1：已锁定 0：未锁定
            return getState() == 1;
        }

        private void throwEarly(int acquires) {
            if (acquires != 1) {
                throw new IllegalArgumentException("acquires: " + acquires);
            }
        }
    }
}
