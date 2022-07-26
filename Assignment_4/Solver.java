import java.util.Deque;
import java.util.LinkedList;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    
    private final boolean isSolvable;
    private final Deque<Board> solutionSequence = new LinkedList<Board>();
    private int moves = -1;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException("Null argument in the constructor");

        MinPQ<SearchNode> gameTree = new MinPQ<>();
        SearchNode node = new SearchNode(initial);
        gameTree.insert(node);

        MinPQ<SearchNode> twinTree = new MinPQ<>();
        SearchNode twinNode = new SearchNode(initial.twin());
        twinTree.insert(twinNode);

        while (true) {
            node = gameTree.delMin();
            if (node.board.isGoal()) {
                this.isSolvable = true;
                break;
            }

            twinNode = twinTree.delMin();
            if (twinNode.board.isGoal()) {
                this.isSolvable = false;
                break;
            }
            
            for(Board neighbor : node.board.neighbors()) {
                if (node.getPrevious() != null)
                    if (neighbor.equals(node.getPrevious().board)) continue;
                SearchNode newNode = new SearchNode(neighbor, node);
                gameTree.insert(newNode);
            }

            for(Board twin : twinNode.board.neighbors()) {
                if (twinNode.getPrevious() != null)
                    if (twin.equals(twinNode.getPrevious().board)) continue;
                SearchNode newTwin = new SearchNode(twin, twinNode);
                twinTree.insert(newTwin);
            }
        }

        if (!isSolvable) return;
        for (SearchNode i = node; i != null; i = i.prev)
            this.solutionSequence.push(i.board);
        this.moves += solutionSequence.size();
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return this.isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return this.moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable) return null;
        return solutionSequence; 
    }

    // test client (see below) 
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

    // Seach node helper class
    private class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final SearchNode prev;
        private final int moves;
        private final int manhattan;
        private final int priority;

        public SearchNode(Board board){
            this.board = board;
            this.prev = null;
            this.moves = 0;
            this.manhattan = this.board.manhattan();
            this.priority = this.manhattan;
        }
        public SearchNode(Board board, SearchNode prev){
            this.board = board;
            this.prev = prev;
            this.moves = (prev == null) ? 0 : prev.getMoves() + 1;
            this.manhattan = this.board.manhattan();
            this.priority = this.manhattan + this.moves;
        }
        public int getMoves() {
            return this.moves;
        }
        public SearchNode getPrevious() {
            return this.prev;
        }
        @Override
        public String toString() {
            StringBuilder str = new StringBuilder();
            str.append("priority  = " + this.priority + "\n");
            str.append("moves     = " + this.moves + "\n");
            str.append("manhattan = " + this.manhattan + "\n");
            str.append(this.board.toString());
            return str.toString();
        }
        /**
         * Compares two nodes by their manhattan priorities.
         * 
         * @param   that the other node
         * @return  0 if both have se same priority;
         *          a positive int if this node is greater than the argument;
         *          a negative int if this node is less than the argument
         */
        public int compareTo(SearchNode that) {
            if (this.priority > that.priority) return 1;
            if (this.priority < that.priority) return -1;
            return 0;
        }
    }

}