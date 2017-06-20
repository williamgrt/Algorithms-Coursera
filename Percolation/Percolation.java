import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private final int N;
	private int numbersOfOpen;
	private WeightedQuickUnionUF grid;
	private WeightedQuickUnionUF gridNVB;
	private boolean[] isOpen;
	
	private int xyTo1D(int x, int y)
	{
		return N * (x - 1) + y;
	}
	
	// create n-by-n grid, with all sites blocked
	public Percolation(int n)
	{
		if (n <= 0)
			throw new java.lang.IllegalArgumentException("n <= 0");
		N = n;
		numbersOfOpen = 0;
		grid = new WeightedQuickUnionUF(N * N + 2);
		gridNVB = new WeightedQuickUnionUF(N * N + 1);
		isOpen = new boolean[N * N + 2];
	}
	
	// open site (row, col) if it is not open already
	public void open(int row, int col)
	{
		if (row <= 0 || row > N || col <= 0 || col > N)
			throw new java.lang.IndexOutOfBoundsException("argument outside its prescribed range");
		openHelp(row, col);
	}
	
	private void openHelp(int row, int col)
	{
		int x = xyTo1D(row, col);
		if (!isOpen[x])
		{
			int n = xyTo1D(row, col);
			isOpen[n] = true;
			numbersOfOpen++;
			if ((row > 1) && isOpen[xyTo1D(row - 1, col)])
			{
				grid.union(n, xyTo1D(row - 1, col));
				gridNVB.union(n, xyTo1D(row - 1, col));
			}
			if ((row < N) && isOpen[xyTo1D(row + 1, col)])
			{
				grid.union(n, xyTo1D(row + 1, col));
				gridNVB.union(n, xyTo1D(row + 1, col));
			}
			if ((col > 1) && isOpen[xyTo1D(row, col - 1)])
			{
				grid.union(n, xyTo1D(row, col - 1));
				gridNVB.union(n, xyTo1D(row, col - 1));
			}
			if ((col < N) && isOpen[xyTo1D(row, col + 1)])
			{
				grid.union(n, xyTo1D(row, col + 1));
				gridNVB.union(n, xyTo1D(row, col + 1));
			}
			if (row == 1)
			{
				grid.union(0, n);
				gridNVB.union(0, n);
			}
			if (row == N)
				grid.union(n, N * N + 1);
		}
	}
	
	// is site (row, col) open?
	public boolean isOpen(int row, int col) 
	{
		if (row <= 0 || row > N || col <= 0 || col > N)
			throw new java.lang.IndexOutOfBoundsException("argument outside its prescribed range");
		return isOpenHelp(row, col);
	}
	
	private boolean isOpenHelp(int row, int col)
	{
		return isOpen[xyTo1D(row, col)];
	}
	
	// is site (row, col) full?
	public boolean isFull(int row, int col) 
	{
		if (row <= 0 || row > N || col <= 0 || col > N)
			throw new java.lang.IndexOutOfBoundsException("argument outside its prescribed range");
		return isFullHelp(row, col);
	}
	
	private boolean isFullHelp(int row, int col)
	{
		return gridNVB.connected(0, xyTo1D(row, col));
	}
	// number of open sites
	public int numberOfOpenSites()
	{
		return numbersOfOpen;
	}
	
	// does the system percolate?
	public boolean percolates()
	{
		return grid.connected(0, N * N + 1);
	}
	public static void main(String[] args)
	{
		
	}
}