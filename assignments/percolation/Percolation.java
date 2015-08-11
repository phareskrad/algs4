package assignments;
import algs4.WeightedQuickUnionUF;
/**
 * Created by Leon on 5/25/15.
 */
public class Percolation {
    private WeightedQuickUnionUF grid;
    private boolean[] state;
    private int N;

    public Percolation(int n) throws IllegalArgumentException{
        N = n;
        if(N <= 0) throw new IllegalArgumentException("Please specify a positive integer to initialize the grid!");

        int gridN = N * N + 2;
        grid = new WeightedQuickUnionUF(gridN);
        state = new boolean[gridN];

        for(int i = 1; i <= N; i++) {
            grid.union(0, i);
            grid.union(gridN - 1, gridN - 1 - i);
        }
    }

    private int getIndex(int i , int j) {
      return (i - 1) * N + j;
    }

    private void connectLeft(int index) {
        if(index % N == 1 || !state[index - 1]) return;
        else grid.union(index, index - 1);
    }

    private void connectRight(int index) {
        if(index % N == 0 || !state[index + 1]) return;
        else grid.union(index, index + 1);
    }

    private void connectUp(int index) {
        if(index <= N || !state[index - N]) return;
        else grid.union(index, index - N);
    }
    private void connectDown(int index) {
        if(index > N * N - N || !state[index + N]) return;
        else grid.union(index, index + N);
    }

    public void open(int i, int j) throws IndexOutOfBoundsException{
        if (i < 1 || j < 1 || i > N || j > N) throw new IndexOutOfBoundsException("Grid is N by N!");
        int index = getIndex(i, j);
        state[index] = true;
        connectLeft(index);
        connectRight(index);
        connectUp(index);
        connectDown(index);
    }

    public boolean isOpen(int i, int j) {
        int index = getIndex(i, j);
        return state[index];
    }

    public boolean isFull(int i, int j) {
        int index = getIndex(i, j);
        return state[index] && grid.connected(index, 0);
    }
    public boolean isPercolated() {
        return grid.connected(0, state.length - 1);
    }

    public int count() {
        int c = 0;
        for (int i = 1; i <= N * N; i++) {
            if (state[i]) c += 1;
        }
        return c;
    }

    public static void main(String[] args) {
        Percolation perc = new Percolation(100);
        perc.open(1,1);
        perc.open(2,1);
        perc.open(3,2);
        perc.open(2,2);
        System.out.print(perc.isFull(3,2));
        System.out.print(perc.count());
    }
}
