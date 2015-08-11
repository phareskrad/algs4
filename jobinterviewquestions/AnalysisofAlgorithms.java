package jobinterviewquestions;

import java.util.Arrays;

/**
 * Created by Leon on 6/30/15.
 */
public class AnalysisofAlgorithms {
    /*
    Question 1
    3-SUM in quadratic time. Design an algorithm for the 3-SUM problem that takes time proportional to N2 in the worst case.
    You may assume that you can sort the N integers in time proportional to N2 or better.
     */

    private boolean sum(int[] a, int key) {
        int left = 0;
        int right = a.length - 1;
        int value = a[key];
        while (left < right) {
            if (left == key || a[left] + a[right] < value) left++;
            else if (right == key || a[left] + a[right] > value) right--;
            else return true;
        }
        return false;
    }

    public boolean threesum(int[] a) {
        Arrays.sort(a);

        for (int i = 0; i < a.length; i++) {
            if (sum(a, i)) return true;
        }
        return false;
    }

    /*
    Question 2
    Search in a bitonic array.
    An array is bitonic if it is comprised of an increasing sequence of integers followed immediately by a decreasing sequence of integers.
    Write a program that, given a bitonic array of N distinct integer values, determines whether a given integer is in the array.
    Standard version: Use ∼3lgN compares in the worst case.
    Signing bonus: Use ∼2lgN compares in the worst case (and prove that no algorithm can guarantee to perform fewer than ∼2lgN compares in the worst case).
     */
    private boolean binsearch(int[] a, int left, int right, int key) {
        if (a[left] < a[right]) {
            while (left < right) {
                int mid = left + (right - left) / 2;
                if (a[mid] < key) left = mid + 1;
                else if (a[mid] > key) right = mid - 1;
                else return true;
            }
        }
        else {
            while (left < right) {
                int mid = left + (right - left) / 2;
                if (a[mid] < key) right = mid - 1;
                else if (a[mid] > key) left = mid + 1;
                else return true;
            }
        }
        return false;
    }

    public boolean serarch(int[] a, int key) {
        int left = 0;
        int right = a.length - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (a[mid] < key) {
                if (a[mid] < a[mid + 1]) left = mid + 1;
                else right = mid - 1;
            }
            else if (a[mid] > key) {
                if (a[mid] < a[mid + 1]) {
                    if (binsearch(a, 0, mid - 1, key)) return true;
                    else left = mid + 1;
                }
                else {
                    if (binsearch(a, mid + 1, a.length - 1, key)) return true;
                    else right = mid - 1;
                }
            }
            else return true;
        }
        return false;
    }

    /*
    Egg drop. Suppose that you have an N-story building (with floors 1 through N) and plenty of eggs.
    An egg breaks if it is dropped from floor T or higher and does not break otherwise.
    Your goal is to devise a strategy to determine the value of T given the following limitations on the number of eggs and tosses:
    Version 0: 1 egg, ≤T tosses.
    Version 1: ∼1lgN eggs and ∼1lgN tosses.
    Version 2: ∼lgT eggs and ∼2lgT tosses.
    Version 3: 2 eggs and ∼2N‾‾√ tosses.
    Version 4: 2 eggs and ≤cT‾‾√ tosses for some fixed constant c.
     */


}
