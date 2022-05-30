import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int N;
    private final WeightedQuickUnionUF grid;
    private boolean[] openSites;
    private int openSiteCount;
    private int virtualTop;
    private int virtualBottom;
    private int[] bottomRoots;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n)
    {
        if (n <= 0) throw new IllegalArgumentException("Cannot initialize a grid with n <= 0");
        N = n;
        grid = new WeightedQuickUnionUF(n*n); //Generates an n-by-n grid 
        openSites = new boolean[n*n]; //Generates an n^2 array (originaly every value is set to false) that represents every open site
        openSiteCount = 0;
        bottomRoots = new int[n];
        for (int i = 1; i <= n; i++) {  //Generates virtual top and virtual bottom
            grid.union(0, xyTo1d(1, i));   // join top row
            grid.union((n*n)-n, xyTo1d(n, i));  // join bottom row
            bottomRoots[i-1] = xyTo1d(n, i);      // save bottom row roots
        }
        //virtualTop = grid.find(0);
        //virtualBottom = grid.find(n*n-1);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col)
    {
        int site = xyTo1d(row, col);
        if (openSites[site]) return; //if already open skip the rest
        openSites[site] = true;  //mark the site as open
        openSiteCount++;

        //generates an array containing all open neighbors or -1 otherwise
        int[] neighbors = new int[4]; //starting from top to left in cw direction (^ > v <)
        neighbors[0] = (row == 1) ? -1 : (openSites[site - N] ? site - N : -1); // ^
        neighbors[2] = (row == N) ? -1 : (openSites[site + N] ? site + N : -1); // v
        neighbors[3] = (col == 1) ? -1 : (openSites[site - 1] ? site - 1 : -1); // <
        neighbors[1] = (col == N) ? -1 : (openSites[site + 1] ? site + 1 : -1); // >

        int root = grid.find(site); //get the root of the point to open
        for (int i = 0; i < 4; i++) {
            if (neighbors[i] != -1) {
                if (root != grid.find(neighbors[i])) grid.union(site, neighbors[i]);  //join neighboring points not previously connected
            } 
        }
        //if any of the bottom row is conected to something new, update virtualBotom's root
        /*root = grid.find(site); //update site root
        for (int i = 0; i < N; i++) { 
            if (root == bottomRoots[i] || site == bottomRoots[i]) 
            {
                 bottomRoots[i] = root;
                 virtualBottom = root;
                 break; 
            } 
        };*/
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col)
    {
        int site = xyTo1d(row, col);
        return openSites[site];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col)
    {
        int site = xyTo1d(row, col);
        return openSites[site] && (grid.find(site) == grid.find(0));
    }

    // returns the number of open sites
    public int numberOfOpenSites() { return openSiteCount; }

    // does the system percolate?
    public boolean percolates() { return grid.find(N*N-1) == grid.find(0); }

    // test client (optional)
    public static void main(String[] args)
    {
        Percolation perc = new Percolation(4);
        //System.out.println(perc.isOpen(1, 1));
        System.out.println(perc.numberOfOpenSites());
        perc.open(1, 2);
        perc.open(2, 1);
        perc.open(3, 2);
        perc.open(2, 3);
        perc.open(2, 2);
        System.out.println(perc.numberOfOpenSites());
        perc.open(2, 2);
        System.out.println(perc.numberOfOpenSites());
        
    }

    // determines the object based on the coordenates
    private int xyTo1d (int x, int y) { 
        if (!isValidCoord(x, y)) throw new IllegalArgumentException("Coordinate out of bounds");
        return y + N * (x - 1) - 1; 
    }
    
    //validates that x & y coords exist
    private boolean isValidCoord(int x, int y) {
        if (x < 1 || x > N) return false;
        if (y < 1 || y > N) return false;
        return true;
    }

}