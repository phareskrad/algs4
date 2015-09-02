package jobinterviewquestions;

import algs4.Bag;
import stdlib.In;

import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Created by Leon on 7/14/15.
 */
public class QuickSort {
    /*
    Question 1
    Nuts and bolts.
    A disorganized carpenter has a mixed pile of N nuts and N bolts.
    The goal is to find the corresponding pairs of nuts and bolts.
    Each nut fits exactly one bolt and each bolt fits exactly one nut.
    By fitting a nut and a bolt together, the carpenter can see which one is bigger
    (but the carpenter cannot compare two nuts or two bolts directly).
    Design an algorithm for the problem that uses NlogN compares (probabilistically).
     */

    /*
    Binary search, compare each nut with bolts already compared (logN! = NlogN time), identify the interval, then divide the bolts in the interval (Sum of N/x = NlogN time)
     */

    class Nut {
        private int size;
        public int compare(Bolt bolt) {
            if (bolt.size > this.size) return -1;
            else if (bolt.size < this.size) return 1;
            else return 0;
        }
    }

    class Bolt {
        private int size;
    }

    public void pair(Bolt[] bolts, Nut[] nuts) {
        int n = nuts.length;
        assert bolts.length == n;
        Nut[] auxN = new Nut[n];
        Bolt[] auxB = new Bolt[n]; //need TreeMap to implement
        for (int i = 0; i < n; i++) {
            int lo = floor(auxB, nuts[i]); //use floor api in TreeMap
            int hi = ceil(auxB, nuts[i]); //use ceil api in TreeMap
            int index = partition(bolts, nuts[i], lo, hi);
            auxB[index] = bolts[index];
            auxN[index] = nuts[i];
        }

        for (int i = 0; i < n; i++) {
            nuts[i] = auxN[i];
        }
    }

    private int partition(Bolt[] bolts, Nut nut, int lo, int hi) {
        int l = lo;
        int r = hi;
        while (true) {
            while (nut.compare(bolts[++l]) > 0) if (l == hi) break;
            while (nut.compare(bolts[--r]) < 0) if (r == lo) break;
            if (l >= r) break;
            exch(bolts, l, r);
        }
        return l;
    }

    private void exch(Bolt[] bolts, int l, int r) {
        Bolt tmp = bolts[l];
        bolts[l] = bolts[r];
        bolts[r] = tmp;
    }

    private int floor(Bolt[] b, Nut nut) {
        return 0;
    }

    private int ceil(Bolt[] b, Nut nut) {
        return 0;
    }


    /*
    Question 2
    Selection in two sorted arrays.
    Given two sorted arrays a[] and b[], of sizes N1 and N2, respectively,
    design an algorithm to find the kth largest key.
    The order of growth of the worst case running time of your algorithm should be logN, where N=N1+N2.
    Version 1: N1=N2 and k=N/2
    Version 2: k=N/2
    Version 3: no restrictions
     */
    int MAX = Integer.MAX_VALUE;
    int MIN = Integer.MIN_VALUE;

    public int select(int[] a, int ah, int[] b, int bh, int k) {
        int n1 = a.length - ah;
        int n2 = b.length - bh;
        int i = ah + (int)(double)(n1/(n1 + n2)*(k - 1));
        int j = bh + k - i - 1;
        int ai = i == n1 ? MAX : a[i];
        int bj = j == n2 ? MAX : b[j];
        int ai1 = i == 0 ? MIN : a[i - 1];
        int bj1 = j == 0 ? MIN : b[j - 1];

        if (ai > bj1 && ai < bj) return ai;
        else if (bj > ai1 && bj < ai) return bj;
        else if (ai < bj1) return select(a, i + 1, b, bh, k - i - 1);
        else return select(a, ah, b, j + 1, k - j - 1);
    }

    /*
    Question 3
    Decimal dominants.
    Given an array with N keys, design an algorithm to find all values that occur more than N/10 times.
    The expected running time of your algorithm should be linear.
     */

    class DecimalDominants {
        private TreeMap<Integer, Integer> counts;
        private int K;
        private int N;
        private int[] A;

        public DecimalDominants(int[] a, int k) {
            A = a;
            N = a.length;
            K = k;

            buildCounts(a);
        }

        private void buildCounts(int[] a) {
            for (int i = 0; i < N; i++) {
                if (counts.containsKey(i)) counts.put(i, counts.get(i) + 1);
                else counts.put(i, 1);
                if (counts.keySet().size() >= K) removeCounts();
            }
        }

        private void removeCounts() {
            for (int k : counts.keySet()) {
                int c = counts.get(k);
                if (c > 1) counts.put(k, c - 1);
                else counts.remove(k);
            }
        }

        public Iterable<Integer> find() {
            Bag<Integer> result = new Bag<Integer>();
            for (int k : counts.keySet()) {
                if (count(k) > N/K) result.add(k);
            }
            return result;
        }

        private int count(int k) {
            int count = 0;
            for (int i = 0; i < N; i++) {
                if (A[i] == k) count++;
            }
            return count;
        }
    }


}
