import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Arrays;
import java.util.Stack;


public class Board {

    private final int[][] tiles;
    private final int n;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.n = tiles.length;
        this.tiles = new int[n][n];
        copyTiles(tiles, this.tiles);
    }
                                           
    // string representation of this board
    public String toString() {
        StringBuffer str = new StringBuffer();
        str.append(this.n + "\n");
        for (int i = 0; i < this.n; i++){
            for (int j = 0; j < this.n; j++)
                str.append(String.format("%2d ", this.tiles[i][j]));
            str.append("\n");
        }
        return str.toString();
    }

    // board dimension n
    public int dimension() {
        return this.n;
    }

    // number of tiles out of place
    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < this.n; i++)
            for (int j = 0; j < this.n; j++){
                if (i == this.n - 1 && j == this.n - 1) continue;
                if (this.tiles[i][j] != (j + this.n * i + 1))
                    hamming++;
            }
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < this.n; i++)
            for (int j = 0; j < this.n; j++){
                manhattan += manhattanDistance(this.tiles[i][j], i+1, j+1);
                //StdOut.printf("Manhattan of tiles[%d][%d] = %d: %d\n", i, j, tiles[i][j],manhattanDistance(this.tiles[i][j], i+1, j+1));
            }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (that.dimension() != this.dimension()) return false;
        return Arrays.deepEquals(that.tiles, this.tiles);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> neighbors = new Stack<>();
        int[] coords = getBlankTile();
        
                                // i  j
        int[][] neighborTiles = {{-1,-1},  // ↑
                                 {-1,-1},  // →
                                 {-1,-1},  // ↓
                                 {-1,-1}}; // ←

        if (coords[0] - 1 >= 0) {          // ↑
            neighborTiles[0][0] = coords[0] - 1;
            neighborTiles[0][1] = coords[1];       
        }
        if (coords[1] + 1 < this.n) {      // →
            neighborTiles[1][1] = coords[1] + 1;
            neighborTiles[1][0] = coords[0];       
        }
        if (coords[0] + 1 < this.n) {      // ↓
            neighborTiles[2][0] = coords[0] + 1;
            neighborTiles[2][1] = coords[1];       
        }
        if (coords[1] - 1 >= 0) {          // ←
            neighborTiles[3][1] = coords[1] - 1;
            neighborTiles[3][0] = coords[0];       
        }
        
        int[][] newTiles = new int[this.n][this.n];
        for (int i = 0; i < 4; i++) {
            if (neighborTiles[i][0] < 0) continue;
            copyTiles(this.tiles, newTiles);
            exch(newTiles, coords, neighborTiles[i]);
            Board newNeighbor = new Board(newTiles);
            neighbors.push(newNeighbor);
        }

        return neighbors; 
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {

        int[][] twinTiles = new int[this.n][this.n];
        copyTiles(this.tiles, twinTiles);

        int[] tile0 = new int[2];
        int[] tile1 = new int[2];

        while (true) {
            tile0[0] = StdRandom.uniform(this.n);
            tile0[1] = StdRandom.uniform(this.n);

            tile1[0] = StdRandom.uniform(this.n);
            tile1[1] = StdRandom.uniform(this.n);

            if (Arrays.equals(tile0, tile1)) continue;
            if (twinTiles[tile0[0]][tile0[1]] == 0) continue;
            if (twinTiles[tile1[0]][tile1[1]] == 0) continue;
    
            break;
        }
        exch(twinTiles, tile0, tile1);

        Board twin = new Board(twinTiles);
        return twin;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] t1 = {{1,0,3},
                      {4,2,5},
                      {7,8,6}};
        int[][] t2 = {{8,1,3},
                      {4,2,1},
                      {7,6,5}};
        int[][] goal = {{1,2,3},
                        {4,5,6},
                        {7,8,0}};

        Board b1 = new Board(t1);
        Board b2 = new Board(t2);
        Board gBoard = new Board(goal);

        StdOut.printf("Board 1: %s\n", b1.toString());
        StdOut.printf("Board 2: %s\n" ,b2.toString());
        
        StdOut.printf("Board 2 hamming: %d\n" ,b2.hamming());
        StdOut.printf("Board 2 manhattan: %d\n" ,b2.manhattan());
        
        StdOut.printf("Board 2 neighbors: \n" );
        for (Board i : b2.neighbors()){
            StdOut.println(i + "\n");
        }
        
        StdOut.printf("is Board 1 == Board 2: %s\n", b1.equals(b2));
        StdOut.printf("is Board 1 == Board 1: %s\n", b1.equals(b1));
        
        StdOut.printf("is Board 2 goal?: %s\n", b1.isGoal());
        StdOut.printf("is Goal board goal?: %s\n", gBoard.isGoal());

        StdOut.printf("Board 2 twin: %s\n", b2.twin().toString());

    }

    private void copyTiles(int[][] og, int[][] cpy) {
        for (int i = 0; i < this.n; i++)
            for (int j = 0; j < this.n; j++)
                cpy[i][j] = og[i][j];
    }

    // Computes the manhattan distance of a tile provided its x & y coords (x,y = 1...n)
    private int manhattanDistance(int tile, int x, int y){
        if (tile == 0) return 0;
        int j = tile % this.n;
        j = (j > 0) ? j : this.n;
        int i = ((tile - j) / this.n) + 1;
        return Math.abs(x - i) + Math.abs(y - j);
    }

    // returns the index of the blank tile (0)
    private int[] getBlankTile() {
        int[] coords = new int[2];
       
        boolean found = false;
        for (int i = 0; i < this.n; i++){
            for (int j = 0; j < this.n; j++) {
                if (this.tiles[i][j] == 0) {
                    coords[0] = i;
                    coords[1] = j;
                    found = true;
                    break;
                }
            }
            if (found) break;
        }
        return coords;
    }

    private void exch(int[][] tiles, int[] iCoord, int[] fCoord) {
        int tmp = tiles[iCoord[0]][iCoord[1]];
        tiles[iCoord[0]][iCoord[1]] = tiles[fCoord[0]][fCoord[1]];
        tiles[fCoord[0]][fCoord[1]] = tmp;
    }
}