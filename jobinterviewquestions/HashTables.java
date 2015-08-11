package jobinterviewquestions;


import algs4.Vector;

import java.util.HashSet;

/**
 * Created by Leon on 7/27/15.
 */
public class HashTables {
    /*
    Question 1
    4-SUM.
    Given an array a[] of N integers,
    the 4-SUM problem is to determine if there exist distinct indices i, j, k, and l such that a[i]+a[j]=a[k]+a[l].
    Design an algorithm for the 4-SUM problem that takes time proportional to N2 (under suitable technical assumptions).
     */

    public static boolean fourSum(int[] a) {
        int n = a.length;
        assert n >= 4;
        HashSet<Integer> s = new HashSet<Integer>();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int tmp = a[i] + a[j];
                if (s.contains(tmp)) {
                    System.out.println(tmp);
                    return true;
                }
                else s.add(tmp);
            }
        }
        return false;
    }

    /*
    Question 2
    Hashing with wrong hashCode() or equals().
    Suppose that you implement a data type OlympicAthlete for use in a java.util.HashMap.
    Describe what happens if you override hashCode() but not equals().
    Describe what happens if you override equals() but not hashCode().
    Describe what happens if you override hashCode() but implement public boolean equals(OlympicAthlete that)
    instead of public boolean equals(Object that).
     */

    /*
    1. Same athelete will be hashed into one bucket, but you can't find the match through search
    2. Same athelete will not be hashed into one bucket
    3.
     */

    public static void main(String[] args) {
        int[] a = {1,3,4,5,6,7,8,9};
        System.out.println(fourSum(a));
    }
}
