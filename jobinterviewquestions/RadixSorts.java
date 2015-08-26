package jobinterviewquestions;

import java.util.Arrays;

/**
 * Created by Leon on 8/12/15.
 */
public class RadixSorts {
    /*
    Question 1
    2-sum.
    Given an array a of N 64-bit integers and a target value T,
    determine whether there are two distinct integers i and j such that ai+aj=T.
    Your algorithm should run in linear time in the worst case.
    */

    /*
    radix sort the array, then two pointers run
     */
    //radix sort for 64 bit int, from algs4
    private static void sort(int[] a) {
        int BITS = 64;
        int W = BITS / 8;
        int R = 1 << 8;
        int MASK = R - 1;

        int N = a.length;
        int[] aux = new int[N];

        for (int d = 0; d < W; d++) {
            int[] count = new int[R+1];
            // compute freq
            for (int i = 0; i < N; i++) {
                int c = (a[i] >> 8*d) & MASK;
                count[c + 1]++;
            }

            // compute cumulates
            for (int r = 0; r < R; r++)
                count[r+1] += count[r];

            // for most significant byte, 0x80-0xFF comes before 0x00-0x7F
            if (d == W-1) {
                int shift1 = count[R] - count[R/2];
                int shift2 = count[R/2];
                for (int r = 0; r < R/2; r++)
                    count[r] += shift1;
                for (int r = R/2; r < R; r++)
                    count[r] -= shift2;
            }

            // move data
            for (int i = 0; i < N; i++) {
                int c = (a[i] >> 8*d) & MASK;
                aux[count[c]++] = a[i];
            }

            // copy back
            for (int i = 0; i < N; i++)
                a[i] = aux[i];
        }
    }

    public boolean twoSum(int[] a, int T) {
        assert a.length > 1;
        sort(a);

        int l = 0;
        int r = a.length - 1;

        while (l < r) {
            if (a[l] + a[r] < T) l++;
            else if (a[l] + a[r] > T) r--;
            else return true;
        }
        return false;
    }

    /*
    Question 2
    American flag sort.
    Given an array with N distinct values between 0 and R−1,
    rearrange them in ascending order in linear time and with extra space at most proportional to R.
    */

    public static void sortR(int[] a, int R) {
        int n = a.length;

        int[] count = new int[R];
        for (int i = 0; i < n; i++) {
            count[a[i]]++;
        }
        int k = 0;
        int r = 0;
        while (k < n) {
            while (count[r]-- > 0) a[k++] = r;
            r++;
        }
    }

    /*
    Question 3
    Cyclic rotations.
    Two strings s and t are cyclic rotations of one another if they have the same length and s consists of a suffix of t followed by a prefix of t.
    For example, "suffixsort" and "sortsuffix" are cyclic rotations.

    Given N distinct strings,
    each of length L,
    design an algorithm to determine whether there exists a pair of distinct strings that are cyclic rotations of one another.
    For example, the following list of N=12 strings of length L=10 contains exactly one pair of strings ("suffixsort" and "sortsuffix")
    that are cyclic rotations of one another.

    algorithms   polynomial   sortsuffix   boyermoore
    structures   minimumcut   suffixsort   stackstack
    binaryheap   digraphdfs   stringsort   digraphbfs
    The order of growth of the running time should be NL2 (or better) in the worst case. Assume that the alphabet size R is a small constant.

    Signing bonus. Do it in NL time in the worst case.
     */
    //substring function make this function n^2
    private static String[] suffixes(String s) {
        int n = s.length();
        String[] suffiexs = new String[n];

        for (int i = 0; i < n; i++) {
            suffiexs[i] = s.substring(i) + s.substring(0, i);
        }
        return suffiexs;
    }
    //explicitly sort the strings
    private static void sort(String[] a, int W) {
        int N = a.length;
        int R = 256;   // extend ASCII alphabet size
        String[] aux = new String[N];

        for (int d = W-1; d >= 0; d--) {
            // sort by key-indexed counting on dth character

            // compute frequency counts
            int[] count = new int[R+1];
            for (int i = 0; i < N; i++)
                count[a[i].charAt(d) + 1]++;

            // compute cumulates
            for (int r = 0; r < R; r++)
                count[r+1] += count[r];

            // move data
            for (int i = 0; i < N; i++)
                aux[count[a[i].charAt(d)]++] = a[i];

            // copy back
            for (int i = 0; i < N; i++)
                a[i] = aux[i];
        }
    }
    //implicitly sort the circular suffixes, keep the sort order in a index array
    private static void sortSuffixes(String s, int[] indices) {
        int N = s.length();
        int R = 256;
        int[] aux = new int[N];

        for (int d = N - 1; d >= 0; d--) {
            int[] count = new int[R+1];

            for (int i = 0; i < N; i++) {
                count[s.charAt((d + indices[i]) % N) + 1]++;
            }

            for (int r = 0; r < R; r++) {
                count[r + 1] += count[r];
            }

            for (int i = 0; i < N; i++) {
                aux[count[s.charAt((d + indices[i]) % N)]++] = indices[i];
            }

            for (int i = 0; i < N; i++)
                indices[i] = aux[i];
        }
    }

    private static int[] initializeIndex(int n) {
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            result[i] = i;
        }
        return result;
    }

    public static boolean pairCyclic(String[] a) {
        int n = a.length;

        String[] fingerprint = new String[n];

        for (int i = 0; i < n; i++) {
            int[] indices = initializeIndex(a[i].length());
            sortSuffixes(a[i], indices);
            String fp = a[i].substring(indices[0]) + a[i].substring(0, indices[0]);
            fingerprint[i] = fp;
        }

        sort(fingerprint, n);

        for (int i = 0; i < n - 1; i++) {
            if (fingerprint[i] == fingerprint[i + 1]) return true;
        }
        return false;
    }

    public static void main(String[] args) {
        int[] a = {1,3,4,4,4,5,6,7,8,3,2,1,3,5,7,89,2,32,0};
        sortR(a, 90);
        System.out.println(Arrays.toString(a));
    }


}
