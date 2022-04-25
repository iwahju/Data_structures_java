package edu.emory.cs.graph.span;

import edu.emory.cs.graph.Edge;
import edu.emory.cs.graph.Graph;

import java.util.*;

public class MSTAllHW implements MSTAll {

    int count = 0;

    private List<Integer>[] adj_list;

    @SuppressWarnings("unchecked")
    @Override
    public List<SpanningTree> getMinimumSpanningTrees(Graph graph) {
        List<SpanningTree> result = new ArrayList<>();

        if (graph.size() == 0) {
            return result;
        }

        SpanningTree init = new MSTKruskal().getMinimumSpanningTree(graph);
        double minWeight = init.getTotalWeight();
        count = init.size();

        List<Edge> edges = graph.getAllEdges();
        Set<Map.Entry<Integer, Integer>> st = new HashSet<>();
        allPossible = new HashSet<>();
        allEdges = new HashMap<>();


        for (int i = 0, idx = 0; i < edges.size(); i++) {
            int x1 = edges.get(i).getSource();
            int x2 = edges.get(i).getTarget();
            if (x1 > x2) {
                int tmp = x1;
                x1 = x2;
                x2 = tmp;
            }
            if (!st.contains(new AbstractMap.SimpleEntry<>(x1, x2)))
                allEdges.put(idx++, edges.get(i));
            st.add(new AbstractMap.SimpleEntry<>(x1, x2));
        }
        subsets();

        Set<Set<Map.Entry<Integer, Integer>>> ords = new HashSet<>();
        for (List<Edge> edges1 : allPossible) {
            adj_list = new List[graph.size()];
            for (int i = 0; i < graph.size(); i++) {
                adj_list[i] = new ArrayList<>();
            }
            double sum = 0;
            SpanningTree tree = new SpanningTree();
            Set<Map.Entry<Integer, Integer>> tmp = new HashSet<>();
            for (Edge e : edges1) {
                int x1 = e.getSource();
                int x2 = e.getTarget();
                if (x1 > x2) {
                    int tmp1 = x1;
                    x1 = x2;
                    x2 = tmp1;
                }
                tmp.add(new AbstractMap.SimpleEntry<>(x1, x2));
                tree.addEdge(new Edge(x1, x2, e.getWeight()));
                sum += e.getWeight();
                adj_list[e.getSource()].add(e.getTarget());
                adj_list[e.getTarget()].add(e.getSource());
            }

            if (tmp.size() != count || ords.contains(tmp)) continue;
            ords.add(tmp);

            if (sum != minWeight) continue;

            if (isValid(adj_list)) {
                result.add(tree);
            }
        }

        return result;
    }

    private boolean isValid(List<Integer>[] adj) {
        boolean[] visited = new boolean[adj.length];
        if (Dfs(adj, 0, visited, -1)) {
            return false;
        }
        for (int u = 0; u < adj.length; u++) {
            if (!visited[u]) {
                return false;
            }
        }
        return true;
    }

    private boolean Dfs(List<Integer>[] adj, int v, boolean[] vis, int parent) {
        vis[v] = true;
        for (Integer i : adj[v]) {
            if (!vis[i]) {
                if (Dfs(adj, i, vis, v)) {
                    return true;
                }
            } else if (i != parent) {
                return true;
            }
        }
        return false;
    }



    private Map<Integer, Edge> allEdges;
    private Set<List<Edge>> allPossible;

    private void subsets() {
        subsetsAux(new ArrayList<>(), 0);
    }

    private void subsetsAux(List<Edge> subset, int idx) {
        if (subset.size() == count) {
            allPossible.add(new ArrayList<>(subset));
        }
        for (int i = idx; i < allEdges.size(); i++) {
            subset.add(new Edge(allEdges.get(i)));
            subsetsAux(subset, idx + 1);
            subset.remove(subset.size() - 1);
        }
    }



    //****** This is the extra credit graph but is in the MSTAllHW instead of the MSTAllHWTest******//



//    public static void main(String[] args) {
//        Graph graph = new Graph(5);
//
//        graph.setUndirectedEdge(0, 4, 1);
//        graph.setUndirectedEdge(0, 3, 1);
//        graph.setUndirectedEdge(1, 2, 1);
//        graph.setUndirectedEdge(1, 3, 1);
//        graph.setUndirectedEdge(2, 4, 1);
//
//        MSTAll mst = new MSTAllHW();
//        List<SpanningTree> sp = mst.getMinimumSpanningTrees(graph);
//        System.out.println("Trees: " + sp.size());
//        int i = 1;
//        for (SpanningTree tree : sp) {
//            System.out.println("Tree: " + i++);
//            System.out.println(tree);
//        }
//    }
}
