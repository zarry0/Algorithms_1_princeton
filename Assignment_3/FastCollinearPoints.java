import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import java.util.Arrays;
import java.util.ArrayList;

public class FastCollinearPoints {

    private int numberOfSegments;
    private ArrayList<LineSegment> segmentList;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException("Null argument in the constructor");

        int n = points.length;
        segmentList = new ArrayList<LineSegment>();
        numberOfSegments = 0;
        if (n < 4)
            return;

        Arrays.sort(points);
        for (int i = 0; i < n - 1; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException("Null point in the argument array");
            if (points[i].compareTo(points[i + 1]) == 0)
                throw new IllegalArgumentException("Null argument in the constructor");
        }

        Point[] pointsCopy = new Point[n];
        for (int j = 0; j < n; j++)
            pointsCopy[j] = points[j];

        for (int i = 0; i < n; i++) {
            Point pivot = pointsCopy[i];
            Arrays.sort(points, 0, n, pivot.slopeOrder());

            int start = 1;
            Point current = points[0];
            for (int j = 2; j <= n; j++) {
                if (j < n)
                    current = points[j];

                if (j == n || pivot.slopeOrder().compare(points[j - 1], current) != 0) {
                    if (j - start >= 3) {
                        Arrays.sort(points, start, j);

                        if (pivot.compareTo(points[start]) < 0) {
                            LineSegment newSegment = new LineSegment(pivot, points[j - 1]);
                            segmentList.add(newSegment);
                            numberOfSegments++;
                        }
                    }
                    start = j;
                }
            }
        }

    }

    // the number of line segments
    public int numberOfSegments() {
        return numberOfSegments;
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] segments = {};
        if (numberOfSegments < 1)
            return segments;
        segments = segmentList.toArray(new LineSegment[0]);
        return segments;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}