package com.romcooo.sorting;

import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.LinkedList;

public class BruteCollinearPoints {
    private final Point[] points;
    private final LinkedList<LineSegment> lineSegments = new LinkedList<>();
    private boolean computed = false;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Argument cannot be null.");
        }
        for (Point point : points) {
            if (point == null) {
                throw new IllegalArgumentException("argument can't be null");
            }
        }
        this.points = points;
    }

    public int numberOfSegments() {
        Arrays.sort(points);
        if (points.length < 4) {
            StdOut.println("not enough points, returning 0");
            return 0;
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
        computed = true;
        return counter;
    }

    public LineSegment[] segments() {
        if (!computed) {
            numberOfSegments();
        }
        LineSegment[] ls = new LineSegment[lineSegments.size()];
        return lineSegments.toArray(ls);
    }

    private boolean compareAll(Point... points) {
        if (points.length < 3) {
            throw new IllegalArgumentException("Can't compare less than 2 points");
        }
        for (int i = 0; i < points.length - 2; i++) {
            for (int j = i + 1; j < points.length - 1; j++) {
                for (int k = j + 1; k < points.length; k++) {
//                    StdOut.println("comparing points: " + points[i] + points[j] + points[k]);
                    double slopeOrder = points[i].slopeOrder().compare(points[j], points[k]);
//                    StdOut.println("slopeOrder: " + slopeOrder);
                    if (slopeOrder != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
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
