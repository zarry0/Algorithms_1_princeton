import edu.princeton.cs.algs4.WeightedQuickUnionUF;
//main
public class Percolation {

    private final int N;
    private final WeightedQuickUnionUF grid;
    private byte[] status;
    private int openSiteCount;
    private boolean percolates;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n)
    {
        if (n <= 0) throw new IllegalArgumentException("Cannot initialize a grid with n <= 0");
        N = n;
        grid = new WeightedQuickUnionUF(n*n); //Generates an n-by-n grid 
        status = new byte[n*n]; //each one of the 3 LSB represents: if is open, if is conected to top, if is conected to bottom : (000: open, top, bottom)
        openSiteCount = 0;
        percolates = false;

        for (int i = 0; i < n; i++) {  
            status[i] |= 0b010;          //Set top row
            status[n*n-n+i] |= 0b001;    //Set bottom row 
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col)
    {
        int site = xyTo1d(row, col);
        if ((status[site] & 0b100) >> 2 == 1) return;  //if is already open skip the rest
        status[site] |= 0b100;  //mark the site as open
        openSiteCount++;

        //generates an array containing all open neighbors roots or -1 otherwise
        int[] neighbors = new int[4]; //starting from top to left in cw direction (^ > v <)
        neighbors[0] = (row == 1) ? -1 : (isOpen(row-1, col) ? grid.find(site - N) : -1); // ^
        neighbors[2] = (row == N) ? -1 : (isOpen(row+1, col) ? grid.find(site + N) : -1); // v
        neighbors[3] = (col == 1) ? -1 : (isOpen(row, col-1) ? grid.find(site - 1) : -1); // <
        neighbors[1] = (col == N) ? -1 : (isOpen(row, col+1) ? grid.find(site + 1) : -1); // >

        byte joinStatus = status[site];
        for (int i = 0; i < 4; i++) {
            if (neighbors[i] != -1) {
                grid.union(site, neighbors[i]);
                joinStatus |= status[neighbors[i]];
            } 
        }
        int root = grid.find(site);
        status[root] |= joinStatus;
        if (status[root] == 7) percolates = true;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col)
    {
        int site = xyTo1d(row, col);
        return (status[site] & 0b100) >> 2 == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col)
    {
        int site = grid.find(xyTo1d(row, col));
        return (status[site] & 0b110) == 6; 
    }

    // returns the number of open sites
    public int numberOfOpenSites() { return openSiteCount; }

    // does the system percolate?
    public boolean percolates() { return percolates; }

    // test client (optional)
    public static void main(String[] args)
    {
        //
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