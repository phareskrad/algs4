package jobinterviewquestions;

import stdlib.StdRandom;

/**
 * Created by Leon on 7/12/15.
 */
public class Mergesort {
    /*
    Question 1
    Merging with smaller auxiliary array.
    Suppose that the subarray a[0] to a[N-1] is sorted and the subarray a[N] to a[2*N-1] is sorted.
    How can you merge the two subarrays so that a[0] to a[2*N-1] is sorted using an auxiliary array of size N (instead of 2N)?
     */
    private boolean less(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }

    public void mergeWithSmaller(Comparable[] a, Comparable[] aux) {
        int N = aux.length;
        assert a.length == 2*N;

        for (int i = 0; i < N; i++) {
            aux[i] = a[i];
        }

        int l = 0;
        int r = N;

        int i = 0;
        for (; i < N; i++) {
            if (less(aux[l], a[r])) a[i] = aux[l++];
            else a[i] = a[r++];
        }

        while (l < N) {
            if (r >= 2*N || less(aux[l], a[r]) ) a[i++] = aux[l++];
            else a[i++] = a[r++];
        }
    }

    /*
    Question 2
    Counting inversions.
    An inversion in an array a[] is a pair of entries a[i] and a[j] such that i<j but a[i]>a[j].
    Given an array, design a linearithmic algorithm to count the number of inversions.
     */

    private int merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }

        int k = lo;
        int i = lo;
        int j = mid + 1;
        int count = 0;

        while (k < hi) {
            if (i > mid) a[k++] = aux[j++];
            else if (j > hi) a[k++] = aux[i++];
            else if (less(aux[j], aux[i])) {
                count += mid + 1 - i;
                a[k++] = aux[j++];
            }
            else a[k++] = aux[i++];
        }
        return count;
    }

    public int countInversion(Comparable[] a) {
        Comparable[] aux = new Comparable[a.length];
        int count = 0;

        for (int sz = 1; sz < a.length; sz += sz) {
            for (int i = 0; i < a.length - sz; i += 2*sz) {
                int lo = i;
                int m = i + sz - 1;
                int hi = Math.min(i + 2*sz - 1, a.length - 1);
                count += merge(a, aux, lo, m, hi);
            }
        }
        return count;
    }


    /*
    Question 3
    Shuffling a linked list.
    Given a singly-linked list containing N items, rearrange the items uniformly at random.
    Your algorithm should consume a logarithmic (or constant) amount of extra memory and run in time proportional to NlogN in the worst case.
     */

    private class Node {
        Object item;
        Node next;
    }

    private void merge(Node lh, Node rh) {
        Node left = lh;
        Node right = rh;

        if (StdRandom.uniform(1) > 0) {
            lh = right;
            right = right.next;
        }
        else {
            left = left.next;
        }

        Node runner = lh;

        while (right != null || left != null) {
            if (left == null) {
                runner.next = right;
                right =right.next;
            }
            else if (right == null) {
                runner.next = left;
                left = left.next;
            }
            else if (StdRandom.uniform(1) > 0) {
                runner.next = right;
                right = right.next;
            }
            else {
                runner.next = left;
                left = left.next;
            }
            runner = runner.next;
        }
    }

    public void shuffle(Node head, int N) {
        if (N == 1) return;

        int k = 1;
        Node mid = head;
        while (k < N / 2) {
            mid = mid.next;
            k++;
        }
        Node rh = mid.next;
        mid.next = null;
        shuffle(head, N / 2);
        shuffle(rh, N - N / 2);
        merge(head, rh);
    }


}
