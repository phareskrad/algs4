package assignments;

import algs4.Bag;
import algs4.Stack;
import stdlib.In;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Leon on 8/19/15.
 */
public class BoggleSolver {
    private HashSet<String> dictionary;

    private static final int R = 26;
    private static final int OFFSET = 65;
    //data structure to store dictionary
    private Node root;
    //data structure Node
    private class Node {
        private Node[] next = new Node[R];
        private boolean isString;
    }
    //add key in data structure
    private void addNode(String key) { root = addNode(root, key, 0);}
    //helper function for adding key
    private Node addNode(Node x, String key, int d) {
        if (x == null) x = new Node();
        if (d == key.length()) {
            if (d > 2)
                x.isString = true;
        }
        else {
            char c = key.charAt(d);
            x.next[c - OFFSET] = addNode(x.next[c - OFFSET], key, d + 1);
        }
        return x;
    }
    //prefix query, given a node
    private Node queryNext(Node node, char next) {
        return node.next[next - OFFSET];
    }
    //DEBUG function
    private boolean contains(String key) {
        Node x = get(root, key, 0);
        if (x == null) return false;
        return x.isString;
    }
    //DEBUG function
    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) return x;
        char c = key.charAt(d);
        return get(x.next[c - OFFSET], key, d+1);
    }

    //data structure to store board graph
    private char[] chars;
    private static Bag<Integer>[] HASBRO;
    private static Bag<Integer>[] adjs;
    private int V;
    //initialize board graph given a board
    private void initializeBoardGraph(BoggleBoard board) {
        int row = board.rows();
        int col = board.cols();
        V = row*col;
        chars = new char[V];
        if (row == 4 && col == 4) this.adjs = HASBRO;
        else {
            adjs = (Bag<Integer>[]) new Bag[row*col];
            for (int v = 0; v < V; v++) {
                adjs[v] = new Bag<Integer>();
                addAdj(adjs, v, col, row);
            }
        }

        for (int v = 0; v < V; v++) {
            chars[v] = board.getLetter(v/col, v%col);
        }
    }
    //initialize a 4x4 hasbro board
    private void initializeHASBROGraph() {
        HASBRO = (Bag<Integer>[]) new Bag[16];
        for (int v = 0; v < 16; v++) {
            HASBRO[v] = new Bag<Integer>();
            addAdj(HASBRO, v, 4, 4);
        }
    }
    //helper function for adding all adjacent vertices given one vertex
    private void addAdj(Bag<Integer>[] adj,int v, int col, int row) {
        if (v % col > 0) {
            addEdge(adj, v, v - 1);
            if (v / col > 0) addEdge(adj, v, v - col - 1);
            if (v / col < row - 1) addEdge(adj, v, v + col - 1);
        }
        if (v % col < col - 1) {
            addEdge(adj, v, v + 1);
            if (v / col > 0) addEdge(adj, v, v - col + 1);
            if (v / col < row - 1) addEdge(adj, v, v + col + 1);
        }
        if (v / col > 0) addEdge(adj, v, v - col);
        if (v / col < row - 1) addEdge(adj, v, v + col);
    }
    //helper function for adding one edge
    private void addEdge(Bag<Integer>[] adj, int v, int w) {
        adj[v].add(w);
    }
    //BoardGraph class, for performance test, not used
    private static class BoardGraph {
        private Bag<Integer>[] adj;
        private int V;
        private BoggleBoard board;
        private int col;
        private int row;

        public BoardGraph(BoggleBoard board) {
            this.board = board;
            this.col = board.cols();
            this.row = board.rows();
            this.V = col * row;
            adj = (Bag<Integer>[]) new Bag[V];
            addAllEdge(board);
        }

        public Iterable<Integer> adj(int v) {
            return adj[v];
        }

        public char getLetter(int v) {
            int r = v / col;
            int c = v % col;
            return board.getLetter(r, c);
        }

        public int V() {return V;}

        private void addAllEdge(BoggleBoard board) {
            for (int v = 0; v < row*col; v++) {
                adj[v] = new Bag<Integer>();
                addAdj(v);
            }
        }

        private void addAdj(int v) {
            if (v % col > 0) {
                addEdge(v, v - 1);
                if (v / col > 0) addEdge(v, v - col - 1);
                if (v / col < row - 1) addEdge(v, v + col - 1);
            }
            if (v % col < col - 1) {
                addEdge(v, v + 1);
                if (v / col > 0) addEdge(v, v - col + 1);
                if (v / col < row - 1) addEdge(v, v + col + 1);
            }
            if (v / col > 0) addEdge(v, v - col);
            if (v / col < row - 1) addEdge(v, v + col);
        }

        private void addEdge(int v, int w) {
            adj[v].add(w);
        }
        //DEBUG
        public String toString() {
            StringBuilder s = new StringBuilder();
            String NEWLINE = System.getProperty("line.separator");
            for (int v = 0; v < V; v++) {
                s.append(getLetter(v) + ": ");
                for (int w : adj[v]) {
                    s.append(getLetter(w) + " ");
                }
                s.append(NEWLINE);
            }
            return s.toString();
        }
    }
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        initialize(dictionary);
        initializeHASBROGraph();
    }
    //helper function for initializing BoggleSolver
    private void initialize(String[] dictionary) {
        this.dictionary = new HashSet<String>();
        for (String d : dictionary) {
            this.dictionary.add(d);
            addNode(d);
        }
    }
    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        initializeBoardGraph(board);
        Set<String> result = new HashSet<String>();
        for (int v = 0; v < V; v++) {
            dfs(v, result);
        }
        return result;
    }
    //DEBUG function
    private Iterable<String> getValidWords(BoggleBoard board, int v) {
        long startTime = System.currentTimeMillis();
        BoardGraph bg = new BoardGraph(board);
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println(totalTime / 1000d);
        Set<String> result = new HashSet<String>();
        dfs(v, result);

        return result;
    }
    //helper function for dfs and adding result
    private void dfs(int s, Set<String> result) {
        //check first char, if not found in trie, return
        char start = chars[s];
        Node first = queryNext(root, start);
        if (first == null) return;
        //initialize iterator for each vertex in bg
        Iterator<Integer>[] adj = (Iterator<Integer>[]) new Iterator[V];
        for (int v = 0; v < V; v++)
            adj[v] = adjs[v].iterator();
        //mark visited vertices to avoid cycle
        boolean[] onTrack = new boolean[V];
        onTrack[s] = true;
        //track visited trie node
        Stack<Node> nodes = new Stack<Node>();
        nodes.push(first);
        //track visited vertices
        Stack<Integer> visited = new Stack<Integer>();
        visited.push(s);
        //track visited prefix
        StringBuilder prefix = new StringBuilder(Character.toString(start));

        //depth first search
        while (!visited.isEmpty()) {
            int v = visited.peek();
            Node n = nodes.peek();
            if (n != null && n.isString)
                result.add(prefix.toString());

            if (adj[v].hasNext() && n != null) { //if prefix node exists and vertex has adjacent vertices to go
                int w = adj[v].next();
                char cw = chars[w];
                if (!onTrack[w]) {
                    onTrack[w] = true;
                    visited.push(w);
                    nodes.push(queryNext(n, cw));
                    prefix.append(cw);
                }
            }
            else {
                visited.pop();
                nodes.pop();
                onTrack[v] = false;
                adj[v] = adjs[v].iterator(); //reset iterator
                prefix.setLength(prefix.length() - 1); //remove last char from prefix
            }
        }

    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        int len = word.length();
        if (dictionary.contains(word)) {
            return scoreOf(len);
        }
        return 0;
    }
    //helper function for computing score
    private int scoreOf(int len) {
        if (len < 3) return 0;
        else if (len < 5) return 1;
        else if (len == 5) return 2;
        else if (len == 6) return 3;
        else if (len == 7) return 5;
        else return 11;
    }

    public static void main(String[] args) {
        //char[][] a =  {
        //        { 'D', 'O', 'T', 'Y' },
        //        { 'T', 'R', 'S', 'F' },
        //        { 'M', 'X', 'M', 'O' },
        //        { 'Z', 'A', 'B', 'W' }
        //};

        BoggleBoard[] board = new BoggleBoard[1000];
        for (int i = 0; i < 1000; i++) {
            board[i] = new BoggleBoard();
        }
        //StdOut.println(board);
        //BoardGraph bg = new BoardGraph(board);
        //StdOut.println(bg);

        //String[] dictionary = {"DOT", "ROT", "ZAXMOFYSORT", "BA", "SF", "FS"};
        In dictIn = new In("./resources/boggle/dictionary-algs4.txt");
        String[] dictionary = dictIn.readAllStrings();
        BoggleSolver bs = new BoggleSolver(dictionary);
        //System.out.println(bs.contains("TEAM"));
        long startTime = System.nanoTime();
        for (BoggleBoard b : board) {
            bs.getAllValidWords(b);
        }
        //Iterable<String> result = bs.getAllValidWords(board);
        //for (String s : result) {
            //System.out.print(bs.scoreOf(s));
        //    System.out.println(" " + s);
        //}
        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println(totalTime / 1000000d);
    }
}
