import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import java.util.Arrays;

public class BruteCollinearPoints {

    private int numberOfSegments;
    private Node head;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException("Null argument in the constructor");

        int n = points.length;
        int r = 4;

        for (int i = 0; i < n; i++) {
            if (points[i] == null)
                    throw new IllegalArgumentException("Null point in the argument array");
            for (int j = i+1; j < n; j++){
                if (points[j] == null)
                    throw new IllegalArgumentException("Null point in the argument array");
                if (points[i].compareTo(points[j]) == 0)
                throw new IllegalArgumentException("Null argument in the constructor");
            }
        }
        
        numberOfSegments = 0;
        if (n < 4) return;
        
        Point[] pointsCopy = new Point[n];
        for (int j = 0; j < n; j++)
        pointsCopy[j] = points[j];
        
        Arrays.sort(pointsCopy);
        
        this.head = new Node();
        Node current = head;

        Point[] lastSegment = new Point[2];
        Point[] selectedPoints = new Point[r];

        for (int i = 0; i <= n-r; i++) 
        {
            for (int j = i+1; j <= n-r+1; j++) 
            {
                for (int k = j+1; k <= n-r+2; k++) 
                { 
                    for (int l = k+1; l < n; l++) 
                    {
                        // Compute each posible combination of 4 points (nC4 = n! / (4!*(n-4)!))
                        selectedPoints[0] = pointsCopy[i];
                        selectedPoints[1] = pointsCopy[j];
                        selectedPoints[2] = pointsCopy[k];
                        selectedPoints[3] = pointsCopy[l];
                        
                        if (!isSegment(selectedPoints)) continue; 
                        //StdOut.println("[ " + selectedPoints[0] + " " + selectedPoints[1] + " " + selectedPoints[2] + " " + selectedPoints[3] + " " + " ]");

                        // Save the current segment
                        LineSegment segment = new LineSegment(selectedPoints[0], selectedPoints[3]);
                        if (numberOfSegments < 1)
                            head.val = segment;
                        else {
                            if (selectedPoints[0].compareTo(lastSegment[0]) == 0 && selectedPoints[3].compareTo(lastSegment[1]) == 0) 
                                continue;
                            Node newSegment = new Node();
                            newSegment.val = segment;
                            current.next = newSegment;
                            current = current.next;

                        }
                        lastSegment[0] = selectedPoints[0];
                        lastSegment[1] = selectedPoints[3];
                        numberOfSegments++;
                    }
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
        if (numberOfSegments < 1) return segments;
        segments = new LineSegment[numberOfSegments];
        int j = 0;
        for (Node i = head; i != null; i = i.next) {
            segments[j++] = i.val;
            if (j >= numberOfSegments) break;
        }
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        StdDraw.setPenColor(100, 100, 255);
        StdDraw.setPenRadius(0.0025);
        LineSegment[] segments = collinear.segments();
        for (LineSegment segment : segments) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
        //mutation test
        // segments[0] = new LineSegment(points[0], points[5]);
        // StdDraw.setPenColor(100, 255, 100);
        // StdDraw.setPenRadius(0.0025);
        // for (LineSegment segment : collinear.segments()) {
        //     StdOut.println(segment);
        //     segment.draw();
        // }
        // StdDraw.show();
    }

    private boolean isSegment(Point[] points) {
        for (int i = 1, j = 2; i < 4 && j < 4; i++, j++)
            if (points[0].slopeOrder().compare(points[i], points[j]) != 0)
                return false;
        return true;
    }

    private class Node {
        LineSegment val;
        Node next;
    }
 }