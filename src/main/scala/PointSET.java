import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class PointSET {
    private final TreeSet<Point2D> orderedSet = new TreeSet<>();
    // construct an empty set of points
    public PointSET() {

    }

    // is the set empty? O(1)
    public boolean isEmpty() {
        return orderedSet.isEmpty();
    }

    // number of points in the set O(1)
    public int size() {
        return orderedSet.size();
    }

    // add the point to the set (if it is not already in the set) O(logN)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("point should not be null");
        orderedSet.add(p);
    }

    // does the set contain point p? O(logN)
    public boolean contains(Point2D p) {
        return p != null && orderedSet.contains(p);
    }

    // draw all points to standard draw O(N)
    public void draw() {
        for (Point2D currentPoint : orderedSet) {
            StdDraw.point(currentPoint.x(), currentPoint.y());
        }
    }

    // all points that are inside the rectangle (or on the boundary) O(N)
    public Iterable<Point2D> range(RectHV rectangle) {
        Set<Point2D> points = new HashSet<>();
        for (Point2D currentPoint : orderedSet) {
            if (rectangle.contains(currentPoint)) {
                points.add(currentPoint);
            }
        }
        return points;
    }

    // a nearest neighbor in the set to point p; null if the set is empty O(N)
    public Point2D nearest(Point2D point) {
        Point2D nearestPoint = null;
        double smallestDistance = Double.MAX_VALUE;
        for (Point2D currentPoint : orderedSet) {
            double distance = currentPoint.distanceTo(point);
            if (distance < smallestDistance) {
                smallestDistance = distance;
                nearestPoint = currentPoint;
            }
        }
        return nearestPoint;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        // TODO: delete following hardcoded path
        String filename = "/Users/seeta/projects/github/algorithms/src/main/resources/kdtree/horizontal8.txt";
        In in = new In(filename);
        PointSET brute = new PointSET();
        double smallestX = Double.MAX_VALUE;
        double smallestY = Double.MAX_VALUE;
        double largestX = Double.MIN_VALUE;
        double largestY = Double.MIN_VALUE;
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            if (x  < smallestX) smallestX = x;
            if (y  < smallestY) smallestY = y;
            if (x  > largestX) largestX = x;
            if (y  > largestY) largestY = y;
            Point2D p = new Point2D(x, y);
            brute.insert(p);
        }

        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        brute.draw();

        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius(0.001);

        RectHV rect = new RectHV(smallestX, smallestY, largestX, largestY);
        brute.range(rect);
        rect.draw();
    }
}