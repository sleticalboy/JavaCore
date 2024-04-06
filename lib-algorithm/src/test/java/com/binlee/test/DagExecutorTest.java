package com.binlee.test;

import com.binlee.algo.dag.Dag;
import com.binlee.algo.dag.DagExecutor;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.ImmutableGraph;
import org.junit.Test;

/** DAGExecutor 测试 */
public class DagExecutorTest {

  /** 任务 */
  static class Task extends Dag.Task {

    private final String name;

    Task(String name) {
      this.name = name;
    }

    @Override
    public String toString() {
      return "{name='" + name + "'}";
    }

    @Override
    public void execute() {
      try {
        Thread.sleep(250);
      } catch (InterruptedException e) {
        // do nothing
        e.printStackTrace();
      }
      System.out.println("Thread: " + Thread.currentThread().getId() + " Task: " + name + " is done.");
    }
  }

  @Test
  public void singleThread() throws InterruptedException {
    final DagExecutor executor = new DagExecutor();
    // 任务
    Task t1 = new Task("transcoding");
    Task t2 = new Task("speak-closer");
    Task t3 = new Task("landmarks");
    Task t4 = new Task("vbss");
    Task t5 = new Task("asr");
    Task t6 = new Task("translate");
    Task t7 = new Task("tts");
    Task t8 = new Task("hierspeech");
    Task t9 = new Task("talking-head");
    Task t10 = new Task("video-export");
    final ImmutableGraph<Task> graph = GraphBuilder.directed()
        .<Task>immutable()
        .putEdge(t1, t2) // 转码 - 开闭口
        .putEdge(t2, t3) // 转码 - 点位提取
        .putEdge(t1, t4) // 转码 - vbss
        .putEdge(t4, t5) // vbss - asr
        .putEdge(t5, t6) // asr - 翻译
        .putEdge(t6, t7) // 翻译 - tts
        .putEdge(t5, t7) // asr - tts
        .putEdge(t4, t8) // vbss - hierspeech
        .putEdge(t7, t8) // tts - hierspeech
        .putEdge(t2, t9) // 开闭口 - 嘴唇驱动
        .putEdge(t3, t9) // 点位 - 嘴唇驱动
        .putEdge(t8, t10) // hierspeech - 导出
        .putEdge(t4, t10) // vbss - 导出
        .putEdge(t9, t10) // 嘴唇驱动 - 导出
        .build();
    System.out.println(graph);
    // 图转换为DAG
    final Dag<Task> dag = new Dag<>(graph);
    System.out.println(dag);
    dag.dump();

    // 提交
    executor.submit(dag);
    synchronized (this) {
      while (dag.status() != Dag.Status.COMPLETED) {
        wait(250);
        dag.dump();
      }
    }
    System.out.println("done....." + dag.status());
  }
}
