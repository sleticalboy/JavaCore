package com.binlee.thread;

import com.binlee.util.Logger;
import com.binlee.util.NamedRunnable;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/9/25
 */
public final class WayToStartThread {

    private static final Logger sLogger = Logger.get(WayToStartThread.class);

    private WayToStartThread() {
    }

    public static void run() {
        sLogger.v("there are 4 ways to start a new thread");
        WayToStartThread how = new WayToStartThread();
        how.way_1();
        how.way_2();
        how.way_3();
        how.way_4();
    }

    private void way_4() {
        final AtomicInteger seq = new AtomicInteger(100);
        final ExecutorService service = new ThreadPoolExecutor(4, 8, 3L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(4), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                final int id = seq.getAndIncrement();
                final Thread thread = new Thread(r, "Thread" + id);
                thread.setDaemon(false);
                sLogger.v("ThreadFactorynewThread() with " + thread.getName() + ", r: " + r);
                return thread;
            }
        }, new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                sLogger.e("rejectedExecution() r: " + r + ", executor: " + executor);
                if (!executor.isShutdown()) {
                    // final Runnable discarded = executor.getQueue().poll();
                    // sLogger.log("discard when rejected execution: " + discarded);
                    // executor.execute(r);
                    r.run();
                }
            }
        });
        sLogger.v("start a thread by ExecutorService");
        for (int i = 0; i < 4; i++) {
            service.submit(new NamedRunnable("Worker", i) {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        sLogger.e(toString(), e);
                    }
                    sLogger.v("submit result from " + toString());
                }
            });
        }
        for (int i = 4; i < 8; i++) {
            service.execute(new NamedRunnable("Worker", i) {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        sLogger.e(toString(), e);
                    }
                    sLogger.v("execute result from " + toString());
                }
            });
        }
        try {
            Thread.sleep(10000L);
            service.isShutdown();
            sLogger.v("shut down executor service");
            System.exit(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void way_3() {
        sLogger.v("start a thread by Thread and FutureTask&Callable");
        final FutureTask<String> task = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "result from FutureTask's Callable<String>call()";
            }
        });
        new Thread(task).start();
        try {
            sLogger.v(task.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void way_2() {
        new Thread() {
            @Override
            public void run() {
                sLogger.v("start a thread by new Thread() {@Override run()}.start");
            }
        }.start();
    }

    private void way_1() {
        new Thread(() -> {
            sLogger.v("start a thread by new Thread(new Runnable() {}).start()");
        }).start();
    }
}
