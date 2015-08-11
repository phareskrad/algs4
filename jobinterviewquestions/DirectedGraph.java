package jobinterviewquestions;

import algs4.*;

import java.util.Iterator;

/**
 * Created by Leon on 7/29/15.
 */
public class DirectedGraph {
    /*
    Question 1
    Shortest directed cycle.
    Given a digraph G,
    design an efficient algorithm to find a directed cycle with the minimum number of edges (or report that the graph is acyclic).
    The running time of your algorithm should be at most proportional to V(E+V) and use space proportional to E+V,
    where V is the number of vertices and E is the number of edges.
     */

    private boolean[] marked;
    private boolean[] onStack;
    private int[] edgeTo;
    Stack<Integer> cycle;
    private int[] group;

    private void dfs(Digraph g, int v) {
        marked[v] = true;
        onStack[v] = true;

        for (int i : g.adj(v)) {
            if (!marked[i]) {
                edgeTo[i] = v;
                dfs(g, i);
            }
            else if (onStack[i]) {
                cycle = new Stack<Integer>();
                for (int x = v; x != i; x = edgeTo[x])
                    cycle.push(x);
                cycle.push(i);
                cycle.push(v);
            }
        }
        onStack[v] = false;
    }

    public DirectedGraph(Digraph g) {
        marked = new boolean[g.V()];
        onStack = new boolean[g.V()];
        edgeTo = new int[g.V()];

        for (int v = 0; v < g.V(); v++) {
            if (!marked[v]) dfs(g, v);
        }
    }

    public Iterable<Integer> cycle() {
        return cycle;
    }

    public boolean hasCycle() {
        return cycle != null;
    }


    /*
    Question 2
    Hamiltonian path in a DAG.
    Given a directed acyclic graph,
    design a linear-time algorithm to determine whether it has a Hamiltonian path (a simple path that visits every vertex), and if so, find one.
     */

    public Iterable<Integer> hamiltonianPath(Digraph g) {
        //assert g is DAG
        Digraph gr = g.reverse();
        int v = findEnd(gr, 0);
        if (v > 0) {
            int count = 1;
            Queue<Integer> path = new Queue<Integer>();
            while (g.outdegree(v) == 1) {
                path.enqueue(v);
                for (int i : g.adj(v)) v = i;
                count++;
            }
            if (count == g.V()) return path;
        }
        return null;
    }

    private int findEnd(Digraph g, int v) {
        if (g.outdegree(v) == 0) return v;
        if (g.outdegree(v) == 1) {
            for (int i : g.adj(v)) return findEnd(g, i);
        }
        return -1;
    }

    /*
    Question 3
    Reachable vertex.
    DAG: Design a linear-time algorithm to determine whether a DAG has a vertex that is reachable from every other vertex, and if so, find one.
    Digraph: Design a linear-time algorithm to determine whether a digraph has a vertex that is reachable from every other vertex, and if so, find one.
     */

    public int findVinDAG(Digraph g) {
        //assert g is DAG
        Digraph gr = g.reverse();
        for (int v = 0; v < gr.V(); v++) {
            if (g.outdegree(v) == 0) {
                int count = 1;
                boolean[] marked = new boolean[gr.V()];
                marked[v] = true;
                Queue<Integer> visited = new Queue<Integer>();
                visited.enqueue(v);
                while (!visited.isEmpty()) {
                    int w = visited.dequeue();
                    for (int i : gr.adj(w)) {
                        if (!marked[i]) {
                            marked[i] = true;
                            count++;
                            visited.enqueue(i);
                        }
                    }
                }
                if (count == g.V()) return v;
                break;
            }
        }
        return -1;
    }

    private int[] id;
    private int count;

    private void dfs_kernel(Digraph g, int v) {
        marked[v] = true;
        id[v] = count;
        for (int w : g.adj(v)) {
            if (!marked[w]) dfs_kernel(g, w);
        }
    }

    public int findV(Digraph g) {
        TarjanSCC scc = new TarjanSCC(g);
        int c = scc.count();

        Digraph kDAG = new Digraph(c);

        DepthFirstOrder dfs = new DepthFirstOrder(g.reverse());
        marked = new boolean[g.V()];
        id = new int[g.V()];
        for (int v : dfs.reversePost()) {
            if (!marked[v]) {
                dfs_kernel(g, v);
                count++;
            }
        }

        for (int v : dfs.reversePost()) {
            for (int w : g.adj(v)) {
                if (id[w] != id[v]) kDAG.addEdge(id[v], id[w]);
            }
        }

        return findVinDAG(kDAG);
    }
}
