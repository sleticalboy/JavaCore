package com.binlee.thread;

import com.binlee.Logger;

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

    public static void main() {
        sLogger.log("there are 4 ways to start a new thread");
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
                final Thread thread = new Thread(r, "Thread#" + id);
                thread.setDaemon(false);
                sLogger.log("ThreadFactory#newThread() with " + thread.getName() + ", r: " + r);
                return thread;
            }
        }, new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                sLogger.err("rejectedExecution() r: " + r + ", executor: " + executor, null);
                if (!executor.isShutdown()) {
                    // final Runnable discarded = executor.getQueue().poll();
                    // sLogger.log("discard when rejected execution: " + discarded);
                    // executor.execute(r);
                    r.run();
                }
            }
        });
        sLogger.log("start a thread by ExecutorService");
        for (int i = 0; i < 4; i++) {
            service.submit(new NamedRunnable("Worker#", i) {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        sLogger.err(toString(), e);
                    }
                    sLogger.log("submit result from " + toString());
                }
            });
        }
        for (int i = 4; i < 8; i++) {
            service.execute(new NamedRunnable("Worker#", i) {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        sLogger.err(toString(), e);
                    }
                    sLogger.log("execute result from " + toString());
                }
            });
        }
        try {
            Thread.sleep(10000L);
            service.isShutdown();
            sLogger.log("shut down executor service");
            System.exit(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void way_3() {
        sLogger.log("start a thread by Thread and FutureTask&Callable");
        final FutureTask<String> task = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "result from FutureTask's Callable<String>#call()";
            }
        });
        new Thread(task).start();
        try {
            sLogger.log(task.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void way_2() {
        new Thread() {
            @Override
            public void run() {
                sLogger.log("start a thread by new Thread() {@Override run()}.start");
            }
        }.start();
    }

    private void way_1() {
        new Thread(() -> {
            sLogger.log("start a thread by new Thread(new Runnable() {}).start()");
        }).start();
    }
}
