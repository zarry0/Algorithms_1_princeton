/*************************************************************************
 *  Compilation:  javac PointPlotter.java
 *  Execution:    java PointPlotter input.txt
 *  Dependencies: Point.java, In.java, StdDraw.java
 *
 *  Takes the name of a file as a command-line argument.
 *  Reads in an integer N followed by N pairs of points (x, y)
 *  with coordinates between 0 and 32,767, and plots them using
 *  standard drawing.
 *
 *************************************************************************/
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;

public class PointPlotter {
    public static void main(String[] args) {

        // rescale coordinates and turn on animation mode
        StdDraw.setPenRadius(0.0025);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show();

        // read in the input
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
            StdDraw.text(x,y,p.toString());
            p.draw();
        }

        // display to screen all at once
        
        StdDraw.show();
    }
}
