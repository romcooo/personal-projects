 package com.romcooo.sorting;

import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.LinkedList;

public class BruteCollinearPoints {
    private final Point[] points;
    private final LinkedList<LineSegment> lineSegments = new LinkedList<>();
    private int numberOfSegments = 0;
    private boolean computed = false;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Argument cannot be null nor a duplicate of an already entered point.");
        }
        this.points = points.clone();
        StdOut.println(Arrays.toString(this.points));
        Arrays.sort(this.points);
        StdOut.println(Arrays.toString(this.points));
        Point lastPoint = null;
        for (Point point : this.points) {
            if (point == null || (lastPoint != null && point.compareTo(lastPoint) == 0)) {
                throw new IllegalArgumentException("argument can't be null");
            }
            StdOut.println(point);
            lastPoint = point;
        }
    }

    public int numberOfSegments() {
        if (!computed) {
            compute();
        }
        return numberOfSegments;
    }

    public LineSegment[] segments() {
        if (!computed) {
            compute();
        }
        LineSegment[] ls = new LineSegment[lineSegments.size()];
        return lineSegments.toArray(ls);
    }

    private boolean compareAll(Point... comparedPoints) {
        if (comparedPoints.length < 3) {
            throw new IllegalArgumentException("Can't compare less than 2 points");
        }
        for (int i = 0; i < comparedPoints.length - 2; i++) {
            for (int j = i + 1; j < comparedPoints.length - 1; j++) {
                for (int k = j + 1; k < comparedPoints.length; k++) {
//                    StdOut.println("comparing points: " + points[i] + points[j] + points[k]);
                    double slopeOrder = comparedPoints[i].slopeOrder().compare(comparedPoints[j], comparedPoints[k]);
//                    StdOut.println("slopeOrder: " + slopeOrder);
                    if (slopeOrder != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void compute() {
        Arrays.sort(points);
        if (points.length < 4) {
            StdOut.println("not enough points, returning 0");
            return;
        }
        int counter = 0;
        for (int i0 = 0; i0 < points.length; i0++) {
            for (int i1 = i0 + 1; i1 < points.length; i1++) {
                for (int i2 = i1 + 1; i2 < points.length; i2++) {
                    for (int i3 = i2 + 1; i3 < points.length; i3++) {
                        if (compareAll(points[i0], points[i1], points[i2], points[i3])) {
                            counter++;
                            lineSegments.add(new LineSegment(points[i0], points[i3]));
                        }
                    }
                }
            }
        }
        numberOfSegments = counter;
        computed = true;
    }

    public static void main(String[] args) {
        Point p1 = new Point(1, 1);
        Point p2 = new Point(2, 2);
        Point p3 = new Point(3, 3);
        Point p4 = new Point(4, 4);
        Point p5 = new Point(5, 6);
        Point[] points = {p1, p2, p3, p4, p5};
        BruteCollinearPoints b = new BruteCollinearPoints(points);
        StdOut.println(String.format("numberOfSegments: %s", b.numberOfSegments()));
        StdOut.println(String.format("segments: %s", Arrays.toString(b.segments())));
    }
}
