package edu.emory.cs.graph.span;

import edu.emory.cs.graph.Graph;

public class MSTAllHWTest {
    static Graph getGraph1() {
        Graph graph = new Graph(5);

        graph.setUndirectedEdge(0, 4, 1);
        graph.setUndirectedEdge(0, 3, 1);
        graph.setUndirectedEdge(1, 2, 1);
        graph.setUndirectedEdge(1, 3, 1);
        graph.setUndirectedEdge(2, 4, 1);

        return graph;
    }

    public static void main(String[] args) {
        System.out.println("Graph");
        System.out.println(getGraph1());
        System.out.println();

        MST mst = new MSTKruskal();

        SpanningTree treeKruskal = mst.getMinimumSpanningTree(getGraph1());
        System.out.println("MST");
        System.out.println(treeKruskal);
    }
}
