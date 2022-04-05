package edu.emory.cs.graph;

import java.util.*;

public class GraphQuiz extends Graph {
    private List<Deque<Edge>> matrix;
    private List<List<Integer>> m_paths;
    private List[] adj_list;

    public GraphQuiz(int size) {
        super(size);
    }

    public GraphQuiz(Graph g) {
        super(g);
    }

    int ss, dd;
    public int numberOfCycles() {
        adj_list = new List[size()];
        for (int i = 0; i < size(); i++) {
            adj_list[i] = new ArrayList<>();
        }
        for (Edge e : getAllEdges()) {
            adj_list[e.getSource()].add(e.getTarget());
        }

        m_paths = new ArrayList<>();
        matrix = getOutgoingEdges();

        for (Edge e : getAllEdges()) {
            System.out.println(e.getTarget() + " " + e.getSource());
            ss = e.getTarget();
            dd = e.getSource();
            paths(e.getTarget(), e.getSource());
        }

        int[] used = new int[m_paths.size()];
        List<List<Integer>> ls_paths = new ArrayList<>(m_paths);
        for (int i = 0; i < used.length; i++) {
            if (used[i] == 1) continue;
            for (int j = i + 1; j < used.length; j++) {
                if (comparePaths(ls_paths.get(i), ls_paths.get(j))) {
                    used[j] = 1;
                }
            }
        }
        int count = 0;
        for (int i = 0; i < used.length; i++) {
            count += used[i] == 0 ? 1 : 0;
        }
        return count;
    }

    public boolean comparePaths(List<Integer> p1, List<Integer> p2) {
        if (p1.size() != p2.size()) return false;
        int[] d1 = new int[size()];
        int[] d2 = new int[size()];
        int pr1 = p1.get(p1.size() - 1), pr2 = p2.get(p1.size() - 1);
        for (int i = 0; i < p1.size(); i++) {
            d1[p1.get(i)] = pr1;
            d2[p2.get(i)] = pr2;
            pr1 = p1.get(i);
            pr2 = p2.get(i);
        }
        return Arrays.compare(d1, d2) == 0;
    }

    private void paths(int source, int dest) {
        boolean[] visited = new boolean[size()];
        //List<Integer> path = new ArrayList<>();
        find_path(source, dest, visited, new ArrayList<>());
    }

    private void find_path(int source, int dest, boolean[] visited, List<Integer> path) {
        visited[source] = true;
        path.add(source);
        if (source == dest && path.get(0) == ss && path.get(path.size() - 1) == dd) {
            // System.out.println(path);
            m_paths.add(new ArrayList<>(path));
        } else {
            List<Integer> pts = adj_list[source];
            for (Integer i : pts) {
                if (!visited[i]) {
                    find_path(i, dest, visited, path);
                }
            }
        }
        path.remove(0);
        visited[source] = false;
    }

}
