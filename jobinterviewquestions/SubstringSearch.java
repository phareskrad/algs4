package jobinterviewquestions;


import algs4.Queue;

/**
 * Created by Leon on 8/18/15.
 */
public class SubstringSearch {
    /*
    Question 1
    Cyclic rotation of a string.
    A string s is a cyclic rotation of a string t
    if s and t have the same length and s consists of a suffix of t followed by a prefix of t.
    For example, "winterbreak" is a cyclic rotation of "breakwinter" (and vice versa).
    Design a linear-time algorithm to determine whether one string is a cyclic rotation of another.
    */

    public static boolean isCyclicRotation(String s1, String s2) {
        String concat = s1 + s1;
        //use knuth-morris-pratt to create a pattern dfa of s2
        KMP pattern = new KMP(s2);
        return pattern.search(concat) < concat.length();
    }

    static class KMP {
        private int[][] dfa;
        private int R;
        private String pattern;

        KMP(String pattern) {
            this.R = 256;
            this.pattern = pattern;

            int M = pattern.length();
            dfa = new int[R][M];
            dfa[pattern.charAt(0)][0] = 1;

            for (int X = 0, j = 1; j < M; j++) {
                for (int c = 0; c < R; c++) {
                    dfa[c][j] = dfa[c][X];
                }
                dfa[pattern.charAt(j)][j] = j + 1;
                X = dfa[pattern.charAt(j)][X];
            }
        }

        int search(String txt) {
            int M = pattern.length();
            int N = txt.length();
            int i, j;
            for (i = 0, j = 0; i < N && j < M; i++) {
                j = dfa[txt.charAt(i)][j];
            }
            if (j == M) return i - M;
            return N;
        }

        String tendemRepeat(String s) {
            int M = pattern.length();
            int N = s.length();

            Queue<Integer> positions = new Queue<Integer>();

            for (int i = 0, j = 0; i < N; i++) {
                j = dfa[s.charAt(i)][j];
                if (j == M) {
                    positions.enqueue(i - M + 1);
                    j = 0;
                }
            }
            if (positions.isEmpty()) throw new IllegalArgumentException("Input does not contain the base pattern!");
            int max = 1;
            int seq = 1;
            int end = 0;
            int previous = positions.dequeue();
            while (!positions.isEmpty()) {
                int current = positions.dequeue();
                if (current == previous + M) {
                    seq++;
                }
                else {
                    if (seq > max) {
                        max = seq;
                        end = previous + M;
                        seq = 1;
                    }
                }
                previous = current;
            }

            if (seq > max) {
                end = previous + M;
                max = seq;
            }
            if (max == 1) return "Input does not have a tendem repeat!";
            else return s.substring(end - M*max, end);
        }
    }

    /*
    Question 2
    Tandem repeat.
    A tandem repeat of a base string b within a string s
    is a substring of s consisting of at least one consecutive copy of the base string b.
    Given b and s, design an algorithm to find a tandem repeat of b within s of maximum length.
    Your algorithm should run in time proportional to M+N, where M is length of b and N is the length s.

    For example,
    if s is "abcabcababcaba" and b is "abcab", then "abcababcab" is the tandem substring of maximum length (2 copies).
    */

    /*
    see KMP.tendemRepeat
     */

    /*
    Question 3
    Longest palindromic substring.
    Given a string s, find the longest substring that is a palindrome in expected linearithmic time.
    Signing bonus: Do it in linear time in the worst case.
     */

    /*
    for linearithmic algorithm, use karp-rabin to hash substring and reverse substring to find palindrome, then use binary search to find the largest number of substring length
     */

    /*
    linear time algorithm, from leetcode
    */

    //preprocess string, to avoid dealing with odd and even number problem
    private static String preprocess(String s) {
        if (s.length() == 0) return "^$";
        String result = "^";// insert ^ at start
        for (int i = 0; i < s.length(); i++) {
            result = result + "#" + s.substring(i, i + 1);
        }
        result += "#$"; // insert $ at end
        return result;
    }

    public static String longestPalindromeSubstring(String s) {
        String t = preprocess(s);
        int[] p = new int[t.length()];
        int center = 0;
        int right = 0; //the right boundary of previous center
        //first run, create index array p, p[i] is the length / 2 of palindrome that use i as center
        for (int i = 1; i < t.length() - 1; i++) {
            int i_mirror = 2*center - i; //mirror position of current position
            p[i] = (right > i) ? Math.min(right - i, p[i_mirror]) : 0; //initialize p[i] with its mirror position data or boundary limit
            while (t.charAt(i + 1 + p[i]) == t.charAt(i - 1 - p[i])) p[i]++; //try to expand i as center
            if (i + p[i] > right) { //if expand exceed the old right boundary, make i the new center, move right boundary
                center = i;
                right = i + p[i];
            }
        }

        //second run, find the largest p[i], and return substring
        int maxlen = 0;
        int centerindex = 0;
        for (int i = 1; i < p.length - 1; i++) {
            if (p[i] > maxlen) {
                maxlen = p[i];
                centerindex = i;
            }
        }

        return s.substring((centerindex - 1 - maxlen) / 2, (centerindex - 1 + maxlen) / 2);
    }

    public static void main(String[] args) {
        KMP kmp = new KMP("abc");
        System.out.print(kmp.tendemRepeat("abcd"));
    }
}
