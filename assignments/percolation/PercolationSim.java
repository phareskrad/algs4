package assignments;
import stdlib.StdRandom;
/**
 * Created by Leon on 5/25/15.
 */
public class PercolationSim {
    private Percolation perc;
    private int N;

    public PercolationSim(int n) throws IllegalArgumentException{
        N = n;
        perc = new Percolation(N);
    }

    private void randomOpen(boolean print) {
        int i = StdRandom.uniform(N) + 1;
        int j = StdRandom.uniform(N) + 1;
        while (perc.isOpen(i, j)) {
            i = StdRandom.uniform(N) + 1;
            j = StdRandom.uniform(N) + 1;
        }
        if (print) System.out.println("Open " + i + "," + j);
        perc.open(i, j);
    }

    public void percolate(boolean print) {
        while (!perc.isPercolated()) {
            randomOpen(print);
        }
    }

    public double estimate(boolean print) {
        percolate(print);
        return (double)(perc.count()) / (double)(N * N);
    }

    public static void main(String[] args) {
        PercolationSim percsim = new PercolationSim(100);
        double result = percsim.estimate(true);
        System.out.println(result);
    }


}
