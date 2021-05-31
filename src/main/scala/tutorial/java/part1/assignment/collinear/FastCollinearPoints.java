package tutorial.java.part1.assignment.collinear;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {
    private final LineSegment[] segments;

    // finds all line segments containing 4 or more points
    // Seeta: very bad, why so many loops
    public FastCollinearPoints(Point[] input) {
        if (input == null) {
            throw new IllegalArgumentException("it should not be null");
        }
        Point[] unmodifiedInput = new Point[input.length];
        Point[] points = new Point[input.length];
        for (int i = 0; i < input.length; i++) {
            Point point = input[i];
            if (point == null) {
                throw new IllegalArgumentException("supplied points should not be null");
            }
            points[i] = point;
            unmodifiedInput[i] = point;
        }

        List<Point[]> list = new ArrayList<>();
        for (Point p : unmodifiedInput) {
            int counter = 0;
            int duplicates = 0;
            double previousSlope = Double.NEGATIVE_INFINITY; // slope to the same point is always negative infinity
            Arrays.sort(points, p.slopeOrder());

            // O(N^2) with above loop
            for (int j = 0; j < points.length; j++) {
                Point q = points[j];
                if (p.compareTo(q) == 0) duplicates += 1;
                if (duplicates > 1) throw new IllegalArgumentException("duplicates are not allowed");
                double currentSlope = p.slopeTo(q);
                if (previousSlope == currentSlope) {
                    counter++;
                } else {
                    if (counter >= 2) {
                        int previousIndex = j - 1;

                        Point[] segmentPoints = createSegmentPoints(previousIndex - counter, previousIndex, p, points);
                        if (!exists(list, segmentPoints)) {
                            list.add(segmentPoints);
                        }
                    }
                    counter = 0;
                }
                previousSlope = currentSlope;

            }
            if (counter >= 2) {
                int previousIndex = points.length - 1;
                Point[] segmentPoints = createSegmentPoints(previousIndex - counter, previousIndex, p, points);
                if (!exists(list, segmentPoints)) {
                    list.add(segmentPoints);
                }
            }
        }

        segments = new LineSegment[list.size()];

        int counter = 0;
        for (Point[] segmentPoints : list) {
            segments[counter++] = new LineSegment(segmentPoints[0], segmentPoints[segmentPoints.length - 1]);
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
