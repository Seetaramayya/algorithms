package tutorial.java.part1.assignment.percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * <b>The problem:</b>. <p>In a famous scientific problem, researchers are interested in the following question:
 * if sites are independently set to be open with probability p (and therefore blocked with probability 1 − p),
 * what is the probability that the system percolates? When p equals 0, the system does not percolate; when p equals 1,
 * the system percolates. The plots below show the site vacancy probability p versus the percolation probability
 * for 20-by-20 random grid (left) and 100-by-100 random grid (right).</p>
 * <br />
 * <p>
 * Rules for this class is
 *
 * <ol>
 *     <li>only use classes from the {{java.lang}} or unmanaged dependency jar which is in $PROJECT_HOME/custom_lib</li>
 *     <li>The constructor must take time proportional to n2; all instance methods must take constant time plus a constant number of calls to union() and find().</li>
 * </ol>
 *
 * <p>
 * By convention, the row and column indices are integers between 1 and n, where (1, 1) is the upper-left site:
 * Throw an IllegalArgumentException if any argument to open(), isOpen(), or isFull() is outside its prescribed range.
 * Throw an IllegalArgumentException in the constructor if n ≤ 0.</p>
 * <p>
 * <br />
 * <b>Monte Carlo simulation:</b> To estimate the percolation threshold, consider the following computational experiment:
 *
 * <ul>
 *     <li>Initialize all sites to be blocked.</li>
 *     <li>
 *         Repeat the following until the system percolates:
 *         <ul>
 *             <li>Choose a site uniformly at random among all blocked sites.</li>
 *             <li>Open the site.</li>
 *         </ul>
 *     </li>
 *     <li>The fraction of sites that are opened when the system percolates provides an estimate of the percolation threshold.</li>
 *
 * </ul>
 *
 * @see <a href="https://coursera.cs.princeton.edu/algs4/assignments/percolation/specification.php">Problem description</a>
 */
public class Percolation {
    private static final int VIRTUAL_TOP_ROW = 0;
    // false is blocked and true is open site
    private final boolean[] sites;
    private final int maxRows;
    private final int maxColumns;
    private int openSites = 0;
    private final WeightedQuickUnionUF percolationGraph;
    // is there any better way rather than maintaining two graphs one with bottom other without
    private final WeightedQuickUnionUF fullGraph;
    private final int virtualBottomRow;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("provide non zero positive number to create grid");
        int gridSize = n * n;
        virtualBottomRow = n * n + 1; // initial virtual top row is first slot so 1 is added

        this.sites = new boolean[gridSize];
        this.percolationGraph = new WeightedQuickUnionUF(gridSize + 2); // For percolation we need top and bottom nodes
        this.fullGraph = new WeightedQuickUnionUF(gridSize + 1); // For full we need only top graph
        maxRows = n;
        maxColumns = n;

        for (int i = 0; i < sites.length; i++) {
            sites[i] = false;
        }
        connectVirtualTopRow(percolationGraph);
        connectVirtualTopRow(fullGraph);
    }

    private void connectVirtualTopRow(WeightedQuickUnionUF unionFind) {
        for (int i = 1; i <= maxColumns; i++) {
            unionFind.union(unionFindIndex(1, i), VIRTUAL_TOP_ROW);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        throwExceptionIfOutsideRange(row, col);
        int index = calculateIndexOrNegativeValue(row, col);
        int connectionIndex = unionFindIndex(row, col);
        if (!sites[index]) {
            sites[index] = true;
            openSites += 1;
            // connect left if it is open
            if (isLeftOpen(row, col)) {
                percolationGraph.union(connectionIndex - 1, connectionIndex);
                fullGraph.union(connectionIndex - 1, connectionIndex);
            }

            if (isRightOpen(row, col)) {
                percolationGraph.union(connectionIndex, connectionIndex + 1);
                fullGraph.union(connectionIndex, connectionIndex + 1);
            }

            if (isTopOpen(row, col)) {
                percolationGraph.union(connectionIndex, connectionIndex - maxColumns);
                fullGraph.union(connectionIndex, connectionIndex - maxColumns);
            }

            if (row == maxRows) {
                percolationGraph.union(connectionIndex, virtualBottomRow);
            } else if (isBottomOpen(row, col)) {
                percolationGraph.union(connectionIndex, connectionIndex + maxColumns);
                fullGraph.union(connectionIndex, connectionIndex + maxColumns);
            }
        }
    }

    private boolean isLeftOpen(int row, int col) {
        return withInRange(row, col - 1) && isOpen(row, col - 1);
    }

    private boolean isRightOpen(int row, int col) {
        return withInRange(row, col + 1) && isOpen(row, col + 1);
    }

    private boolean isTopOpen(int row, int col) {
        return withInRange(row - 1, col) && isOpen(row - 1, col);
    }

    private boolean isBottomOpen(int row, int col) {
        return withInRange(row + 1, col) && isOpen(row + 1, col);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        throwExceptionIfOutsideRange(row, col);
        return sites[calculateIndexOrNegativeValue(row, col)];
    }

    /**
     * A full site is an open site that can be connected to an open site in the top row via a chain of neighboring
     * (left, right, up, down) open sites
     *
     * @param row row number (starts with 1)
     * @param col column number (starts with 1)
     * @return true if given position is able to connect to top row otherwise false
     */
    public boolean isFull(int row, int col) {
        throwExceptionIfOutsideRange(row, col);
        int index = unionFindIndex(row, col);
        return isOpen(row, col) && fullGraph.find(VIRTUAL_TOP_ROW) == fullGraph.find(index);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return percolationGraph.find(VIRTUAL_TOP_ROW) == percolationGraph.find(virtualBottomRow);
    }

    @Override
    public String toString() {
        StringBuilder matrix = new StringBuilder();
        for (int i = 1; i <= maxRows; i++) {
            for (int j = 1; j <= maxColumns; j++) {
                int place = sites[calculateIndexOrNegativeValue(i, j)] ? 1 : 0;
                matrix.append(" ").append(place).append(" ");
            }
            matrix.append("\n");
        }
        return matrix.toString();
    }

    private void openRandomly(Percolation percolation, int n) {
        while (!percolation.percolates()) {
            int a = StdRandom.uniform(n + 1);
            int b = StdRandom.uniform(n + 1);
            int p = a == 0 ? 1 : a;
            int q = b == 0 ? 1 : b;
            percolation.open(p, q);
        }
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(20);
        percolation.openRandomly(percolation, 20);
        System.out.println(percolation);
        System.out.println("Percolates at " + percolation.numberOfOpenSites());
    }

    /**
     * this method calculates flattened 2 dimensional array index. Simple formula is (currentRow * totalColumns + currentColumn)
     *
     * <p>for example, for 2x2 matrix expected outputs are
     * <pre>
     *     (1, 1) => 0
     *     (1, 2) => 1
     *     (2, 1) => 2
     *     (2, 2) => 3
     * </pre>
     * </p>
     *
     * @param row grid starting with 1, not array index. so corresponding row value
     * @param col grid starting with 1, not array index. so corresponding row value
     * @return array index value
     */
    private int calculateIndexOrNegativeValue(int row, int col) {
        if (withInRange(row, col)) {
            return (row - 1) * maxColumns + (col - 1);
        }
        return -1; // if it is not with in the range
    }

    private int unionFindIndex(int row, int col) {
        int position = calculateIndexOrNegativeValue(row, col);
        if (position != -1) {
            return position + 1; // first position is occupied by virtual top so moving right
        }
        return position;
    }

    private boolean withInRange(int row, int col) {
        return row >= 1 && row <= maxRows && col >= 1 && col <= maxColumns;
    }

    private void throwExceptionIfOutsideRange(int row, int col) {
        if (!withInRange(row, col)) {
            throw new IllegalArgumentException("given (" + row + ", " + col + ") are outside of the range.");
        }
    }
}
