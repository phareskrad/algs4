package assignments;

import stdlib.BinaryStdIn;
import stdlib.BinaryStdOut;
import stdlib.StdIn;
import stdlib.StdOut;

/**
 * Created by Leon on 8/25/15.
 */
public class BurrowsWheeler {
    private static final int R = 256;
    // apply Burrows-Wheeler encoding, reading from standard input and writing to standard output
    public static void encode() {
        String s = BinaryStdIn.readString();
        CircularSuffixArray csa = new CircularSuffixArray(s);
        int n = csa.length();
        int m = s.length();

        for (int i = 0; i < n; i++) {
            if (csa.index(i) == 0) {
                BinaryStdOut.write(i);
                break;
            }
        }

        for (int i = 0; i < n; i++) {
            int index = csa.index(i);
            BinaryStdOut.write(s.charAt((index + m) % m));
        }
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler decoding, reading from standard input and writing to standard output
    public static void decode() {
        int first =  BinaryStdIn.readInt();
        char[] t = BinaryStdIn.readString().toCharArray();

        int[] count = new int[R+1];
        int[] next = new int[t.length];

        for (int i = 0; i < t.length; i++)
            count[t[i] + 1]++;

        for (int r = 0; r < R; r++)
            count[r+1] += count[r];

        for (int i = 0; i < t.length; i++)
            next[count[t[i]]++] = i;

        int p = first;
        for (int i = 0; i < t.length; i++) {
            BinaryStdOut.write(t[p]);
            p = next[first];
        }
        BinaryStdOut.close();
    }

    // if args[0] is '-', apply Burrows-Wheeler encoding
    // if args[0] is '+', apply Burrows-Wheeler decoding
    public static void main(String[] args) {
        if (args[0].equals("-")) encode();
        else if (args[0].equals("+")) decode();
        else throw new IllegalArgumentException("Please use - and + to specify encoding or decoding!");
    }
}
