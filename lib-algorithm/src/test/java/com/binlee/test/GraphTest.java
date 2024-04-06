package com.binlee.test;

import com.binlee.algo.graph.Graph;
import org.junit.Test;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 4/4/24
 */
public class GraphTest {

  @Test
  public void graphTest() {
    Graph graph = new Graph(5);
    graph.addEdge(0, 1);
    graph.addEdge(1, 2);
    graph.addEdge(2, 4);
    graph.addEdge(0, 3);
    graph.addEdge(3, 4);
    System.out.println(graph);
    graph.bfs(0, 4);
    graph.dfs(0, 4);
  }
}
