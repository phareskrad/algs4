package assignments;

import algs4.Point2D;
import algs4.Queue;
import org.omg.CORBA.PUBLIC_MEMBER;
import stdlib.StdDraw;
import stdlib.StdIn;
import stdlib.StdOut;

import java.util.Comparator;

/**
 * Created by Leon on 7/22/15.
 */
public class KdTree {
    private static class Node {
        private Point2D p;
        private RectHV rect;
        private Node lb;
        private Node rt;

        public Node(Point2D p) {
            this.p = p;
        }
    }

    private Node root;
    private int N = 0;

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void insert(Point2D p) {
        if (p == null) throw new NullPointerException();
        root = insert(root, p, 0, new RectHV(0, 0, 1, 1));
        N++;
    }

    private Node insert(Node node, Point2D p, int level, RectHV rect) {
        if (node == null) {
            Node n = new Node(p);
            n.rect = rect;
            return n;
        }
        int cmp = compare(node, p, level);
        if (cmp < 0) {
            RectHV lb = divide(rect, node.p, level, -1);
            node.lb = insert(node.lb, p, level + 1, lb);
        }
        else if (cmp > 0) {
            RectHV rt = divide(rect, node.p, level, 1);
            node.rt = insert(node.rt, p, level + 1, rt);
        }
        return node;
    }

    private RectHV divide(RectHV rect, Point2D p, int level, int orient) {
        if (level % 2 == 0) {
            double xmin = orient > 0 ? p.x() : rect.xmin();
            double xmax = orient > 0 ? rect.xmax() : p.x();
            return new RectHV(xmin, rect.ymin(), xmax, rect.ymax());
        }
        else {
            double ymin = orient > 0 ? p.y() : rect.ymin();
            double ymax = orient > 0 ? rect.ymax() : p.y();
            return new RectHV(rect.xmin(), ymin, rect.xmax(), ymax);
        }
    }

    private int compare(Node node, Point2D p, int level) {
        Comparator cmp;
        if (level % 2 == 0) cmp = Point2D.X_ORDER;
        else cmp = Point2D.Y_ORDER;
        return cmp.compare(p, node.p);
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException();
        return contains(root, p, 0);
    }

    private boolean contains(Node node, Point2D p, int level) {
        if (node == null) return false;
        int cmp = compare(node, p , level);
        if (cmp < 0) return contains(node.lb, p, level + 1);
        else if (cmp > 0) return contains(node.rt, p, level + 1);
        else return true;
    }

    public void draw() {
        if (isEmpty()) return;
        //StdDraw.square(0.5, 0.5, 0.5);
        draw(root, 0, new Point2D(0, 1), -1);
    }

    private void draw(Node node, int level, Point2D previous, double orient) {
        if (node == null) return;
        StdDraw.setPenRadius(0.01);
        node.p.draw();
        //node.rect.draw();
        drawLine(node, level);
        draw(node.lb, level + 1, node.p, -1);
        draw(node.rt, level + 1, node.p, 1);
    }

    private void drawLine(Node node, int level) {
        StdDraw.setPenRadius(0.002);
        if (level % 2 == 0) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException();
        if (isEmpty()) return null;

        Queue<Point2D> q = new Queue<Point2D>();

        findPoints(root, rect, q);
        if (q.isEmpty()) return null;
        return q;
    }

    private void findPoints(Node node, RectHV rect, Queue<Point2D> q){
        if (rect.contains(node.p)) q.enqueue(node.p);
        if (node.lb != null && rect.intersects(node.lb.rect)) findPoints(node.lb, rect, q);
        if (node.rt != null && rect.intersects(node.rt.rect)) findPoints(node.rt, rect, q);
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new NullPointerException();
        if (isEmpty()) return null;
        Point2D nearest = nearest(root, p, new Point2D(100, 100));
        return nearest;
    }

    private Point2D nearest(Node node, Point2D p, Point2D nearest) {
        double d = p.distanceSquaredTo(nearest);
        if (p.distanceSquaredTo(node.p) < d) {
            nearest = node.p;
            print(nearest);
        }
        if (node.lb != null && node.lb.rect.distanceSquaredTo(p) < d) {
            nearest = nearest(node.lb, p, nearest);
            //print(nearest);
            d = p.distanceSquaredTo(nearest);
            if (node.rt != null && node.rt.rect.distanceSquaredTo(p) < d) {
                nearest = nearest(node.rt, p, nearest);
                //print(nearest);
            }
        }
        if (node.rt != null && node.rt.rect.distanceSquaredTo(p) < d) {
            nearest = nearest(node.rt, p, nearest);
            //print(nearest);
            d = p.distanceSquaredTo(nearest);
            if (node.lb != null && node.lb.rect.distanceSquaredTo(p) < d) {
                nearest = nearest(node.lb, p, nearest);
                //print(nearest);
            }
        }
        return nearest;
    }

    private void print(Point2D p) {
        System.out.println(p);
    }


    public static void main(String[] args) {
        //KdTree t = new KdTree();
        //double[] input = StdIn.readAllDoubles();
        //for (int i = 0; i < input.length; i+=2) {
        //    t.insert(new Point2D(input[i], input[i + 1]));
        //}
        //t.draw();
        //System.out.println(t.contains(new Point2D(input[4], input[5])));

        RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        StdDraw.show(0);
        KdTree kdtree = new KdTree();
        while (true) {
            if (StdDraw.mousePressed()) {
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();
                System.out.printf("%8.6f %8.6f\n", x, y);
                Point2D p = new Point2D(x, y);
                if (rect.contains(p)) {
                    StdOut.printf("%8.6f %8.6f\n", x, y);
                    kdtree.insert(p);
                    StdDraw.clear();
                    kdtree.draw();
                }
            }
            StdDraw.show(50);
        }

    }
}



