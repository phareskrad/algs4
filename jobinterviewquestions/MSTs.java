package jobinterviewquestions;

import algs4.*;
import com.sun.corba.se.impl.activation.ServerTableEntry;

/**
 * Created by Leon on 8/5/15.
 */
public class MSTs {
    /*
    Question 1
    Bottleneck minimum spanning tree.
    Given a connected edge-weighted graph,
    design an efficient algorithm to find a minimum bottleneck spanning tree.
    The bottleneck capacity of a spanning tree is the weights of its largest edge.
    A minimum bottleneck spanning tree is a spanning tree of minimum bottleneck capacity.
    */

    /*
    Can use any algorithm that help building a MST or:
    Camerini's algorithm:
    1. Find the median edge weight W (find kth algorithm, use pivot and recursively find kth)
    2. two subgraph by the median edge,
        if the lower part connected (using DFS or BFS), then decrease W and repeat 1, 2
        if the not connected, let the connected component become one node, increase W, repeat 1, 2
     */

    /*
    Question 2
    Is an edge in a MST.
    Given an edge-weighted graph G and an edge e, design a linear-time algorithm to determine whether e appears in some MST of G.
    Note: Since your algorithm must take linear time in the worst case, you cannot afford to compute the MST itself.
    */

    public boolean edgeInMST(EdgeWeightedGraph G, Edge e) {
        SET<Integer> vertices = new SET<Integer>();
        double weight = e.weight();
        for (Edge edge: G.edges()) {
            if (edge.weight() < weight) {
                int v = edge.either();
                int w = edge.other(v);
                vertices.add(v); vertices.add(w);
            }
        }
        int v = e.either();
        int w = e.other(v);
        if (vertices.contains(v) && vertices.contains(w)) return false;
        return true;
    }

    /*
    Question 3
    Minimum-weight feedback edge set.
    A feedback edge set of a graph is a subset of edges that contains at least one edge from every cycle in the graph.
    If the edges of a feedback edge set are removed,
    the resulting graph is acyclic. Given an edge-weighted graph,
    design an efficient algorithm to find a feedback edge set of minimum weight. Assume the edge weights are positive.
     */

    /*
    use kruskal's algorithm, but use MaxPQ
     */

    public Queue<Edge> MFES(EdgeWeightedGraph G) {
        MaxPQ<Edge> pq = new MaxPQ<Edge>();
        Queue<Edge> mfes = new Queue<Edge>();
        int size = 0;

        for (Edge e : G.edges()) {
            pq.insert(e);
        }
        UF uf = new UF(G.V());
        while (!pq.isEmpty()) {
            Edge e = pq.delMax();
            int v = e.either();
            int w = e.other(v);
            if (!uf.connected(v, w)) {
                uf.union(v, w);
            }
            else {
                mfes.enqueue(e);
            }
        }
        return mfes;

    }


}
