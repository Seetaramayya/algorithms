package tutorial.java.part1.assignment.percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final double[] thresholds;
    private final int trails;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        this.thresholds = new double[trials];
        this.trails = trials;
        for (int i = 0; i < trials; i++) {
            thresholds[i] = calculatePercolationThreshold(n);
        }
    }

    private double calculatePercolationThreshold(int n) {
        Percolation percolation = new Percolation(n);
        while (!percolation.percolates()) {
            percolation.open(random(n), random(n));
        }
        return percolation.numberOfOpenSites() / (double) (n * n);
    }

    private int random(int n) {
        int random = StdRandom.uniform(n + 1);
        return random == 0 ? 1 : random;
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (CONFIDENCE_95 * stddev() / Math.sqrt(trails));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (CONFIDENCE_95 * stddev() / Math.sqrt(trails));
    }

    // test client (see below)
    public static void main(String[] args) {
        int n;
        int trails;
        if (args.length != 2) {
            throw new IllegalArgumentException("Usage is wrong, expected 2 arguments but not provided. Usage <n> <trails>");
        }
        n = Integer.parseInt(args[0]);
        trails = Integer.parseInt(args[1]);

        if (n <= 0 || trails <= 0) {
            throw new IllegalArgumentException("n and trails must be non zero positive number");
        }
        PercolationStats stats = new PercolationStats(n, trails);
        /*
         *  ===============================================================
         *  |    N           |     trails         |   Time in seconds
         *  ===============================================================
         *  |   10           |     120            |  0.038                |
         *  |   100          |     120            |  0.154                |
         *  |   500          |     120            |  2.342                |
         *  |   1000         |     120            | 18.574                |
         *  |   10000        |     1              | 36.762                |
         *  |   500          |     240            |  4.385                |
         *  |   10           |     120            |                       |
         *  |   10           |     120            |                       |
         *  |   10           |     120            |                       |
         *  ===============================================================
         */
        System.out.printf("mean                    = %.16f\n", stats.mean());
        System.out.printf("stddev                  = %.16f\n", stats.stddev());
        System.out.printf("95%% confidence interval = [%.16f, %.16f]\n", stats.confidenceLo(), stats.confidenceHi());
    }

}
