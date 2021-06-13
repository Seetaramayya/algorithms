import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.HashSet;

public class KdTree {
    private Node root;
    private int size;

    private static class Node {
        private Node left;
        private Node right;
        private final int level;
        private final Point2D point;

        public Node(Point2D point, int level) {
            this.point = point;
            this.level = level;
        }

        public boolean isEvenLevel() {
            return level % 2 == 0;
        }

        @Override
        public String toString() {
            return point.toString();
        }
    }

    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("point should not be null");
        if (root == null) root = new Node(p, 0);
        else {
            Node currentNode = root;
            while (true) {
                // if p < currentNode goLeft
                if (isLessThan(currentNode, p)) {
                    if (currentNode.left == null) {
                        currentNode.left = new Node(p, currentNode.level + 1);
                        break;
                    } else {
                        currentNode = currentNode.left;
                    }
                } else {
                    if (currentNode.right == null) {
                        currentNode.right = new Node(p, currentNode.level + 1);
                        break;
                    } else {
                        currentNode = currentNode.right;
                    }
                }
            }
        }
        size += 1;
    }

    private boolean isLessThan(Node node, Point2D p) {
        if (node.isEvenLevel()) return p.x() < node.point.x();
        else return p.y() < node.point.y();
    }

    private Node find(Node current, Point2D p) {
        if (current == null) return null;
        else if (current.point == p) return current;
        else {
            if (isLessThan(current, p)) return find(current.left, p);
            else return find(current.right, p);
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        return find(root, p) != null;
    }

    // draw all points to standard draw
    public void draw() {
        drawRecursive(root, 0.0, 0.0, 1.0, 1.0);
    }

    private void drawRecursive(Node current, double x1, double y1, double x2, double y2) {
        if (current != null) {
            System.out.println("Current node is " + current.point);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            StdDraw.point(current.point.x(), current.point.y());
            StdDraw.setPenRadius(0.001);

            if (current.isEvenLevel()) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(current.point.x(), y1, current.point.x(), y2);
                drawRecursive(current.left, x1, y1, current.point.x(), y2);
                drawRecursive(current.right, current.point.x(), y1, x2, y2);
            } else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(x1, current.point.y(), x2, current.point.y());
                drawRecursive(current.left, x1, y1, x2, current.point.y());
                drawRecursive(current.right, x1, current.point.y(), x2, y2);
            }
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rectangle) {
        if (root == null) return new HashSet<>();
        else {
            RectHV rootRect = new RectHV(0.0, 0.0, 1.0, 1.0);
            HashSet<Point2D> pointsInRect = new HashSet<>();
            range(root, rootRect, rectangle, pointsInRect);
            return pointsInRect;
        }
    }

    private void range(Node currentNode, RectHV currentRectangle, RectHV queryRectangle, HashSet<Point2D> points) {
        if (currentNode != null && currentRectangle.intersects(queryRectangle)) {
            if (queryRectangle.contains(currentNode.point)) points.add(currentNode.point);
            if (currentNode.isEvenLevel()) {
                RectHV leftRectangle = new RectHV(currentRectangle.xmin(), currentRectangle.ymin(), currentNode.point.x(), currentRectangle.ymax());
                RectHV rightRectangle = new RectHV(currentNode.point.x(), currentRectangle.ymin(), currentRectangle.xmax(), currentRectangle.ymax());
                range(currentNode.left, leftRectangle, queryRectangle, points);
                range(currentNode.right, rightRectangle, queryRectangle, points);
            } else {
                RectHV bottomRectangle = new RectHV(currentRectangle.xmin(), currentRectangle.ymin(), currentRectangle.xmax(), currentNode.point.y());
                RectHV topRectangle = new RectHV(currentRectangle.xmin(), currentNode.point.y(), currentRectangle.xmax(), currentRectangle.ymax());
                range(currentNode.left, bottomRectangle, queryRectangle, points);
                range(currentNode.right, topRectangle, queryRectangle, points);
            }
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (root == null) return null;
        else {
            return findNearestPoint(root, root.point, p);
        }
    }

    private Point2D findNearestPoint(Node current, Point2D lastKnownNearestPoint, Point2D p) {
        if (current == null) return lastKnownNearestPoint;
        else {
            double distance = current.point.distanceTo(p);
            double previousNearestDistance = lastKnownNearestPoint.distanceTo(p);
            Point2D currentNearestPoint = distance < previousNearestDistance ? current.point : lastKnownNearestPoint;
            if (isLessThan(current, p)) {
                return findNearestPoint(current.left, currentNearestPoint, p);
            } else {
                return findNearestPoint(current.right, currentNearestPoint, p);
            }
        }
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        KdTree kdTree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdTree.insert(p);
        }

        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        kdTree.draw();

        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius(0.001);
    }
}