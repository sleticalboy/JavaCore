package com.binlee.algo.graph;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 4/4/24
 */
public class Graph {
  private final int vertexes; // 顶点的个数
  private final LinkedList<Integer>[] adj; // 邻接表
  //找到顶点后，终止递归的标志
  boolean found = false; // 全局变量或者类成员变量

  public Graph(int vertexes) {
    this.vertexes = vertexes;
    adj = new LinkedList[vertexes];
    for (int i = 0; i < vertexes; ++i) {
      adj[i] = new LinkedList<>();
    }
  }

  @Override
  public String toString() {
    return "Graph{" +
        "vertexes=" + vertexes +
        ", adj=" + Arrays.toString(adj) +
        ", found=" + found +
        '}';
  }

  public void addEdge(int start, int end) {
    adj[start].add(end);
    // adj[end].add(start);
  }

  public void dfs(int start, int end) {
    found = false;
    //visited 记录已经被访问的顶点，避免顶点被重复访问
    boolean[] visited = new boolean[vertexes];
    //prev 记录搜索路径
    int[] prev = new int[vertexes];
    for (int i = 0; i < vertexes; ++i) {
      prev[i] = -1;
    }
    recurDfs(start, end, visited, prev);
    print(prev, start, end);
    System.out.println();
  }

  private void recurDfs(int w, int t, boolean[] visited, int[] prev) {
    if (found == true) return;
    visited[w] = true;
    if (w == t) {
      found = true;
      return;
    }
    for (int i = 0; i < adj[w].size(); ++i) {
      int q = adj[w].get(i);
      if (!visited[q]) {
        prev[q] = w;
        recurDfs(q, t, visited, prev);
      }
    }
  }

  public void bfs(int start, int end) {
    if (start == end) return;
    //visited 记录已经被访问的顶点，避免顶点被重复访问
    boolean[] visited = new boolean[vertexes];
    visited[start] = true;
    //queue 用来存储已经被访问、但相连的顶点还没有被访问的顶点
    Queue<Integer> queue = new LinkedList<>();
    queue.add(start);
    //prev 记录搜索路径
    int[] prev = new int[vertexes];
    for (int i = 0; i < vertexes; ++i) {
      prev[i] = -1;
    }
    while (queue.size() != 0) {
      int w = queue.poll();
      for (int i = 0; i < adj[w].size(); ++i) {
        int q = adj[w].get(i);
        if (!visited[q]) {
          prev[q] = w;
          if (q == end) {
            print(prev, start, end);
            System.out.println();
            return;
          }
          visited[q] = true;
          queue.add(q);
        }
      }
    }
  }

  private void print(int[] prev, int s, int t) { // 递归打印 1->9 的路径
    if (prev[t] != -1 && t != s) {
      print(prev, s, prev[t]);
    }
    System.out.print(t + " ");
  }
}
