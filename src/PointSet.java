
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class PointSet extends ArrayList<Point>{

  // PointSet data members here.




  // nested classes (if any) could go here

	


  /*************** PART OF LEVEL 3 FUNCTIONALITY ****/
  /**
  * TODO:  modify so resulting tree is as balanced
  * 		as possible.
  *
  *   Given trivial implementation just calls insert
  *      for each point, but does not control balance
  *      of tree.
  */
  public PointSet(Point [] pts){
    this();
    for(Point p : pts)
	insert(p);
  }

  
  /*************** LEVEL 1 FUNCTIONALITY ************/
  /**
  * TODO
  *   default constructor creates an empty tree.
  */
  public PointSet() {
    super();
  }
  /**
  * TODO
  * runtime requirement:  O(1)
  *  we get size for free from ArrayList
  public int size() {
    System.out.println("PointSet unimplemented method:  size()");
    return 0;
  }
  */

  /**
  * doesn't make sense for a non-tree representation
  public int height() {
    System.out.println("PointSet unimplemented method:  height()");
    return 0;
  }
  */
  /**
  * TODO
  */
  public Double minX(){
    Double min=null;
    for(Point p : this)
        min = (min==null || p.x() < min)? p.x() : min;
    return min;
  }
  /**
  * TODO
  */
  public Double minY(){
    Double min=null;
    for(Point p : this)
        min = (min==null || p.y() < min)? p.y() : min;
    return min;
  }
  /**
  * TODO
  */
  public Double maxX(){
    Double max=null;
    for(Point p : this)
        max = (max==null || p.x() > max)? p.x() : max;
    return max;
  }
  /**
  * TODO
  */
  public Double maxY(){
    Double max=null;
    for(Point p : this)
        max = (max==null || p.y() > max)? p.y() : max;
    return max;
  }

  /**
  * TODO
  * returns false if point pt already in set 
  */
  public boolean insert(Point pt) {
    if(contains(pt)) return false;
    add(pt);
    return true;
  }
  public boolean insert(double x, double y) {
    return insert(new Point(x,y));
  }

  /**
  * we get contains for free from ArrayList
  */
/***
  public boolean contains(Point p){
    System.out.println("PointSet unimplemented method:  contains()");
    return false;
  }
***/
  public boolean contains(double x, double y) {
    return contains(new Point(x,y));
  }



  /******************** LEVEL 2 FUNCTIONALITY ******/

  /**
  * TODO
  */
  public boolean delete(Point p) {
    return remove(p);
  }
  /**
  * for convenience
  */
  public boolean delete(double x, double y) {
    return delete(new Point(x,y));
  }

  /**
  * TODO
  */
  public void draw(){
    // StdDraw.setPenRadius(0.03);
    for(Point p : this)
        p.draw();

  }

  /***************** PART OF LEVEL 3 FUNCTIONALITY *****/
  /** Note that incremental enforcement of the size-balanced
  *     property is part of LEVEL 3.
  *   It requires modification to insert and delete.
  *******************************************************/
  /**
  * TODO
  *  returns point in tree closest to point p (by 
  *     Euclidean distance).
  *  if set empty, null is returned.
  */
  public Point nearest(Point p) {
    if(p==null || size()==0)
        return null;
    Double dmin = null;
    Point nn = null;  // nearest neighbor

    for(Point candidate : this){
        Double d2 = p.squaredDistanceTo(candidate);
        if(dmin == null || d2 < dmin){
            dmin = d2;
            nn = candidate;
        }
    }
    return nn;
  }

  /**
  * for convenience
  */
  public Point nearest(double x, double y) {
     return nearest(new Point(x,y));
  }



  /****************** BONUS METHODS ***************/
  /**
  * TODO
  *
  *   given points indicate the "southwest" and "northeast"
  *     corners of the query rectangle.
  *   populates Collection<Point> result with points in the
  *     rectangle.
  */
  public void rangeQuery(Point sw, Point ne,
                            Collection<Point> result) {
        for(Point p : this) 
            if(p.inside(sw, ne))
                result.add(p);
  }

  /**
  *  for convenience.  Just calls above method
  */
  public void rangeQuery(double minX, double maxX, 
                            double minY, double maxY, 
                            Collection<Point> result) {

	Point sw = new Point(minX, minY);
	Point ne = new Point(maxX, maxY);

	rangeQuery(sw, ne, result);
  }


  /**
  * TODO
  *   given points indicate the "southwest" and "northeast"
  *     corners of the query rectangle.
  *   given points indicate the "southwest" and "northeast"
  *     corners of the query rectangle.
  *   returns number of points within the rectangle (but NOT
  *     the points themselves).
  *   
  */
  public int rangeSize(Point sw, Point ne) {
        int n = 0;
        for(Point p : this) 
            if(p.inside(sw, ne))
                n++;
	return n;

  }
  public int rangeSize(double minX, double maxX, 
                            double minY, double maxY){

	Point sw = new Point(minX, minY);
	Point ne = new Point(maxX, maxY);

	return rangeSize(sw, ne);
  }

}
