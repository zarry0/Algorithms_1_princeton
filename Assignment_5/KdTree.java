import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;

public class KdTree {

    private final boolean VERTICAL = false;

    private Node treeRoot; 
    private int size;

    public KdTree(){                                 // construct an empty set of points 
        this.treeRoot = new Node();
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
        if (contains(p)) return;
        if (size == 0) {
            this.treeRoot.point = p;
            this.treeRoot.rect = new RectHV(0, 0, 1, 1);
        }  
        else {
            this.treeRoot = insert(treeRoot, p, VERTICAL, treeRoot.rect);
        }
        this.size++;
    }
    private static Node insert(Node nodeRoot, Point2D p, boolean orientation, RectHV rect) {
        if (nodeRoot == null) return new Node(p, orientation, rect);
        int cmp = nodeRoot.compareTo(p);

        RectHV nodeRect;

        if (orientation) //horizontal
            if (cmp < 0) nodeRect = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), nodeRoot.point.y());
            else         nodeRect = new RectHV(rect.xmin(), nodeRoot.point.y(), rect.xmax(), rect.ymax()); 
        else             //vertical
            if (cmp < 0) nodeRect = new RectHV(rect.xmin(), rect.ymin(), nodeRoot.point.x(), rect.ymax());
            else         nodeRect = new RectHV(nodeRoot.point.x(), rect.ymin(), rect.xmax(), rect.ymax()); 
            
        if (cmp < 0) nodeRoot.left =  insert(nodeRoot.left,  p, !nodeRoot.orientation, nodeRect);
        else         nodeRoot.right = insert(nodeRoot.right, p, !nodeRoot.orientation, nodeRect);
        
        return nodeRoot;
    }

    public boolean contains(Point2D p){              // does the set contain point p? 
        validateInput(p);
        if (this.size == 0) return false;
        return contains(this.treeRoot, p);
    }
    private static boolean contains(Node nodeRoot, Point2D p) {
        if (nodeRoot == null) return false;
        int cmp = nodeRoot.compareTo(p);
        if      (cmp < 0) return contains(nodeRoot.left,  p); //left
        else if (cmp > 0) return contains(nodeRoot.right, p); //rigth
        else              return true;
    }

    public void draw(){                              // draw all points to standard draw 
        draw(treeRoot);
    }
    private static void draw(Node nodeRoot) {
        if (nodeRoot == null) return;

        StdDraw.setPenRadius(0.001);
        if (nodeRoot.orientation) { //horizontal 
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(nodeRoot.rect.xmin(), nodeRoot.point.y(), nodeRoot.rect.xmax(), nodeRoot.point.y());
        } else {//vertical
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(nodeRoot.point.x(), nodeRoot.rect.ymin(), nodeRoot.point.x(), nodeRoot.rect.ymax());
        }
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.BLACK);
        nodeRoot.point.draw();
        
        draw(nodeRoot.left);
        draw(nodeRoot.right);
    }

    public Iterable<Point2D> range(RectHV rect){     // all points that are inside the rectangle (or on the boundary).
        validateInput(rect);
        Queue<Point2D> pointsInRange = new Queue<>();
        range(rect, treeRoot, pointsInRange);
        return pointsInRange;
    }
    private static void range(RectHV queryRect, Node nodeRoot, Queue<Point2D> points){
        if (nodeRoot == null) return;

        //Check if current point is in te query rectangle
        if (queryRect.contains(nodeRoot.point))
            points.enqueue(nodeRoot.point);
        
        //Check whether the query rectangle intersects one or both sides of the nodes rectangle
        double[][] coords = new double[2][1];
        coords[0][0] = (nodeRoot.orientation) ? queryRect.ymin() : queryRect.xmin();
        coords[0][1] = (nodeRoot.orientation) ? queryRect.ymax() : queryRect.xmax();
        coords[1][0] = (nodeRoot.orientation) ? nodeRoot.point.y() : nodeRoot.point.x();

        if (coords[0][0] > coords[1][0]) { // rigth
            range(queryRect, nodeRoot.right, points);
        }else if (coords[0][1] < coords[1][0]) { // left  
            range(queryRect, nodeRoot.left, points);
        }else { //both
            range(queryRect, nodeRoot.left,  points);
            range(queryRect, nodeRoot.right, points);
        }

    }

    public Point2D nearest(Point2D p){               // a nearest neighbor in the set to point p; null if the set is empty 
        validateInput(p);

        Node nearestNode = nearest(p, this.treeRoot, this.treeRoot);
        return (nearestNode != null) ? nearestNode.point : null;
    }
    private static Node nearest(Point2D queryPoint, Node rootNode, Node nearestNode) {
        // if the node is empty, exit
        if (rootNode == null) return nearestNode;

        // check if current node is closer to query point than the best distance so far
        double distanceToPoint = queryPoint.distanceSquaredTo(rootNode.point);
        double bestDistance = queryPoint.distanceSquaredTo(nearestNode.point);
        if (bestDistance > distanceToPoint) nearestNode = rootNode;

        //
        double coords[] = new double[2];
        if (rootNode.orientation) { // horizontal
            coords[0] = queryPoint.y();
            coords[1] = rootNode.point.y();
        }else { // vertical
            coords[0] = queryPoint.x();
            coords[1] = rootNode.point.x();
        }
        
        // go to the subtree on the same side as the query point
        boolean goLeft;
        if (coords[0] > coords[1]){ // rigth
            nearestNode = nearest(queryPoint, rootNode.right, nearestNode);
            goLeft = true;
        } else{                     // left
            nearestNode = nearest(queryPoint, rootNode.left,  nearestNode);
            goLeft = false;
        } 
        
        // if the distance to the other subtree is less than the current best distance, visit that subtree
        if (Math.pow(coords[0] - coords[1], 2) < bestDistance) {
            if (goLeft) nearestNode = nearest(queryPoint, rootNode.left,  nearestNode);
            else        nearestNode = nearest(queryPoint, rootNode.right, nearestNode);
        }

        // return the current best node
        return nearestNode;

    }
    public static void main(String[] args){          // unit testing of the methods (optional) 

        String filename = args[0];
        In in = new In(filename);
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }
        kdtree.draw();
 
    }

    private void validateInput(Object o) {
        if (o == null) throw new IllegalArgumentException("Argument can't be null");
    }

    private static class Node {
        private Point2D point;
        private RectHV rect;
        private Node left;
        private Node right;
        private boolean orientation;
        
        Node() {
        } 
        Node(Point2D p, boolean o, RectHV r) {
            this.point = p;
            this.orientation = o;
            this.rect = r;
        }

        public int compareTo(Point2D p) {
            if (this.point.equals(p)) return 0;
            if (this.orientation)     return (this.point.y() > p.y()) ? -1 : 1;
            else                      return (this.point.x() > p.x()) ? -1 : 1;
        }

    }
 }