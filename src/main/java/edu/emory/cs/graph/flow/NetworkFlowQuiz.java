package edu.emory.cs.graph.flow;

import edu.emory.cs.graph.Edge;
import edu.emory.cs.graph.Graph;
import edu.emory.cs.graph.Subgraph;

import java.util.*;

public class NetworkFlowQuiz {
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
    private double[][] matrix ;

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
        boolean[] visited = new boolean[graph.size()];
        ss = source;
        dd = target;
        List<Integer> path = new ArrayList<>();
        path.add(source);
        Dfs(source, target, visited, path);
    }

    private void Dfs(int source, int target, boolean[] visited, List<Integer> path) {
        if (source == target) {
            m_paths.add(new ArrayList<>(path));
            return;
        }
        visited[source] = true;
        for (Integer i : adj_list[source]) {
            if (!visited[i]) {
                path.add(i);
                Dfs(i, target, visited, path);
                path.remove(i);
            }
        }
        visited[source] = false;
    }
}
