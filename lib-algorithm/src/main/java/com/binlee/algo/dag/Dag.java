package com.binlee.algo.dag;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.graph.Graph;
import com.google.common.graph.Graphs;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/** directed-acyclic-graph (DAG) */
public final class Dag<R extends Dag.Task> {

  public static abstract class Task implements Runnable {

    private Status mStatus = Status.IDLE;

    public final Status status() {
      return mStatus;
    }

    public final boolean idle() {
      return mStatus == Status.IDLE;
    }

    @Override
    public final void run() {
      try {
        mStatus = Status.PROCESSING;
        execute();
        mStatus = Status.COMPLETED;
      } catch (Throwable tr) {
        mStatus = Status.ERRORS;
      }
    }

    public abstract void execute();
  }

  /** 所有任务（顶点） */
  private final Set<R> tasks;

  /** 任务依赖的（多个）任务 */
  private final ListMultimap<R, R> dependencies;

  /** 任务错误信息 */
  private final Map<R, Throwable> errors;

  /** 锁 */
  private final ReadWriteLock lock = new ReentrantReadWriteLock();
  private final Lock readLock = lock.readLock();
  private final Lock writeLock = lock.writeLock();

  /**
   * 基于给定的图，创建一个DAG
   *
   * @param graph 期待有向无环图
   */
  public Dag(Graph<R> graph) {
    if (!graph.isDirected()) {
      throw new RuntimeException("Graph must be directed.");
    }
    if (Graphs.hasCycle(graph)) {
      throw new RuntimeException("Graph has cycle.");
    }
    // 全部任务
    tasks = Sets.newHashSet(graph.nodes());
    dependencies = ArrayListMultimap.create();
    errors = Maps.newHashMap();
    for (R node : tasks) {
      // 依赖（前驱任务）
      dependencies.putAll(node, graph.predecessors(node));
    }
  }

  /** 任务状态，在 DAGExecutor 执行完成后调用 */
  public Status status() {
    readLock.lock();
    try {
      if (numTasks() == 0) {
        return Status.COMPLETED;
      }
      if (!errors.isEmpty()) {
        return Status.ERRORS;
      }
      return Status.PROCESSING;
    } finally {
      readLock.unlock();
    }
  }

  /** DAG 执行错误信息 */
  public Map<R, Throwable> getErrors() {
    readLock.lock();
    try {
      return errors;
    } finally {
      readLock.unlock();
    }
  }

  /** 是否存在下一个任务 */
  public boolean hasNextTask() {
    readLock.lock();
    try {
      return peekNextTask() != null;
    } finally {
      readLock.unlock();
    }
  }

  /**
   * 返回下一个任务，会删除该任务
   *
   * @return 下一个任务
   */
  public R nextTask() {
    writeLock.lock();
    try {
      R task = peekNextTask();
      if (task == null) {
        return null;
      }
      // tasks.remove(task);
      return task;
    } finally {
      writeLock.unlock();
    }
  }

  /** 返回下一个要执行的任务，不会删除该任务 */
  private R peekNextTask() {
    return peekInternal(tasks);
  }

  private R peekInternal(Iterable<R> tasks) {
    //{name='视频导出'}=>[{name='hierspeech'}, {name='vbss'}, {name='嘴唇驱动'}]
    // {name='转码'}=>[]
    // {name='点位'}=>[{name='开闭口'}]
    // {name='嘴唇驱动'}=>[{name='开闭口'}, {name='点位'}]
    // {name='vbss'}=>[{name='转码'}]
    // {name='开闭口'}=>[{name='转码'}]
    // {name='tts'}=>[{name='翻译'}, {name='asr'}]
    // {name='asr'}=>[{name='vbss'}]
    // {name='翻译'}=>[{name='asr'}]
    // {name='hierspeech'}=>[{name='vbss'}, {name='tts'}]
    for (final R task : tasks) {
      // 执行中或完成或失败
      if (!task.idle()) continue;
      final List<R> deps = dependencies.get(task);
      if (deps.size() > 0) {
        final R target = peekInternal(deps);
        if (target != null) return target;
      }
      return task;
    }
    return null;
  }

  /** 是否存在任务，如果 hasNextRunnableTask 返回 true，还存在任务，则说明图存在环 */
  public boolean hasTasks() {
    return numTasks() > 0;
  }

  /** 是否存在执行错误 */
  public boolean hasErrors() {
    readLock.lock();
    try {
      return errors.size() > 0;
    } finally {
      readLock.unlock();
    }
  }

  /**
   * 任务执行成功通知，依赖会删除该任务的
   *
   * @param runnable 任务
   */
  public void notifyDone(R runnable) {
    // Remove task from the list of remaining dependencies for any other tasks.
    writeLock.lock();
    try {
      // dependencies.values().remove(runnable);
    } finally {
      writeLock.unlock();
    }
  }

  /**
   * 任务执行错误
   *
   * @param runnable 任务
   * @param error    错误
   */
  public void notifyError(R runnable, Throwable error) {
    writeLock.lock();
    try {
      errors.put(runnable, error);
    } finally {
      writeLock.unlock();
    }
  }

  /**
   * 剩余的任务数目
   *
   * @return 剩余的任务数目
   */
  public int numTasks() {
    readLock.lock();
    try {
      int num = 0;
      for (final R task : tasks) {
        if (task.idle()) num++;
      }
      return num;
    } finally {
      readLock.unlock();
    }
  }

  @Override
  public String toString() {
    return "Dag{deps=" + dependencies + '}';
  }

  public void dump() {
    System.out.print("hasNextTask: " + hasNextTask());
    System.out.print(", hasTasks: " + hasTasks());
    System.out.print(", numTasks: " + numTasks());
    System.out.print(", errors: " + getErrors());
    System.out.println(", status: " + status());
  }

  /** 状态 */
  public enum Status {
    /** 未执行 */
    IDLE,
    /** 正常完成所有任务 */
    COMPLETED,
    /** 任务存在错误 */
    ERRORS,
    /** 处理中 */
    PROCESSING,
    ;
  }
}
