package assignments;

import algs4.MinPQ;
import algs4.Stack;
import algs4.SymbolDigraph;
import stdlib.In;
import stdlib.StdOut;

/**
 * Created by Leon on 7/20/15.
 */
public class Solver {
    private class SearchNode implements Comparable<SearchNode>{
        private Board board;
        private SearchNode previous;
        private int moves;

        SearchNode(Board board) {
            this.board = board;
            this.previous = null;
            this.moves = 0;
        }

        public int compareTo(SearchNode that) {
            if (this.board.manhattan() + this.moves > that.board.manhattan() + that.moves) return 1;
            if (this.board.manhattan() + this.moves < that.board.manhattan() + that.moves) return -1;
            return 0;
        }
    }

    private SearchNode solutionNode;
    private SearchNode twinsolutionNode;

    public boolean isSolvable() {
        return moves() >= 0;
    }

    public int moves() {
        return solutionNode.moves;
    }

    public Iterable<Board> solution() {
        if (!isSolvable()) return null;

        Stack<Board> steps = new Stack<Board>();

        while (solutionNode != null) {
            steps.push(solutionNode.board);
            solutionNode = solutionNode.previous;
        }

        return steps;
    }

    public Solver(Board initial) {
        if (initial == null) throw new NullPointerException("Please initialize the board!");

        solutionNode = new SearchNode(initial);
        twinsolutionNode = new SearchNode(initial.twin());

        MinPQ<SearchNode> solutionQueue = new MinPQ<SearchNode>();
        MinPQ<SearchNode> twinsolutionQueue = new MinPQ<SearchNode>();

        solutionQueue.insert(solutionNode);
        twinsolutionQueue.insert(twinsolutionNode);

        while (true) {
            solutionNode = solutionQueue.delMin();
            twinsolutionNode = twinsolutionQueue.delMin();

            if (twinsolutionNode.board.isGoal()) {
                solutionNode.moves = -1;
                solutionNode.previous = null;
                return;
            }

            if (solutionNode.board.isGoal()){
                return;
            }

            addSearchNodes(solutionQueue, solutionNode);
            addSearchNodes(twinsolutionQueue, twinsolutionNode);
        }
    }

    private void addSearchNodes(MinPQ<SearchNode> q, SearchNode node) {
        assert node.board != null;

        for (Board b: node.board.neighbors()) {
            if (!b.equals(node.board)) {
                SearchNode candidate = new SearchNode(b);
                candidate.previous = node;
                candidate.moves = node.moves + 1;
                q.insert(candidate);
            }
        }
    }

    public static void main(String[] args) {

        // create initial board from file
        In in = new In("./resources/8puzzle/puzzle2x2-unsolvable2.txt");
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        //System.out.println(initial.isGoal());

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
