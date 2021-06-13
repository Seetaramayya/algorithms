import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class PointSET {
    private final TreeSet<Point2D> orderedSet = new TreeSet<>();

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
        if (p == null) throw new IllegalArgumentException("non null please");
        return orderedSet.contains(p);
    }

    // draw all points to standard draw O(N)
    public void draw() {
        for (Point2D currentPoint : orderedSet) {
            StdDraw.point(currentPoint.x(), currentPoint.y());
        }
    }

    // all points that are inside the rectangle (or on the boundary) O(N)
    public Iterable<Point2D> range(RectHV rectangle) {
        if (rectangle == null) throw new IllegalArgumentException("rectangle should not be null");
        List<Point2D> points = new ArrayList<>();
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
        if (point == null) throw new IllegalArgumentException("non null please");
        double smallestDistance = Double.POSITIVE_INFINITY;
        for (Point2D currentPoint : orderedSet) {
            double distance = currentPoint.distanceSquaredTo(point);
            if (distance < smallestDistance) {
                smallestDistance = distance;
                nearestPoint = currentPoint;
            }
        }
        return nearestPoint;
    }
}