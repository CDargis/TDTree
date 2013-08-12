/*************************************************************************
 *  Compilation:  javac NearestNeighborVisualizer.java
 *  Execution:    java NearestNeighborVisualizer < input.txt
 *                java Generator N | java NearestNeighborVisualizer
 *  Dependencies: StdDraw.java PointSet.java TDTree.java Point.java
 *
 *  Read points from standard input and draw to standard draw.
 *  Highlight the closest point to the mouse.
 *
 *  The nearest neighbor according to the brute-force algorithm is drawn
 *  in red; the nearest neighbor using the kd-tree algorithm is drawn in blue.
 *
 *************************************************************************/

public class NearestNeighborVisualizer {

    public static void main(String[] args) {

        StdDraw.setCanvasSize(800, 800);
        StdDraw.show(0);

        // initialize the two data structures with point from standard input

        PointSet brute = new PointSet();
        TDTree tdt = new TDTree();
        while (!StdIn.isEmpty()) {
            double x = StdIn.readDouble();
            double y = StdIn.readDouble();
            Point p = new Point(x, y);
            System.out.println("Read Point: " + p);
            tdt.insert(p);
            brute.insert(p);
        }

        System.out.println("brute contents:  " + brute);

        StdDraw.setPenRadius();
        // StdDraw.setPenRadius(.007);
        while (true) {

            // the location (x, y) of the mouse
            double x = StdDraw.mouseX();
            double y = StdDraw.mouseY();
            Point query = new Point(x, y);

            // draw all of the points
            StdDraw.clear();
            StdDraw.setPenColor(StdDraw.BLACK);
            brute.draw();

            // draw in red the nearest neighbor according to the brute-force algorithm
            // StdDraw.setPenRadius(.03);
            // StdDraw.setPenColor(StdDraw.RED);
            brute.nearest(query).draw(StdDraw.RED);
            // StdDraw.setPenRadius(.02);

            // draw in blue the nearest neighbor according to the kd-tree algorithm
            StdDraw.setPenColor(StdDraw.BLUE);
            // tdt.nearest(query).draw();
            StdDraw.show(0);
            StdDraw.show(40);
        }
    }
}
