package assignments;

import algs4.Queue;

import java.util.Arrays;

/**
 * Created by Leon on 7/20/15.
 */
public class Board {
    private int dim;
    private int[][] blocks;

    public Board(int[][] blocks) {
        this.blocks = copyMDArray(blocks);
        dim = blocks.length;
    }

    public int dimension() {
        return dim;
    }

    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (blocks[i][j] != i*dim + j + 1) hamming++;
            }
        }
        return hamming - 1;
    }

    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (blocks[i][j] > 0) {
                    int row = (blocks[i][j] - 1) / dim;
                    int col = (blocks[i][j] - 1) % dim;
                    manhattan += Math.abs(row - i) + Math.abs(col - j);
                }
            }
        }
        return manhattan;
    }

    public boolean isGoal() {
        return hamming() == 0;
    }

    public Board twin() {
        // a board obtained by exchanging two adjacent blocks in the same row

        int[][] blocksCopy = copyMDArray(blocks);
        if (blocksCopy.length <= 1) {
            return new Board(blocksCopy);
        }

        int row = 0;
        int col = 0;
        int value = 0;
        int lastValue = blocksCopy[0][0];

        for (row = 0; row < blocksCopy.length; row++) {
            for (col = 0; col < blocksCopy.length; col++) {
                value = blocksCopy[row][col];
                if (value != 0 && lastValue != 0 && col > 0) {
                    blocksCopy[row][col] = lastValue;
                    blocksCopy[row][col - 1] = value;
                    return new Board(blocksCopy);
                }
                lastValue = value;
            }
        }

        return null;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Board))
            return false;
        Board other = (Board) obj;
        if (!Arrays.deepEquals(this.blocks, other.blocks))
            return false;
        return true;
    }

    public Iterable<Board> neighbors() {

        // neighbors are all boards that can be reached in ONE move
        // this means

        // use queue -- FIFO, and add boards to it in order
        Queue<Board> queue = new Queue<Board>();

        int[][] zero = findEmpty();
        int row = zero[0][0];
        int col = zero[0][1];

        // if NOT the top row, swap with one above
        if (row > 0) {
            int[][] blocksCopy = copyMDArray(blocks);
            swap(blocksCopy, row, col, row - 1, col);
            queue.enqueue(new Board(blocksCopy));
        }

        // if NOT the bottom row, swap with one below
        if (row < blocks.length - 1) {
            int[][] blocksCopy = copyMDArray(blocks);
            swap(blocksCopy, row, col, row + 1, col);
            queue.enqueue(new Board(blocksCopy));
        }

        // if NOT the far left col, swap with left
        if (col > 0) {
            int[][] blocksCopy = copyMDArray(blocks);
            swap(blocksCopy, row, col, row, col - 1);
            queue.enqueue(new Board(blocksCopy));
        }

        // if NOT the far right col, swap with right
        if (col < blocks.length - 1) {
            int[][] blocksCopy = copyMDArray(blocks);
            swap(blocksCopy, row, col, row, col + 1);
            queue.enqueue(new Board(blocksCopy));
        }

        return queue;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(blocks.length + "\n");
        sb.append(toString2D(blocks));
        return sb.toString();
    }

    private static int[][] copyMDArray(int[][] input) {
        int[][] output = new int[input.length][input[0].length];
        for (int i = 0; i < input.length; i++) {
            output[i] = Arrays.copyOf(input[i], input[i].length);
        }
        return output;
    }

    private int[][] findEmpty() {
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                if (blocks[i][j] == 0) {
                    return new int[][] { { i, j } };
                }
            }
        }
        return null;
    }

    private static void swap(int[][] input, int row, int col, int toRow, int toCol) {
        int first = input[row][col];
        int second = input[toRow][toCol];
        input[row][col] = second;
        input[toRow][toCol] = first;
    }

    // need this util because System.arrayCopy and Arrays.copyOf
    // will get this wrong and use same pointers
    // (a 2D array is a 1D array of 1D arrays, etc)
    private static String toString2D(int[][] input) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input.length; j++) {
                int digit = input[i][j];
                sb.append(" " + digit + " ");
            }
            if (i < input.length - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }




}
