package jobinterviewquestions;

import algs4.WeightedQuickUnionUF;

/**
 * Created by Leon on 6/30/15.
 */
public class UnionFind {
    /* Question 1
    Social network connectivity.
    Given a social network containing N members and a log file containing M timestamps at which times pairs of members formed friendships,
    design an algorithm to determine the earliest time at which all members are connected
    (i.e., every member is a friend of a friend of a friend ... of a friend).
    Assume that the log file is sorted by timestamp and that friendship is an equivalence relation.
    The running time of your algorithm should be MlogN or better and use extra space proportional to N. */
    private class Log {
        int p;
        int q;
        double time;
    }

    public double getTime(Log[] M, int N) {
        WeightedQuickUnionUF network = new WeightedQuickUnionUF(N);

        for (int i = 0; i < M.length; i++) {
            network.union(M[i].p, M[i].q);
            if (network.count() == 1) return M[i].time;
        }
        return -1;
    }

    /*
    Question 2
    Union-find with specific canonical element.
    Add a method find() to the union-find data type so that find(i) returns the largest element in the connected component containing i.
    The operations, union(), connected(), and find() should all take logarithmic time or better.
    For example, if one of the connected components is {1,2,6,9}, then the find() method should return 9 for each of the four elements in the connected components.
     */

    private class MaxUF{
        private int[] id;
        private int[] size;
        private int[] largest;
        private int count;

        public MaxUF(int N){
            id = new int[N];
            size = new int[N];
            largest = new int[N];
            count = N;
            for (int i = 0; i < N; i++) {
                id[i] = i;
                size[i] = 1;
                largest[i] = i;
            }
        }

        private int root(int i){
            while (i != id[i]){
                id[i] = id[id[i]];
                i = id[i];
            }
            return i;
        }

        public void union(int p, int q){
            int proot = root(p);
            int qroot = root(q);
            if (proot == qroot) return;
            if (size[proot] <= size[qroot]) {
                id[proot] = qroot;
                size[qroot] += size[proot];
                if (largest[qroot] < largest[proot]) largest[qroot] = largest[proot];
            }
            else {
                id[qroot] = proot;
                size[proot] += size[qroot];
                if (largest[proot] < largest[qroot]) largest[proot] = largest[qroot];
            }
            count--;
        }

        public boolean connected(int p, int q){
            return root(p) == root(q);
        }

        public int count(){
            return count;
        }

        public int find(int i) {
            int root = root(i);
            return largest[root];
        }

    }

    /*
    Question 3
    Successor with delete. Given a set of N integers S={0,1,...,N−1} and a sequence of requests of the following form:
    Remove x from S
    Find the largest of x: the smallest y in S such that y≥x.
    design a data type so that all operations (except construction) should take logarithmic time or better.
     */

    private class Successor {
        private int[] id;
        private int[] size;
        private int[] largest;

        public Successor(int N) {
            id = new int[N + 1];
            size = new int[N + 1];
            largest = new int[N + 1];

            for (int i = 0; i <= N; i++) {
                id[i] = i;
                size[i] = 1;
                largest[i] = i;
            }
            largest[N] = -1;
        }

        private int root(int i){
            while (i != id[i]){
                id[i] = id[id[i]];
                i = id[i];
            }
            return i;
        }

        private void union(int p, int q){
            int proot = root(p);
            int qroot = root(q);
            if (proot == qroot) return;
            if (size[proot] <= size[qroot]) {
                id[proot] = qroot;
                size[qroot] += size[proot];
                if (largest[qroot] < largest[proot]) largest[qroot] = largest[proot];
            }
            else {
                id[qroot] = proot;
                size[proot] += size[qroot];
                if (largest[proot] < largest[qroot]) largest[proot] = largest[qroot];
            }
        }

        public void delete(int i) {
            assert i < id.length - 1 && i > 0;
            int rootnext = root(i + 1);
            if (size[i] <= size[rootnext]) {
                id[i] = id[rootnext];
                size[rootnext] += size[i];
            }
            else {
                id[rootnext] = id[i];
                size[i] += size[rootnext];
                largest[i] = largest[rootnext];
            }
        }

        public int find(int i) {
            int root = root(i);
            return largest[root];
        }
    }

    /*
    Question 4
    Union-by-size.
    Develop a union-find implementation that uses the same basic strategy as weighted quick-union
    but keeps track of tree height and always links the shorter tree to the taller one.
    Prove a lgN upper bound on the height of the trees for N sites with your algorithm.
     */

    private class WeightUFByHeight {
        private int[] id;
        private int[] size;
        private int count;
        private int[] largest;

        public WeightUFByHeight(int N){
            id = new int[N];
            size = new int[N];
            largest = new int[N];
            count = N;
            for (int i = 0; i < N; i++) {
                largest[i] = i;
                id[i] = i;
                size[i] = 1;
            }
        }

        private int root(int i){
            while (i != id[i]){
                id[i] = id[id[i]];
                i = id[i];
            }
            return i;
        }

        public void union(int p, int q){
            int proot = root(p);
            int qroot = root(q);
            if (proot == qroot) return;
            if (size[proot] < size[qroot]) {
                id[proot] = qroot;
                size[qroot] += size[proot] - 1;
                if (largest[qroot] < largest[proot]) largest[qroot] = largest[proot];
            }
            else if (size[proot] > size[qroot]){
                id[qroot] = proot;
                size[proot] += size[qroot] - 1;
                if (largest[proot] < largest[qroot]) largest[proot] = largest[qroot];
            }
            else {
                id[proot] = qroot;
                if (size[proot] > 1) size[qroot] += size[proot] - 1;
                else size[qroot] += size[proot];
                if (largest[qroot] < largest[proot]) largest[qroot] = largest[proot];
            }
            count--;
        }

        public boolean connected(int p, int q){
            return root(p) == root(q);
        }

        public int count(){
            return count;
        }

        public int find(int i) {
            int root = root(i);
            return largest[root];
        }
    }


}
