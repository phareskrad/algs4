package assignments;

import algs4.Queue;
import algs4.SymbolDigraph;
import stdlib.*;

import java.util.ArrayList;

/**
 * Created by Leon on 8/24/15.
 */
public class MoveToFront {
    private final static int R = 256;
    private static int[] encodings = new int[R];
    private static char[] ranks = new char[R];

    private static void initEncoding() {
        for (int i = 0; i < R; i++) {
            encodings[i] = i;
            ranks[i] = (char) i;
        }
    }

    private static void moveToFront(char c) {
        int current = encodings[c];

        for (int i = current; i > 0; i--) {
            ranks[i] = ranks[i - 1];
            encodings[ranks[i]] = i;
        }
        ranks[0] = c;
        encodings[c] = 0;
    }
    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        initEncoding();

        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            int out = encodings[c];
            BinaryStdOut.write(out);
            moveToFront(c);
        }
        BinaryStdOut.close();
    }
    //DEBUG
    private static void dump(Queue<Integer> q) {
        int BYTES_PER_LINE = 16;
        int i;
        for (i = 0; !q.isEmpty(); i++) {
            if (i == 0) StdOut.printf("");
            else if (i % BYTES_PER_LINE == 0) StdOut.printf("\n", i);
            else StdOut.print(" ");
            StdOut.print(Integer.toHexString(q.dequeue()));
        }
        if (BYTES_PER_LINE != 0) StdOut.println();
        StdOut.println((i * 8) + " bits");
    }
    //DEBUG
    private static void encode(String file) {
        initEncoding();
        BinaryIn bin = new BinaryIn(file);
        Queue<Integer> output = new Queue<Integer>();

        while (!bin.isEmpty()) {
            char c = bin.readChar();
            int out = encodings[c];
            output.enqueue(out);
            moveToFront(c);
        }
        dump(output);
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        initEncoding();

        while (!BinaryStdIn.isEmpty()) {
            int i = BinaryStdIn.readInt();
            char c = ranks[i];
            BinaryStdOut.write(c);
            moveToFront(c);
        }
        BinaryStdOut.close();
    }
    //DEBUG
    private static void decode(boolean debug) {
        if (!debug) return;
        else {
            initEncoding();
            String[] input = StdIn.readAllStrings();

            for (String s : input) {
                int i = Integer.parseInt(s, 16);
                char c = ranks[i];
                System.out.print(Character.toString(c));
                moveToFront(c);
            }
        }
    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("-")) encode();
        else if (args[0].equals("+")) decode();
        else throw new IllegalArgumentException("Please use - and + to specify encoding or decoding!");

        //encode("./resources/burrowswheeler/abra.txt");
        //decode(true);
    }
}
