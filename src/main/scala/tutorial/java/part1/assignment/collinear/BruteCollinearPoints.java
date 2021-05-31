package tutorial.java.part1.assignment.collinear;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    private final LineSegment[] segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] input) {
        if (input == null) {
            throw new IllegalArgumentException("it should not be null");
        }
        Point[] points = input.clone();
        List<Point> inputPoints = new ArrayList<>();
        // O(N)
        for (Point p : points) {
            if (p != null) inputPoints.add(p);
        }
        if (points.length != inputPoints.size()) throw new IllegalArgumentException("null points are not allowed!");

        Arrays.sort(points); // O(N)
        int count = countUniqElements(points); // O(N)
        if (points.length != count) throw new IllegalArgumentException("supplied points should not be null and must be not repeated");

        List<Point[]> list = new ArrayList<>();

        // O(N^4)
        // Seeta: Does not work for with segment size 5 or above
        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            for (int j = i + 1; j < points.length; j++) {
                Point q = points[j];
                double pq = p.slopeTo(q);
                for (int k = j + 1; k < points.length; k++) {
                    Point r = points[k];
                    double pr = p.slopeTo(r);
                    for (int w = k + 1; w < points.length; w++) {
                        Point s = points[w];
                        double ps = p.slopeTo(s);
                        if (pq == pr && pq == ps) {
                            Arrays.sort(points, i, w);
                            Point[] segmentPoints = createSegmentPoints(i, w, p, points);
                            if (!exists(list, segmentPoints)) {
                                list.add(segmentPoints);
                            }
//                            list.add(new tutorial.java.part1.assignment.collinear.LineSegment(points[i], points[w])); // Set is better but can not use it
                        }
                    }
                }
            }
        }

        segments = new LineSegment[list.size()];
        int i = 0;
        for (Point[] segment : list) {
            segments[i++] = new LineSegment(segment[0], segment[segment.length - 1]);
        }
    }

    private Point[] createSegmentPoints(int fromIndex, int toIndex, Point p, Point[] points) {
        // Seeta: ( 5 - 3) + 1 + 1 = 4
        Point[] segmentPoints = new Point[toIndex - fromIndex + 1 + 1];
        segmentPoints[0] = p;
        for (int k = 1; k < segmentPoints.length; k++) {
            segmentPoints[k] = points[fromIndex++];
        }
        Arrays.sort(segmentPoints);
        return segmentPoints;
    }

    private boolean exists(List<Point[]> list, Point[] segmentPoints) {
        boolean result = false;
        for (Point[] previousPath : list) {
            if (bothSame(previousPath, segmentPoints)) {
                result = true;
                break;
            }
        }
        return result;
    }

    private boolean bothSame(Point[] a, Point[] b) {
        boolean result = true;
        if (a.length != b.length) {
            return false;
        } else {
            for (int i = 0; i < a.length; i++) {
                if (a[i].compareTo(b[i]) != 0) {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return Arrays.copyOf(segments, segments.length);
    }

    // Seeta: Do we need to run different loops?
    private int countUniqElements(Point[] points) {
        if (points.length <= 1) {
            return points.length;
        } else {
            int count = 1;
            for (int i = 1; i < points.length; i++) {
                Point currentPoint = points[i];
                Point previousPoint = points[i - 1];
                if (currentPoint != null && currentPoint.compareTo(previousPoint) > 0) count += 1;
            }
            return count;
        }
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