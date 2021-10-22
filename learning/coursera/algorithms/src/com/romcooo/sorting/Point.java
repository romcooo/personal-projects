 package com.romcooo.sorting;

import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {
    private static final double EQUALITY_THRESHOLD = 0.0001d;

    private final double x;
    private final double y;

    // constructs the point (x, y)
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // draws this point
    public void draw() {
        StdDraw.point(x, y);
    }

    // draws the line segment from this point to that point
    public void drawTo(Point that) {
        StdDraw.line(x, y, that.x, that.y);
    }
    // string representation
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

    // compare two points by y-coordinates, breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (floatsAreEqual(this.y, that.y)) {
            if (floatsAreEqual(this.x, that.x)) {
                return 0;
            } else {
                return ((this.x < that.x) ? -1 : 1);
            }
        } else {
            return ((this.y < that.y) ? -1 : 1);
        }
    }

    // the slope between this point and that point
    public double slopeTo(Point that) {
//        StdOut.println(String.format("slopeTo: this = %s, that = %s", this.toString(), that.toString()));
        if (this.compareTo(that) == 0) {
//            StdOut.println("equal - negative infinity");
            return Double.NEGATIVE_INFINITY;
        } else if (floatsAreEqual(this.y, that.y)) {
//            StdOut.println("y equal - 0");
            return 0;
        } else if (floatsAreEqual(this.x, that.x)) {
//            StdOut.println("y not equal but x equal - positive infinity");
            return Double.POSITIVE_INFINITY;
        }
        double x0 = this.x;
        double x1 = that.x;
        double y0 = this.y;
        double y1 = that.y;

//        StdOut.println((y1 - y0) / (x1 - x0));

        return (y1 - y0) / (x1 - x0);
    }

    // compare two points by slopes they make with this point
    public Comparator<Point> slopeOrder()   {
        return new Sloparator();
    }

    private class Sloparator implements Comparator<Point> {
        @Override
        public int compare(Point o1, Point o2) {
            return Double.compare(slopeTo(o1), slopeTo(o2));
        }
    }

    private boolean floatsAreEqual(double a, double b) {
        return (a == b ||
                (abs(a - b) < EQUALITY_THRESHOLD));
    }

    private double abs(double a) {
        if (a >= 0) return a;
        else return - a;
    }

}
