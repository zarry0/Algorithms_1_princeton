import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int N;
    private final WeightedQuickUnionUF grid;
    private boolean[] openSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n)
    {
        if (n <= 0) throw new IllegalArgumentException("Cannot initialize a grid with n <= 0");
        N = n;
        grid = new WeightedQuickUnionUF(n*n); //Generates an n-by-n grid 
        openSites = new boolean[n*n]; //Generates an n^2 array (originaly every value is set to false) that represents every open site
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col)
    {
        int site = xyTo1d(row, col);
        if (openSites[site]) return; //if already open skip the rest
        openSites[site] = true;  //mark the site as open

        //generates an array containing all open neighbors or -1 otherwise
        int[] neighbors = new int[4]; //starting from top to left in cw motion (^ > v <)
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
        /*
        System.out.println("    " + neighbors[0]);
        System.out.println("     ^");
        System.out.println(neighbors[3] + " < " + site + " > " + neighbors[1]);
        System.out.println("     v");
        System.out.println("    " + neighbors[2]);
        System.out.println("");*/
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
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites()
    {
        return 0;
    }

    // does the system percolate?
    public boolean percolates()
    {
        return false;
    }

    // test client (optional)
    public static void main(String[] args)
    {
        Percolation perc = new Percolation(4);
        //System.out.println(perc.isOpen(1, 1));
        perc.open(1, 2);
        perc.open(2, 1);
        perc.open(3, 2);
        perc.open(2, 3);
        perc.open(2, 2);
        //System.out.println(perc.isOpen(1, 1));
        
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