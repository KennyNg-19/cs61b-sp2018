package hw2;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;
public class PercolationStats {
    private double[] result;
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException("N and T should be a positive integer");
        }
        result = new double[T];
        for (int i = 0; i < T; i++) {
            Percolation per;
            per = pf.make(N);
            while (!per.percolates()) {
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                if (!per.isOpen(row, col)) {
                    per.open(row, col);
                }
                if (per.percolates()) {
                    break;
                }
            }
            result[i] = (float) per.numberOfOpenSites() / (N * N);
        }

    }

    public double mean() {
        return StdStats.mean(result);

    }

    public double stddev() {

        return StdStats.stddev(result);

    }

    public double confidenceLow() {
        double meanVal = mean();
        double devVal = stddev();
        return meanVal - 1.96 * devVal / Math.sqrt(result.length);
    }

    public double confidenceHigh() {
        double meanVal = mean();
        double devVal = stddev();
        return meanVal + 1.96 * devVal / Math.sqrt(result.length);
    }

    public static void main(String[] strings) {
        PercolationFactory pf = new PercolationFactory();
        PercolationStats p = new PercolationStats(100, 100, pf);
        System.out.println(p.mean());
    }
}
