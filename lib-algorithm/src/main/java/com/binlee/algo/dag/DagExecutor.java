package com.binlee.algo.dag;

/** 单线程实现 DAGExecutor */
public class DagExecutor {

  private volatile boolean running = true;

  public boolean isShutdown() {
    return !running;
  }

  public void shutdown() {
    // do nothing
  }

  public void shutdownNow() {
    running = false;
  }

  public <R extends Dag.Task> void submit(Dag<R> dag) {
    while (running) {
      R task = dag.nextTask();
      if (task == null) {
        return;
      }
      try {
        task.run();
        dag.notifyDone(task);
      } catch (Throwable err) {
        dag.notifyError(task, err);
      }
    }
  }
}
