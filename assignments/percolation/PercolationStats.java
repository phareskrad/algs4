package assignments;
import stdlib.StdStats;
/**
 * Created by Leon on 5/25/15.
 */
public class PercolationStats {
    private double[] results;

    public PercolationStats(int N, int T) throws IllegalArgumentException {
        if (T <= 0) throw new IllegalArgumentException("Please specify a positive integer to carry out the experiments!");
        results = new double[T];
        for (int i = 0; i < T; i++) {
            PercolationSim temp = new PercolationSim(N);
            results[i] = temp.estimate(false);
        }
    }

    public double mean() {
        return StdStats.mean(results);
    }

    public double stddev() {
        return StdStats.stddev(results);
    }

    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(results.length);
    }

    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(results.length);
    }

    public static void main(String[] args) {
        PercolationStats percstats = new PercolationStats(200, 100);
        System.out.println("mean: " + percstats.mean());
        System.out.println("stddev: " + percstats.stddev());
        System.out.println("95% CI: " + percstats.confidenceLo() + "," + percstats.confidenceHi());
    }


}
