import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int size;
    private boolean[][] grid;
    private WeightedQuickUnionUF weightedQuickUnionUF;
    private int topVirtual;
    private int bottomVirtual;
    private int numberOfOpenSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        numberOfOpenSites = 0;
        size = n;
        topVirtual = n * n;
        bottomVirtual = n * n + 1;
        grid = new boolean[n][n];
        weightedQuickUnionUF = new WeightedQuickUnionUF(n * n + 2);
        for (int i = 0; i < n; i++) {
            weightedQuickUnionUF.union(topVirtual, i);
            weightedQuickUnionUF.union(bottomVirtual, (n - 1) * n + i);
        }
    }

    private void isValid(int row, int col) {
        if (row < 1 || row > size || col < 1 || col > size) {
            throw new IllegalArgumentException();
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        isValid(row, col);
        if (grid[row - 1][col - 1]) {
            return;
        }

        numberOfOpenSites++;
        grid[row - 1][col - 1] = true;
        int p = convertGridToUnion(row, col);

        // Left
        if (col > 1 && isOpen(row, col - 1)) {
            int q = convertGridToUnion(row, col - 1);
            weightedQuickUnionUF.union(p, q);
        }
        // Right
        if (col < size && isOpen(row, col + 1)) {
            int q = convertGridToUnion(row, col + 1);
            weightedQuickUnionUF.union(p, q);
        }

        // Top
        if (row > 1 && isOpen(row - 1, col)) {
            int q = convertGridToUnion(row - 1, col);
            weightedQuickUnionUF.union(p, q);
        }

        // Bottom
        if (row < size && isOpen(row + 1, col)) {
            int q = convertGridToUnion(row + 1, col);
            weightedQuickUnionUF.union(p, q);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        isValid(row, col);
        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        isValid(row, col);
        return isOpen(row, col)
                && weightedQuickUnionUF.find(convertGridToUnion(row, col)) == weightedQuickUnionUF.find(topVirtual);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        if (numberOfOpenSites < 1)
            return false;

        return (size == 1 && grid[0][0]) ||
                weightedQuickUnionUF.find(bottomVirtual) == weightedQuickUnionUF.find(topVirtual);
    }

    private int convertGridToUnion(int row, int col) {
        return (row - 1) * size + (col - 1);
    }
}
