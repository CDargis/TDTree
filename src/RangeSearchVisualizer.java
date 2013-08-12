/*************************************************************************
 *  Compilation:  javac RangeSearchVisualizer.java
 *  Execution:    java RangeSearchVisualizer < input.txt
 *                java Generator N | java RangeSearchVisualizer
 *  Dependencies: StdDraw.java Point.java TDTree.java StdIn.java
 *
 *  Read points from standard input and draw to standard draw.
 *  Also draw all of the points in the rectangle the user selects
 *  by dragging the mouse.
 *
 *  The range search results using the brute-force algorithm are drawn
 *  in red; the results using the kd-tree algorithms are drawn in blue.
 *
 *************************************************************************/

import java.util.*;

public class RangeSearchVisualizer {

    // add points into kd-tree one at a time and draw
    public static void main(String[] args) {
        StdDraw.show(0);

        // initialize the data structures with N points from standard input
        // PointSET brute = new PointSET();
        TDTree tdt = new TDTree();
        while (!StdIn.isEmpty()) {
            double x = StdIn.readDouble();
            double y = StdIn.readDouble();
            Point p = new Point(x, y);
            tdt.insert(p);
            // brute.insert(p);
        }

        ArrayList<Point> pointsInRange = new ArrayList<Point>();

        double x0 = 0.0, y0 = 0.0;      // initial endpoint of rectangle
        double x1 = 0.0, y1 = 0.0;      // current location of mouse
        boolean isDragging = false;     // is the user dragging a rectangle

        // draw the points
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        // brute.draw();

        while (true) {
            StdDraw.show(40);

            // no rectangle selected
            if (!StdDraw.mousePressed()) {
                isDragging = false;
                continue;
            }

            // user starts to drag a rectangle
            else if (!isDragging) {
                x0 = StdDraw.mouseX();
                y0 = StdDraw.mouseY();
                isDragging = true;
            }

            x1 = StdDraw.mouseX();
            y1 = StdDraw.mouseY();


            Point sw = new Point(Math.min(x0, x1), Math.min(y0, y1));
            Point ne = new Point(Math.max(x0, x1), Math.max(y0, y1));
            // draw the points
            StdDraw.clear();
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(.01);


            // brute.draw();

            // draw the rectangle
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius();


            // rect.draw();
            //   Insert code to draw rectangle


            /***
            // draw the range search results for brute-force data structure in red
            StdDraw.setPenRadius(.03);
            StdDraw.setPenColor(StdDraw.RED);

        
            for (Point p : brute.range(rect))
                p.draw();

            ***/

            // draw the range search results for kd-tree in blue
            StdDraw.setPenRadius(.02);
            StdDraw.setPenColor(StdDraw.BLUE);

            pointsInRange.clear();
            tdt.rangeQuery(sw, ne, pointsInRange);
            for (Point p : pointsInRange)
                p.draw();

            StdDraw.show(40);
        }
    }
}
