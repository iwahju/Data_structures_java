package edu.emory.cs.set;

public class DisjointSetQuiz {
    public static void main(String[] args) {
        DisjointSet ds = new DisjointSet(5);
        System.out.println(ds.union(1, 3));
        System.out.println(ds.union(2, 3));
        System.out.println(ds.union(4, 3));
        System.out.println(ds.union(0, 1));
        System.out.println(ds.toString());
        // All point to the root

    }
}
