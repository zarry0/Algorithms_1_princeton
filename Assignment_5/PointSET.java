import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.TreeSet;

public class PointSET {
    private final TreeSet<Point2D> set;
    private int size;

    public PointSET(){                              // construct an empty set of points 
        this.set = new TreeSet<>();
        this.size = 0;
    }
    public boolean isEmpty(){                        // is the set empty?
        return this.size == 0;
    }
    public int size(){                               // number of points in the set 
        return this.size;
    }
    public void insert(Point2D p){                   // add the point to the set (if it is not already in the set).
        validateInput(p);
        if (this.contains(p)) return;
        this.set.add(p);
        this.size++;
    }
    public boolean contains(Point2D p){              // does the set contain point p? 
        validateInput(p);
        return this.set.contains(p);
    }
    public void draw(){                              // draw all points to standard draw 
        for (Point2D point : this.set) 
            point.draw();
    }
    public Iterable<Point2D> range(RectHV rect){     // all points that are inside the rectangle (or on the boundary).
        validateInput(rect);
        TreeSet<Point2D> pointsInRange = new TreeSet<>();
        for (Point2D point : this.set) {
            if (rect.contains(point)) 
                pointsInRange.add(point);
        }
        return pointsInRange;
    }
    public Point2D nearest(Point2D p){               // a nearest neighbor in the set to point p; null if the set is empty 
        validateInput(p);
        if (this.size == 0) return null;

        Point2D nearest = new Point2D(2, 2);
        for (Point2D point : this.set) {
            if (p.equals(point)) continue;
            if (p.distanceTo(point) < p.distanceTo(nearest)) {
                nearest = point;
            }
        }
        return nearest;
    }
 
    public static void main(String[] args){          // unit testing of the methods (optional) 

    }

    private void validateInput(Object o) {
        if (o == null) throw new IllegalArgumentException("Argument can't be null");
    }
 }