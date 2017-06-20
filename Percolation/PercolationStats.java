import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private final int N;
	private final int T;
	private int[] every;
	
	// perform trials independent experiments on an n-by-n grid
	public PercolationStats(int n, int trials)
	{
		if (n <= 0 || trials <= 0)
			throw new java.lang.IllegalArgumentException();
		N = n;
		T = trials;
		every = new int[T];
		StatsHelp(N, T);
	}
	
	private void StatsHelp(int N, int T)
	{
		for (int i = 0; i < T; i++)
		{
			Percolation percolation = new Percolation(N);
			while (!percolation.percolates())
			{
				int row = 0, col = 0;
				do
				{
					row = StdRandom.uniform(1, N + 1);
					col = StdRandom.uniform(1, N + 1);
				}
				while (percolation.isOpen(row, col));
				percolation.open(row, col);
				every[i]++;
			}
		}
	}
	
	// sample mean of percolation threshold
	public double mean()
	{
		return StdStats.mean(every) / (N * N);
	}
	// sample standard deviation of percolation threshold
	public double stddev()
	{
		return StdStats.stddev(every) / N;
	}
	// low  endpoint of 95% confidence interval
	public double confidenceLo()
	{
		return mean() - 1.96 * stddev() / Math.sqrt(T);
	}
	// high endpoint of 95% confidence interval
	public double confidenceHi()
	{
		return mean() + 1.96 * stddev() / Math.sqrt(T);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int n = Integer.parseInt(args[0]);
		int trials = Integer.parseInt(args[1]);
		PercolationStats ps = new PercolationStats(n, trials);
		StdOut.print("mean                    = " + ps.mean());
		StdOut.println();
		StdOut.print("stddev                  = " + ps.stddev());
		StdOut.println();
		StdOut.print("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
		StdOut.println();
	}

}
