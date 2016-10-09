import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

  private static final double PERCENTILE_POINT_975 = 1.96;
  private static final int SUFFICIENT_TRIALS = 30;

  private double[] openSitesRatio;
  private int size;

  /**
   * perform trials independent experiments on an n-by-n grid
   */
  public PercolationStats(int n, int trials) {
    if (n <= 0)
      throw new IllegalArgumentException("Grid size must be positive");
    if (trials <= 0)
      throw new IllegalArgumentException("Number of trials must be positive");

    size = n;
    openSitesRatio = new double[trials];
    while (trials-- > 0)
      openSitesRatio[trials] = runExperiment();
  }
  
  private double runExperiment() {
    Percolation percolation = new Percolation(size);
    int openedSites = 0;
    while (!percolation.percolates()) {
      int randomRow = StdRandom.uniform(1, size+1);
      int randomCol = StdRandom.uniform(1, size+1);
      if (!percolation.isOpen(randomRow, randomCol)) {
        percolation.open(randomRow, randomCol);
        openedSites++;
      }
    }
    return (double) openedSites / (size*size);
  }

  /**
   * sample mean of percolation threshold
   */
  public double mean() {
    return StdStats.mean(openSitesRatio);
  }

  /**
   * sample standard deviation of percolation threshold
   */
  public double stddev() {
    return StdStats.stddev(openSitesRatio);
  }

  /**
   * low endpoint of 95% confidence interval
   */
  public double confidenceLo() {
    return mean() - ((PERCENTILE_POINT_975 * stddev()) / Math.sqrt(getTrials()));
  }

  /**
   * high endpoint of 95% confidence interval
   */
  public double confidenceHi() {
    return mean() + ((PERCENTILE_POINT_975 * stddev()) / Math.sqrt(getTrials()));
  }

  private int getTrials() {
    return openSitesRatio.length;
  }

  public static void main(String[] args) {
    int size = Integer.parseInt(args[0]);
    int trials = Integer.parseInt(args[1]);
    PercolationStats percolationStats = new PercolationStats(size, trials);

    StdOut.println("mean                    = " + percolationStats.mean());
    StdOut.println("stddev                  = " + percolationStats.stddev());
    if (isSufficient(trials)) {
      String confidence = percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi();
      StdOut.println("95% confidence interval = " + confidence);
    }
  }

  private static boolean isSufficient(int trials) {
    return trials >= SUFFICIENT_TRIALS;
  }
}