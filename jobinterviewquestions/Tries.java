package jobinterviewquestions;

/**
 * Created by Leon on 8/18/15.
 */
public class Tries {
    /*
    Question 1
    Prefix free codes.
    In data compression, a set of binary strings is prefix free if no string is a prefix of another.
    For example, {01,10,0010,1111} is prefix free, but {01,10,0010,10100} is not because 10 is a prefix of 10100.
    Design an efficient algorithm to determine if a set of binary strings is prefix-free.
    The running time of your algorithm should be proportional the number of bits in all of the binary stings.
    */
    static class Prefixfree {

        private Node root;
        private int N;

        private class Node {
            private Node[] next = new Node[2];
            private boolean isString;
        }

        private Node add(Node x, String binary, int d) {
            if (x == null) x = new Node();
            if (d == binary.length()) {
                if (x.next[0] != null || x.next[1] != null) throw new IllegalArgumentException("This input is a prefix of existing binary!");
                x.isString = true;
                N++;
            } else {
                if (x.isString) throw new IllegalArgumentException("Prefix exists for this input!");
                int c = (int)binary.charAt(d) - 48;
                x.next[c] = add(x.next[c], binary, d + 1);
            }
            return x;
        }

        public void add(String binary) {
            root = add(root, binary, 0);
        }

        public int size() {
            return N;
        }
    }

    /*
    Question 2
    Boggle.
    Boggle is a word game played on an 4-by-4 grid of tiles, where each tile contains one letter in the alphabet.
    The goal is to find all words in the dictionary that can be made by following a path of adjacent tiles (with no tile repeated),
    where two tiles are adjacent if they are horizontal, vertical, or diagonal neighbors.
    */

    /*
    programming assignment
     */

    /*
    Question 3
    Suffix trees. Learn about and implement suffix trees, the ultimate string searching data structure.
     */

    public static void main(String[] args) {
        Prefixfree pf = new Prefixfree();

        pf.add("0100");
        pf.add("01");

        pf.add("01");
        pf.add("0100");

    }
}
