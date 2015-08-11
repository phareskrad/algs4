package assignments;

import algs4.Point2D;
import algs4.Queue;
import algs4.RedBlackBST;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.TreeSet;

/**
 * Created by Leon on 7/22/15.
 */
public class PointSet {
    private TreeSet<Point2D> points;

    public PointSet() {
        points = new TreeSet<Point2D>();
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int size() {
        return points.size();
    }

    public void insert(Point2D p) {
        if (p == null) throw new NullPointerException();
        points.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException();
        return points.contains(p);
    }

    public void draw() {
        if (isEmpty()) return;
        for (Point2D p : points) {
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException();
        if (isEmpty()) return null;
        Queue<Point2D> inside = new Queue<Point2D>();

        for (Point2D p : points) {
            if (rect.contains(p)) inside.enqueue(p);
        }

        if (inside.isEmpty()) return null;
        return inside;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new NullPointerException();
        if (isEmpty()) return null;
        double min = Double.MAX_VALUE;
        Point2D nearest = p;

        for (Point2D point : points) {
            if (point.distanceSquaredTo(p) < min) {
                nearest = point;
                min = point.distanceSquaredTo(p);
            }
        }
        return nearest;
    }


}
