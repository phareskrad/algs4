package jobinterviewquestions;

/**
 * Created by Leon on 8/12/15.
 */
public class MaxFlow {
    /*
    Question 1
    Fattest path.
    Given an edge-weighted digraph and two vertices s and t,
    design an ElogE algorithm to find a fattest path from s to t.
    The bottleneck capacity of a path is the minimum weight of an edge on the path.
    A fattest path is a path such that no other path has a higher bottleneck capacity.
    */

    /*
    1. Dijkstra algorithm with bottleneck (v -> w, bottleneck[w] < min(bottleneck[v], e(v, w))) instead of sum of distance, max PQ instead of min PQ
    Note: Not a ElogE algorithm

     */

    /*
    Question 2
    Perfect matchings in k-regular bipartite graphs.
    Suppose that there are n men and n women at a dance and that each man knows exactly k women and each woman knows exactly k men (and relationships are mutual).
    Show that it is always possible to arrange a dance so that each man and woman are matched with someone they know.
    */

    /*
    L:R vertices, add s and t and new edges for s to L and R to t,
    we can now send |L| units of flow from s to t by setting the flow to 1 for every new edge and to 1/k for every original edge.
     */

    /*
    Question 3
    Maximum weight closure problem.
    A subset of vertices S in a digraph is closed if there are no edges pointing from S to a vertex outside S.
    Given a digraph with weights (positive or negative) on the vertices, find a closed subset of vertices of maximum total weight.
     */

    /*
    From wiki:
    Adding two additional vertices s and t.
    For each vertex v with positive weight in G, the augmented graph H contains an edge from s to v with capacity equal to the weight of v,
    and for each vertex v with negative weight in G, the augmented graph H contains an edge from v to t whose capacity is the negation of the weight of v.
    All of the edges in G are given infinite capacity in H.

    A minimum cut separating s from t in this graph cannot have any edges of G passing in the forward direction across the cut:
    a cut with such an edge would have infinite capacity and would not be minimum.
     */
}
