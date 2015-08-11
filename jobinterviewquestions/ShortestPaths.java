package jobinterviewquestions;

import algs4.*;

/**
 * Created by Leon on 8/5/15.
 */
public class ShortestPaths {
    /*
    Question 1
    Monotonic shortest path.
    Given an edge-weighted digraph G,
    design an ElogE algorithm to find a monotonic shortest path from s to every other vertex.
    A path is monotonic if the sequence of edge weights along the path are either strictly increasing or strictly decreasing.
    */
    private DirectedEdge[] edgeTo;
    private double[] distTo;
    private IndexMinPQ<Double> pq;

    /*
    1. Sort edges of each vertex, ascending or descending
    2. relax edges using Dijkstra algorithm, check monotonic condition before check distTo
     */

    /*
    Question 2
    Critical edge.
    Given an edge-weighted digraph,
    design an ElogV algorithm to find an edge whose removal causes the maximal increase (possibly infinite) in the length of the shortest path from s to t.
    Assume all of the edge weights are strictly positive.
    */

    /*
    1. remove the last edge before t, and run shortest path from s to t, find the change in distance, if infinite return
    2. compare the new path with the original one, and find the last common vertex, use that as new t, repeat 1, 2
    3. when reach s, compare all the distance changes by removals, decide which is the critical one
     */

    /*
    Question 3
    Shortest path with one skippable edge.
    Given an edge-weighted digraph,
    design an ElogV algorithm to find a shortest path from s to t
    where you can change the weight of any one edge to zero. Assume the edge weights are nonnegative.
     */

    //1. add reverse method in EdgeWeightedDigraph class
    public Iterable<DirectedEdge> skippablePath(EdgeWeightedDigraph G,int s, int t) {
        DijkstraSP spaths = new DijkstraSP(G, s);
        DijkstraSP tpaths = new DijkstraSP(G.reverse(), t);

        double min = Double.MAX_VALUE;
        DirectedEdge skippable = null;

        for (DirectedEdge e : G.edges()) {
            int v = e.from();
            int w = e.to();
            if (spaths.distTo(v) + tpaths.distTo(w) < min) {
                skippable = e;
                min = spaths.distTo(v) + tpaths.distTo(w);
            }
        }

        Stack<DirectedEdge> skippablepath = new Stack<DirectedEdge>();
        Stack<DirectedEdge> tmp = new Stack<DirectedEdge>();

        for (DirectedEdge e : tpaths.pathTo(skippable.to())) skippablepath.push(e);
        skippablepath.push(skippable);
        for (DirectedEdge e : spaths.pathTo(skippable.from())) tmp.push(e);
        for (DirectedEdge e : tmp) skippablepath.push(e);
        return skippablepath;
    }


}
