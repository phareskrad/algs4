package assignments;

import algs4.Point2D;
import stdlib.In;
import stdlib.StdDraw;

/**
 * Created by Leon on 7/23/15.
 */
public class NearestNeighborVisualizer {

    public static void main(String[] args) {
        //String filename = args[0];
        In in = new In("./resources/kdtree/circle10000.txt");

        StdDraw.show(0);

        // initialize the two data structures with point from standard input
        PointSet brute = new PointSet();
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            brute.insert(p);
        }

        System.out.println(kdtree.contains(new Point2D(0.206107,0.904508)));

        double x = 0;
        double y = 0;

        while (true) {

            // the location (x, y) of the mouse
            if (StdDraw.mousePressed()) {
                x = StdDraw.mouseX();
                y = StdDraw.mouseY();
            }
            else {
                continue;
            }
            Point2D query = new Point2D(x, y);


            // draw all of the points
            StdDraw.clear();
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(.01);
            brute.draw();

            StdDraw.setPenColor(StdDraw.GREEN);
            StdDraw.setPenRadius(.01);
            query.draw();
            System.out.println(query);

            // draw in red the nearest neighbor (using brute-force algorithm)
            StdDraw.setPenRadius(.03);
            StdDraw.setPenColor(StdDraw.RED);
            brute.nearest(query).draw();
            StdDraw.setPenRadius(.02);
            //System.out.println("brute:" + brute.nearest(query) + "dist:" + brute.nearest(query).distanceSquaredTo(query));

            // draw in blue the nearest neighbor (using kd-tree algorithm)
            StdDraw.setPenColor(StdDraw.BLUE);
            kdtree.nearest(query).draw();
            System.out.println("");
            //System.out.println("kdtree:" + kdtree.nearest(query) + "dist:" + kdtree.nearest(query).distanceSquaredTo(query));
            StdDraw.show(0);
            StdDraw.show(40);
        }
    }
}
