package com.romcooo.sorting;

import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.LinkedList;

public class FastCollinearPoints {
    private Point[] points;
    private boolean computed = false;

    private LinkedList<LineSegment> lineSegments = new LinkedList<>();

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("argument can't be null");
        }
        for (Point point : points) {
            if (point == null) {
                throw new IllegalArgumentException("argument can't be null");
            }
        }
        this.points = points;
    }

    /**
     * First, sort the array by y/x.
     * Then, for each point P, create a copy of the rest of the array (not including P or any points preceding it in the y/x order) and sort it
     *  by the slopes each other point creates with the given point P.
     * Then, iterate through the array of points A[i], keeping a counter c starting at 1 for the first point A[0],
     *  and the slope that the last point A[i-1] had with P.
     * If the slope that A[i] has with P is equal, increase the counter by 1.
     * Otherwise, reset the counter to 1.
     * If the counter reaches 4 after checking the slope of a point L, store a reference to that point L.
     * While the next point A[i] in the array has the same slope again, keep increasing the counter, and update the reference so that L points to the latest matching point.
     * This will make sure that the longest line segment is reached. Once the next point A[i] doesn't match the slope,
     *  the last reference L will create a line with point P containing c collinear points.
     * @return
     */
    public int numberOfSegments() {
//        StdOut.println(String.format("Before sorting: %s", Arrays.toString(points)));
        Arrays.sort(points);
//        StdOut.println(String.format("After sorting by y/x: %s", Arrays.toString(points)));
        int lineCounter = 0;
        // Start with the first point, but ignore the last 3 points, as they cannot create a line containing 4+ points.
        for (int i = 0; i < points.length - 3; i++) {
            Point p = points[i];
            // comparing others to point p[i]
            // copy the old array for sorting, accounting for the shorter length by subtracting the count i of previous points (and 1 for the current one)
            Point[] a = new Point[points.length - 1 - i];
            int c = 0;
            // i + 1 to not compare to itself
            for (int j = i + 1; j < points.length; j++) {
                Point point = points[j];
                if (point.compareTo(p) != 0) {
                    a[c++] = point;
                } else {
                    throw new IllegalArgumentException("Repeated point");
                }
            }
            Arrays.sort(a, 0, a.length, p.slopeOrder());
//            StdOut.println(String.format("After sorting for %s: %s", p.toString(), Arrays.toString(a)));

            c = 2;
            double slope = p.slopeTo(a[0]);

            for (int j = 1; j < a.length; j++) {
                if (slope == p.slopeTo(a[j])) {
                    c++;
                } else {
                    c = 2;
                    slope = p.slopeTo(a[j]);
                }
                // If this is the 4th point on a line, add a new line segment.
                if (c == 4) {
                    lineSegments.add(new LineSegment(p, a[j]));
                    lineCounter++;
                }
                // If this is the 5th+ point on the line, replace the last reference to only keep the longest line in the list.
                if (c > 4) {
                    lineSegments.removeLast();
                    lineSegments.add(new LineSegment(p, a[j]));
                }
            }

        }

        computed = true;
        return lineCounter;
    }

    public LineSegment[] segments() {
        if (!computed) {
            numberOfSegments();
        }
        LineSegment[] ls = new LineSegment[lineSegments.size()];
        return lineSegments.toArray(ls);
    }

    public static void main(String[] args) {

        Point p1 = new Point(2, 2);
        Point p2 = new Point(3, 5);
        Point p3 = new Point(4, 3);
        Point p4 = new Point(4, 8);
        Point p5 = new Point(5, 6);
        Point p6 = new Point(6, 4);
        Point p7 = new Point(7, 2);
        Point p8 = new Point(8, 5);

        Point[] points = {p1, p2, p3, p4, p5, p6, p7, p8};

        FastCollinearPoints f = new FastCollinearPoints(points);
        StdOut.println(f.numberOfSegments());
        StdOut.println(Arrays.toString(f.segments()));

    }

}
