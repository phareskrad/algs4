package assignments;

/**
 * Created by Leon on 7/15/15.
 */

import algs4.DoublingRatio;
import stdlib.StdDraw;

import java.util.Arrays;
import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new LineOrder();       // YOUR DEFINITION HERE

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
        if (compareTo(that) == 0) return Double.NEGATIVE_INFINITY;
        if (this.y == that.y) return 0.0;
        if (this.x == that.x) return Double.POSITIVE_INFINITY;
        return (double)(that.y - this.y) / (that.x - this.x);
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        if (this.y < that.y) return -1;
        if (this.y > that.y) return 1;
        if (this.x < that.x) return -1;
        if (this.x > that.x) return 1;
        return 0;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    private class LineOrder implements Comparator<Point> {
        public int compare(Point p, Point q) {
            if (slopeTo(p) < slopeTo(q)) return -1;
            if (slopeTo(p) > slopeTo(q)) return 1;
            return 0;
        }
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
        Point p0 = new Point(0, 0);
        Point p1 = new Point(5, 2);
        Point p2 = new Point(2, 1);
        Point[] p = new Point[3];
        p[0] = p0;
        p[1] = p1;
        p[2] = p2;
        Arrays.sort(p);
        System.out.print(p1.slopeTo(p2));

    }
}