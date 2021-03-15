package com.romcooo.sorting;

import java.util.Comparator;

public class Point implements Comparable<Point> {
    private double x, y;

    // constructs the point (x, y)
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // draws this point
    public void draw() {

    }

    // draws the line segment from this point to that point
    public void drawTo(Point that) {

    }
    // string representation
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

    // compare two points by y-coordinates, breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (this.y == that.y) {
            if (this.x == that.x) {
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
        if (this.compareTo(that) == 0) {
            return Double.NEGATIVE_INFINITY;
        } else if (this.y == that.y) {
            return 0;
        } else if (this.x == that.x) {
            return Double.POSITIVE_INFINITY;
        }
        double x0 = this.x;
        double x1 = that.x;
        double y0 = this.y;
        double y1 = that.y;

        return (y1 - y0) / (x1 - x0);
    }

    // compare two points by slopes they make with this point
    public Comparator<Point> slopeOrder()   {
        return new Sloparator();
    }

    private class Sloparator implements Comparator<Point> {
        @Override
        public int compare(Point o1, Point o2) {
            return (int) slopeTo(o1) - (int) slopeTo(o2);
        }
    }

}
