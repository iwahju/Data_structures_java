package edu.emory.cs.graph.flow;

import edu.emory.cs.graph.Edge;
import edu.emory.cs.graph.Graph;
import edu.emory.cs.graph.Subgraph;

import java.util.*;

public class NetworkFlowQuizExtra {
    public Set<Subgraph> getAugmentingPaths(Graph graph, int source, int target) {
        init(graph, source, target);
        Set<Subgraph> subgraphs = new HashSet<>();
        for (List<Integer> path : m_paths) {
            System.out.println(path);
            Subgraph subgraph = new Subgraph();
            for (int i = 1; i < path.size(); i++) {
                int x = path.get(i - 1);
                int y = path.get(i);
                subgraph.addEdge(new Edge(x, y, matrix[x][y]));
            }
            subgraphs.add(subgraph);
        }
        return subgraphs;
    }

    private List<List<Integer>> m_paths;
    private List<Integer>[] adj_list;
    private double[][] matrix;

    int ss, dd;

    @SuppressWarnings("unchecked")
    private void init(Graph graph, int source, int target) {
        adj_list = new List[graph.size()];
        matrix = new double[graph.size()][graph.size()];

        for (int i = 0; i < graph.size(); i++) {
            adj_list[i] = new ArrayList<>();
        }
        for (Edge e : graph.getAllEdges()) {
            adj_list[e.getSource()].add(e.getTarget());
            matrix[e.getSource()][e.getTarget()] = e.getWeight();
        }
        m_paths = new ArrayList<>();
        // boolean[] visited = new boolean[graph.size()];
        // ss = source;
        // dd = target;
        // List<Integer> path = new ArrayList<>();
        // path.add(source);
        // Dfs(source, target, visited, path);
        Bfs(source, target);
    }

    private void addPath(List<Integer> path) {
        m_paths.add(new ArrayList<Integer>(path));
    }

    private boolean visited(int x, List<Integer> path) {
        int size = path.size();
        for (int i = 0; i < size; i++) {
            if (path.get(i) == x) {
                return true;
            }
        }
        return false;
    }

    private void Bfs(int source, int target) {
        Queue<List<Integer>> queue = new LinkedList<>();
        List<Integer> path = new ArrayList<>();
        path.add(source);
        queue.offer(path);
        while (!queue.isEmpty()) {
            path = queue.poll();
            int last = path.get(path.size() - 1);
            if (last == target) {
                addPath(path);
            }
            List<Integer> lastNode = adj_list[last];
            for (Integer u : adj_list[last]) {
                if (!visited(u, path)) {
                    List<Integer> newPath = new ArrayList<>(path);
                    newPath.add(u);
                    queue.offer(path);
                }
            }
        }
    }
}
