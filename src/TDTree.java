
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class TDTree {

  // TDTree data members here.
	private TreeNode root;
	private int size;
	
	int nodesTouchedOnNearest;
	
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
  public TDTree(Point [] pts){
	  this();
	 // for(Point p : pts)
	  //insert(p);
	  this.size = pts.length;
	  //TreeNode newRoot = buildBalanced(new ArrayList<Point>(Arrays.asList(pts)), true);
	  ArrayList<Point> xSorted = new ArrayList<Point>(Arrays.asList(pts));
	  ArrayList<Point> ySorted = new ArrayList<Point>(Arrays.asList(pts));
	  Collections.sort(xSorted, new PointComparator(true));
	  Collections.sort(ySorted, new PointComparator(false));
	  TreeNode newRoot = buildBalanced(xSorted, ySorted, true);
	  recalculateData(newRoot);
	  root = newRoot;
  }

  public TDTree(Point[] pts, boolean cd) {
	  this();
	  // for(Point p : pts)
	  //insert(p);
	  this.size = pts.length;
	  //TreeNode newRoot = buildBalanced(new ArrayList<Point>(Arrays.asList(pts)), true);
	  ArrayList<Point> xSorted = new ArrayList<Point>(Arrays.asList(pts));
	  ArrayList<Point> ySorted = new ArrayList<Point>(Arrays.asList(pts));
	  Collections.sort(xSorted, new PointComparator(true));
	  Collections.sort(ySorted, new PointComparator(false));
	  TreeNode newRoot = buildBalanced(xSorted, ySorted, cd);
	  recalculateData(newRoot);
	  root = newRoot;
  }
  
  /**
   * 
   * @param xOrdered -- Points ordered on x
   * @param yOrdered  -- Points ordered on y
   * @param cd  -- cutting dimension
   * @return  -- Root node
   * This method builds a balanced tree(or sub-tree) in nlogn time. It does this by creating a root
   * node based on the cutting dimension. It then splits up each list into sub-lists, for x-left, y-left,
   * x-right, y-right. 
   */
  private TreeNode buildBalanced(List<Point> xOrdered, List<Point> yOrdered, boolean cd) {
	  TreeNode t = null;
	  if(cd && !xOrdered.isEmpty()) {
		  if(xOrdered.size() == 1) return new TreeNode(xOrdered.get(0));
		  int median = xOrdered.size() / 2;
		  t = new TreeNode(xOrdered.get(median));
		  
		  List<Point> xL = xOrdered.subList(0, median);
		  List<Point> xR = xOrdered.subList(median + 1, xOrdered.size());
		  List<Point> yL = new ArrayList<Point>(xOrdered.size());
		  List<Point> yR = new ArrayList<Point>(xOrdered.size());
		  for(int i = 0; i < yOrdered.size(); i++) {
			  if(yOrdered.get(i).x() < t.p.x()) {
				  yL.add(yOrdered.get(i));			  
			  }
			  else if(yOrdered.get(i).x() > t.p.x()) {
				  yR.add(yOrdered.get(i));
			  }
			  else {        // They must be equal, cut on y
				  if(yOrdered.get(i).y() < t.p.y()) {
					  yL.add(yOrdered.get(i));
				  }
				  else if(yOrdered.get(i).y() > t.p.y()) {
					  yR.add(yOrdered.get(i));
				  }
			  }
		  }
		  t.left = buildBalanced(xL, yL, !cd);
		  if(median >= 1) t.right = buildBalanced(xR, yR, !cd);
	  }
	  else if(!cd && !yOrdered.isEmpty()){
		  if(yOrdered.size() == 1) return new TreeNode(yOrdered.get(0));
		  int median = yOrdered.size() / 2;
		  t = new TreeNode(yOrdered.get(median));
		  
		  List<Point> xL = new ArrayList<Point>(yOrdered.size());
		  List<Point> xR = new ArrayList<Point>(yOrdered.size());
		  List<Point> yL = yOrdered.subList(0, median);
		  List<Point> yR = yOrdered.subList(median + 1, xOrdered.size());
		  
		  for(int i = 0; i < xOrdered.size(); i++) {
			  if(xOrdered.get(i).y() < t.p.y()) {
				  xL.add(xOrdered.get(i));
			  }
			  else if(xOrdered.get(i).y() > t.p.y()) {
				  xR.add(xOrdered.get(i));
			  }
			  else {                                   // They must be equal, cut on x
				  if(xOrdered.get(i).x() < t.p.x()) {
					  xL.add(xOrdered.get(i));			  
				  }
				  else if(xOrdered.get(i).x() > t.p.x()) {
					  xR.add(xOrdered.get(i));
				  }
			  }
		  }
		  t.left = buildBalanced(xL, yL, !cd);
		  if(median >= 1) t.right = buildBalanced(xR, yR, !cd);
	  }
	  
	  return t;
  }
  
  /*
  private TreeNode buildBalanced(Point[] points, boolean even) {
	  if(points.length == 0) return null;
	  if(points.length == 1) return new TreeNode(points[0]);
	  Arrays.sort(points, new PointComparator(even));
	  int median = points.length / 2;
	  ArrayList<Point> list;
	  
	  TreeNode t = new TreeNode(points[median]);
	  Point[] left  = Arrays.copyOfRange(points, 0, median);
	  Point[] right = Arrays.copyOfRange(points, median + 1, points.length);
	  
	  t.left = buildBalanced(left, !even);
	  t.right = buildBalanced(right, !even);
	  return t;
  } // End buildBalanced
  */
  
  /*
  private TreeNode buildBalanced(ArrayList<Point> points, boolean cd) {
	  if(points.size() == 0) return null;
	  if(points.size() == 1) return new TreeNode(points.get(0));
	  
	  Collections.sort(points, new PointComparator(cd));
	  int median = points.size() / 2;
	  
	  TreeNode t = new TreeNode(points.get(median));
	  ArrayList<Point> left = new ArrayList<Point>(points.subList(0, median));
	  ArrayList<Point> right = new ArrayList<Point>(points.subList(median + 1, points.size()));
	  t.left = buildBalanced(left, !cd);
	  t.right = buildBalanced(right, !cd);
	  
	  return t;
  }
  */
  
  private void recalculateData(TreeNode t) {
	  if(t == null) return;
	  if(t.left != null) t.left.parent = t;
	  if(t.right != null) t.right.parent = t;
	  recalculateData(t.left);
	  recalculateData(t.right);
	  updateMinMaxOnDeleteAndRebuild(t);
  }
  
  /*************** LEVEL 1 FUNCTIONALITY ************/
  /**
  * TODO
  *   default constructor creates an empty tree.
  */
  public TDTree() {
	  root = null;
	  size = 0;
  }
  /**
  * TODO
  * runtime requirement:  O(1)
  */
  public int size() {
    return size;
  }

  /**
  * TODO
  * runtime requirement:  O(1)
  */
  public int height() {
	if(root == null) return -1;
	else return root.height;
  }
  /**
  * TODO
  */
  public Double minX(){
    return root != null ? root.minX.p.x() : null;
  }
  /**
  * TODO
  */
  public Double minY(){
    return root != null ? root.minY.p.y() : null;
  }
  /**
  * TODO
  */
  public Double maxX(){
    return root != null ? root.maxX.p.x() : null;
  }
  /**
  * TODO
  */
  public Double maxY(){
    return root != null ? root.maxY.p.y() : null;
  }
  // Method to calculate the above boundary queries
  // This method is called whenever a new node is inserted
  private void updateMinMax(TreeNode t, TreeNode newNode) {
	  	if(t == null && newNode == null) return;        
	  	if(t.maxX.p.x() < newNode.p.x()) t.maxX = newNode;
	  	if(t.maxY.p.y() < newNode.p.y()) t.maxY = newNode;
	  	if(t.minX.p.x() > newNode.p.x()) t.minX = newNode;
	  	if(t.minY.p.y() > newNode.p.y()) t.minY = newNode;
  }
  // Works by calling private insertion method
  public boolean insert(Point pt) {
	  if(pt == null) return false;
	  
	  // Tree is empty
	  if(root == null) {
		  	root = new TreeNode(pt);
		  	root.cd = true;
		  	size++;
		  	return true;
	  }
	  else return insert(root, null, new TreeNode(pt), true, true);
  }
  public boolean insert(double x, double y) {
	  return insert(new Point(x,y));
  }

  /**
   * Private method created for inserting into the tree. Works recursively
   * returns false if point already in tree
   * Method for inserting a node into the tree. Works by traversing through the tree, 
   * branches off x value for even cuts, and y for odd cuts. Uses other value as a tie breaker. 
   * Also keeps track of height and size for constant return time on those function calls.
   */
  private boolean insert(TreeNode t, TreeNode prev, TreeNode newNode, boolean cd, boolean left) {
	  if(t == null) {                  // Found where node should live
		  	if(left) {
		  		prev.left = newNode;
		  	}
		  	else {
		  		prev.right = newNode;
		  	}
		  	newNode.cd = cd;
		  	newNode.parent = prev;
		  	size++;
		  	return true;
	  }
	  else if(t.p.equals(newNode.p)) return false;      // Found the point, don't insert it
	  // Recursive calls to left or right tree
	  else if(cd) {
		  	if(newNode.p.x() < t.p.x()) insert(t.left, t, newNode, !cd, true);
		  	else if(newNode.p.x() > t.p.x()) insert(t.right, t, newNode, !cd, false);
		  	else { // Tie breaker
		  		if(newNode.p.y() < t.p.y()) insert(t.left, t, newNode, !cd, true);
			  	else insert(t.right, t, newNode, !cd, false);
		  	}
	  }
	  else {
		  	if(newNode.p.y() < t.p.y()) insert(t.left, t, newNode, !cd, true);
		  	else if(newNode.p.y() > t.p.y()) insert(t.right, t, newNode, !cd, false);
		  	else {   // Tie breaker
		  		if(newNode.p.x() < t.p.x()) insert(t.left, t, newNode, !cd, true);
			  	else insert(t.right, t, newNode, !cd, false);
		  	}
	  }
	  updateMinMax(t, newNode);
	  t.height = maxHeight(t.left, t.right) + 1;
	  // Now check if tree became unbalanced
	  if(unbalanced(t)) {
		  //System.out.println("unbalanced");
		  Point[] points = collectSatanSpawn(t);
		  TDTree newSubTree = new TDTree(points, cd);
		  swapDataMembers(t, newSubTree.root);
	  }
	  return true;
  } // End private insert() method
  
  private int maxHeight(TreeNode t1, TreeNode t2) {
	  if(t1 == null && t2 == null) return -1;    // No children has height -1
	  if(t1 == null) return t2.height;
	  if(t2 == null) return t1.height;
	  if(t1.height > t2.height) return t1.height;
	  else return t2.height;
  }
  
  /**
  * TODO
  */
  public boolean contains(Point p){
	  return contains(root, p, true);
  }
  
  public boolean contains(double x, double y) {
	  return contains(new Point(x,y));
  }

  // Recursive method for determining if a point is in the tree
  // This function works very similar to the insert method
  private boolean contains(TreeNode t, Point p, boolean even) {
	  if(p == null) return false;
	  if(t == null) return false;
	  if(t.p.equals(p)) return true;        // Found the point, return true
	  if(even) {
		  if(p.x() < t.p.x()) return contains(t.left, p, !even);
		  else if(p.x() > t.p.x()) return contains(t.right, p, !even);
		  else {                      // They must be equal, cut on y
			  if(p.y() < t.p.y()) return contains(t.left, p, !even);
			  else return contains(t.right, p, !even);
		  }
	  }
	  else {
		  if(p.y() < t.p.y()) return contains(t.left, p, !even);
		  else if(p.y() > t.p.y()) return contains(t.right, p, !even);
		  else {                     // They must be equal, cut on x
			  if(p.x() < t.p.x()) return contains(t.left, p, !even);
			  else return contains(t.right, p, !even);
		  }
	  }
  }  // End contains method
  
  // Print should be able to be called publicly, without having to send it parameters
  public void printPreOrder() {
	  printPreOrder(root, true, "", "L");
  }
  // Private method for printing the tree in pre-order - Root-Left-Right
  private void printPreOrder(TreeNode tn, boolean even, String spaces, String side) {
	  if(tn == null) return;
	  if(even) {
		  System.out.print(spaces + "V -" + tn.p.toString() + " " + side + " " + tn.cd + " h: " + tn.height);
	  }
	  else {
		  System.out.print(spaces + "H -" + tn.p.toString() + " " + side + " " + tn.cd + " h: " + tn.height);
	  }
	  System.out.println(" - has maxX: " + tn.maxX.p.x() + " - maxY: " + tn.maxY.p.y() + 
		  " - minX: " + tn.minX.p.x() + " - minY: " + tn.minY.p.y());
	  printPreOrder(tn.left, !even, spaces + " ", "L");
	  printPreOrder(tn.right, !even, spaces + " ", "R");
  }

  public int getSize() {
	  return size;
  }
  
  /******************** LEVEL 2 FUNCTIONALITY ******/

  /**
  * TODO
  */
  public boolean delete(Point p) {
	  if(p == null) return false;
	  return delete(root, p, true);  
  }
  
  /**
  * for convenience
  */
  public boolean delete(double x, double y) {
    return delete(new Point(x,y));
  }
  
  // Private method for deleting a node. Called from the public delete method. Works recursively
  // Method works by recursively deleting a node, and then once backing out of the recursion all 
  // data members are updated for each node
  private boolean delete(TreeNode t, Point p, boolean cd) {
	  boolean status = false;
	  // Base case, not found so return false
	  if(t == null) return false;
	  if(t.p.equals(p)) {             // Found the node, now delete it
		  if(t.right != null) {
			  TreeNode min = findMin(t.right, cd);
			  swapData(t, min);
			  status = delete(min, min.p, min.cd);
		  }
		  else if(t.left != null) {
			  TreeNode min = findMin(t.left, cd);
			  swapData(t, min);
			  t.right = t.left;
			  t.left = null;
			  status = delete(min, min.p, min.cd);
		  }
		  else {                   // At a leaf, remove the node
			  size--;
			  status = deleteFromParent(t);
		  }
	  }
	  else if(cd) {
		  if(p.x() < t.p.x()) status = delete(t.left, p, !cd);
		  else if(p.x() > t.p.x()) status = delete(t.right, p, !cd);
		  else {                      // They must be equal, cut on y
			  if(p.y() < t.p.y()) status = delete(t.left, p, !cd);
			  else status = delete(t.right, p, !cd);
		  }
	  }
	  else {
		  if(p.y() < t.p.y()) status = delete(t.left, p, !cd);
		  else if(p.y() > t.p.y()) status = delete(t.right, p, !cd);
		  else {                     // They must be equal, cut on x
			  if(p.x() < t.p.x()) status = delete(t.left, p, !cd);
			  else status = delete(t.right, p, !cd);
		  }
	  }
	  // Backing out of recursion, need to update data members
	  updateMinMaxOnDeleteAndRebuild(t);
	  t.height = maxHeight(t.left, t.right) + 1;
	  
	  // Check if becoming unbalanced
	  if(unbalanced(t)) {
		  Point[] points = collectSatanSpawn(t);
		  TDTree newSubTree = new TDTree(points, cd);
		  swapDataMembers(t, newSubTree.root);
	  }	
	  return status;
  } // End private delete method
  
  // Method for deleting a node from it's parent, simply set a parents min max values to itself
  // and erase the pointer
  private boolean deleteFromParent(TreeNode t) {
	  if(t.parent == null) {
		  return false;               // Should never happen
	  }
	  t.parent.maxX = t.parent;
	  t.parent.maxY = t.parent;
	  t.parent.minX = t.parent;
	  t.parent.minY = t.parent;
	  if(t.parent.left == t) {
		  t.parent.left = null;
		  t = null;
		  return true;
	  }
	  else if(t.parent.right == t) {
		  t.parent.right = null;
		  t = null;
		  return true;
	  }
	  System.out.println("BAD THINGS HAPPENING");
	  return false;                                  // Should never happen
  }
  
  // Private method for updating min/max on deletion
  private void updateMinMaxOnDeleteAndRebuild(TreeNode t) {
	  if(t == null) return;
	  if(t.left == null && t.right == null) {
		  return;
	  }
	  if(t.left != null && t.right == null) {
		  	t.maxX = maximum(t.left.maxX, t, true);
		  	t.maxY = maximum(t.left.maxY, t, false);
		  	t.minX = minimum(t.left.minX, t, true);
		  	t.minY = minimum(t.left.minY, t, false);
		  	return;
	  }
	  else if(t.left == null && t.right != null) {
		  	t.maxX = maximum(t.right.maxX, t, true);
		  	t.maxY = maximum(t.right.maxY, t, false);
		  	t.minX = minimum(t.right.minX, t, true);
		  	t.minY = minimum(t.right.minY, t, false);
		  	return;
	  }
	  else { // Get max from all three nodes
		  	t.maxX = maximum(t, t.left.maxX, t.right.maxX, true);
		  	t.maxY = maximum(t, t.left.maxY, t.right.maxY, false);
		  	t.minX = minimum(t, t.left.minX, t.right.minX, true);
		  	t.minY = minimum(t, t.left.minY, t.right.minY, false);
		  	return;
	  }
  }  // End private updateMinMaxOnDelete() method
  
  // Private method for returning the min x or y value
  private TreeNode findMin(TreeNode t, boolean even) {
	  if(even) return t.minX;
	  else return t.minY;
  }
  
  // Private method for swapping data
  // Copys t2 data into t1
  private void swapData(TreeNode t1, TreeNode t2) {
	  //System.out.println("SWAPPING: " + t1.p.toString() + " " + t2.p.toString());
	  t1.p = t2.p;
  }
  
  private void swapDataMembers(TreeNode t1, TreeNode t2) {
	  t1.p = t2.p;
	  t1.left = t2.left;
	  t1.right = t2.right;
  }
  
  // Method created to return the point that is minimum of two points, with respect to the cutting dimension
  private TreeNode minimum(TreeNode t1, TreeNode t2, boolean even) {
	  // Base cases
	  if(t1 == null && t2 == null) return null;
	  if(t1 != null && t2 == null) return t1;
	  if(t1 == null && t2 != null) return t2;
	  
	  if(even) {   // compare on x
		  if(t1.p.x() < t2.p.x()) return t1;
		  else return t2;
	  }
	  else {          // compare on y
		  if(t1.p.y() < t2.p.y()) return t1;
		  else return t2;
	  }
  } // End minimum() method
  
  // Overloaded minimum method for calculating minimum point between 3 points
  private TreeNode minimum(TreeNode t1, TreeNode t2, TreeNode t3, boolean even) {
	  
	  return minimum( t1, minimum(t2, t3, even), even);
  }
  
  // Private methods for getting maximum values
  private TreeNode maximum(TreeNode t1, TreeNode t2, boolean even) {
	  if(even) {
		  if(t1.p.x() > t2.p.x()) return t1;
		  else return t2;
	  }
	  else {
		  if(t1.p.y() > t2.p.y()) return t1;
		  else return t2;
	  }
  }
  
  private TreeNode maximum(TreeNode t1, TreeNode t2, TreeNode t3, boolean even) {
	  return maximum( t1, maximum(t2, t3, even), even);
  }
  
  // Private method for unbalanced detection
  private boolean unbalanced(TreeNode t) {
	  if(t == null) return false;
	  if(t.left == null || t.right == null) return false;
	  if(max(t.left.height, t.right.height) > (2 * min(t.left.height, t.right.height)) + 1) {
		  return true;
	  }
	  else return false;
  } // end unBalDetect() method 
  
  // This method was created to collect all nodes in a sub tree if unbalnced was detected
  private Point[] collectSatanSpawn(TreeNode t) {
	  LinkedList<Point> points = new LinkedList<Point>();
	  getPointsForRebuilding(t, points);
	  Point[] a = new Point[points.size()];
	  points.toArray(a);
	  return a;
  }
  
  // Method that aids collectSatanSpawn(), recursively walks through tree and populates a linked list
  private void getPointsForRebuilding(TreeNode t, LinkedList<Point> points) {
	  if(t == null) return;
	 
	  points.add(t.p);
	  getPointsForRebuilding(t.left, points);
	  getPointsForRebuilding(t.right, points);
  }
  
  // Methods for returning min and max
  private int min(int a, int b) {
	  return a < b ? a : b;
  }
  
  private int max(int a, int b) {
	  return a > b ? a : b;
  }
  
  /**
  * TODO
  */
  public void draw(){
	  if(root == null) return;
	  
	  // Call recursive method
	  drawPoints(root, true, true, 1);
	  //System.out.println("done");
  }

  /**
   * @param tn - TreeNode object
   * @param even - Vertical or horizontal cuts
   * @param left - left or right
   * @param pivot - Pivot point
   * This function recursively draws the binary tree and displays how the tree slices
   * the unit square.
   * It works by sending it the current node to draw, whether or not it is a vertical cut,
   * whether or not it is a left node, and a "pivot" point. The pivot point tells the method
   * where to start and stop the line.
   */
  private void drawPoints(TreeNode tn, boolean even, boolean left, double pivot) {
	  if(tn == null) return;
	  StdDraw.setPenColor();
	  //StdDraw.filledCircle(tn.p.x(), tn.p.y(), .005);
	  if(even) {                                  // Vertical cuts
		  StdDraw.setPenColor(Color.red);
		  if(left) {
			  StdDraw.line(tn.p.x(), 0, tn.p.x(), pivot);
		  }
		  else {
			  StdDraw.line(tn.p.x(), pivot, tn.p.x(), 1);
		  }
		  pivot = tn.p.x();
	  }
	  else {                                      // Horizontal cuts
		  StdDraw.setPenColor(Color.blue);
		  if(left) {
			  StdDraw.line(0, tn.p.y(), pivot, tn.p.y());
		  }
		  else {
			  StdDraw.line(pivot, tn.p.y(), 1, tn.p.y());
		  }
		  pivot = tn.p.y();
	  }
	  StdDraw.setPenColor();
	  StdDraw.filledCircle(tn.p.x(), tn.p.y(), .007);
	  drawPoints(tn.left, !even, true, pivot);
	  drawPoints(tn.right, !even, false, pivot);
  } // End drawPoints() function
  
  /***************** PART OF LEVEL 3 FUNCTIONALITY *****/
  /** Note that incremental enforcement of the size-balanced
  *     property is part of LEVEL 3.
  *   It requires modification to insert and delete.
  *******************************************************/
  /**
  * TODO
  *  returns point in tree closest to point p (by 
  *     Euclidean distance).
  *  if tree empty, null is returned.
  */
  
  private double squaredDistance(Point p1, Point p2) {
	  double dx = p1.x() - p2.x();
	  double dy = p1.y() - p2.y();
	  return ( (dx*dx) + (dy*dy) );
  }
  
  /**
   * Returns the closest point to @param p.
   * Works recursively. Compares current point with p, then checks which tree to explore sub-trees
   * @param t
   * @param p
   * @param best
   * @param bestDistance
   * @param cd
   * @return Point nearest to p
   */
  private Point nearest(TreeNode t, Point p, Point best, double bestDistance, boolean cd) {
	  if(p == null) return best;
	  if(t == null) return best;
	  nodesTouchedOnNearest++;
	  //System.out.println(t.p.toString());
	  
	  double distance = squaredDistance(t.p, p);
	  if(distance < bestDistance) {
		  best = t.p;
		  bestDistance = distance;
	  }
	  
	  Point result;
	  if(cd) {
		  if(p.x() < t.p.x()) result = nearest(t.left, p, best, bestDistance, !cd);
		  else if(p.x() > t.p.x()) result = nearest(t.right, p, best, bestDistance, !cd);
		  else {                      // They must be equal, cut on y
			  if(p.y() < t.p.y()) result = nearest(t.left, p, best, bestDistance, !cd);
			  else result = nearest(t.right, p, best, bestDistance, !cd);
		  }
	  }
	  else {
		  if(p.y() < t.p.y()) result = nearest(t.left, p, best, bestDistance, !cd);
		  else if(p.y() > t.p.y()) result = nearest(t.right, p, best, bestDistance, !cd);
		  else {                     // They must be equal, cut on x
			  if(p.x() < t.p.x()) result = nearest(t.left, p, best, bestDistance, !cd);
			  else result = nearest(t.right, p, best, bestDistance, !cd);
		  }
	  }
	  distance = squaredDistance(result, p);
	  if(distance < bestDistance) {
		  best = result;
		  bestDistance = distance;
	  }
	  return best;
  }
  
  public Point nearest(Point p) {
	  
	  nodesTouchedOnNearest = 0;
	  return nearest(root, p, root.p, Double.MAX_VALUE, true);
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
	  
	  rangeQuery(sw, ne, result, root);
  }

  // Works the exact same way as rangeSize(). Simply checks if sub-tree possibly contains points in query
  private void rangeQuery(Point sw, Point ne, Collection<Point> result, TreeNode t) {
	  if(t == null) return;
	  
	  if(subTreeInRange(sw, new Point(ne.x(), sw.y()), new Point(sw.x(), ne.y()), ne, t.minX.p) ||
			  subTreeInRange(sw, new Point(ne.x(), sw.y()), new Point(sw.x(), ne.y()), ne, t.maxX.p) || 
			  subTreeInRange(sw, new Point(ne.x(), sw.y()), new Point(sw.x(), ne.y()), ne, t.minY.p) ||
			  subTreeInRange(sw, new Point(ne.x(), sw.y()), new Point(sw.x(), ne.y()), ne, t.maxY.p) ) {
		  rangeQuery(sw, ne, result,t.left);
		  rangeQuery(sw, ne, result,t.right);
	  }
	  else return;
	  
	  if(inRange(sw, ne, t.p)) result.add(t.p);
  }
  
  private boolean inRange(Point sw, Point ne, Point p) {
	  if(p == null || sw == null || ne == null) return false;
	  if( (sw.x() < p.x() && sw.y() < p.y()) && (ne.x() > p.x() && ne.y() > p.y() ) )
		  return true;
	  else return false;
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
    	
	return rangeSize(sw, ne, root);

  }
  
  // This method returns true of p lies in the rectangle outlined by sw, se, nw, ne
  private boolean subTreeInRange(Point sw, Point se, Point nw, Point ne, Point p) {
	  if(sw.x() < p.x() && p.x() < se.x()) {
		  if(sw.y() < p.y() && p.y() < nw.y()) {
			  return true;
		  }
	  }
	  return false;
  }
  
  private boolean boxOverlaps(TreeNode t, Point sw, Point ne) {
	  Point nw = new Point(sw.x(), ne.y());
	  Point se = new Point(ne.x(), sw.y());
	  
	  Point myNw = new Point(t.minX.p.x(), t.maxY.p.y());
	  Point mySe = new Point(t.maxX.p.x(), t.minY.p.y());
	  
	  if(myNw.x() >= nw.x() || myNw.y() >= nw.y() || mySe.x() <= se.x() || mySe.y() <= se.y()) {
		  return true;
	  }
	  return false;
  }
  
  // Private method for calculating range size. Works by checking if two rectangles overlap each other
  // If the two overlap (range query and range of points in sub-tree), keep searching. If not return 0
  private int rangeSize(Point sw, Point ne, TreeNode t) {
	  if(t == null) return 0;           // Fell out of tree
	  int count = 0;
	  if(subTreeInRange(sw, new Point(ne.x(), sw.y()), new Point(sw.x(), ne.y()), ne, t.minX.p) ||
			  subTreeInRange(sw, new Point(ne.x(), sw.y()), new Point(sw.x(), ne.y()), ne, t.maxX.p) || 
			  subTreeInRange(sw, new Point(ne.x(), sw.y()), new Point(sw.x(), ne.y()), ne, t.minY.p) ||
			  subTreeInRange(sw, new Point(ne.x(), sw.y()), new Point(sw.x(), ne.y()), ne, t.maxY.p) ) {
		  
		  count += rangeSize(sw, ne, t.left);
		  count += rangeSize(sw, ne, t.right);
	  }
	  else return 0;                          // If there is no overlap, return 0
	  
	  if(inRange(sw, ne, t.p)) {                     // Add current node to count if within range
		  count++;
	  }
	  
	  return count;
  }
  
  public int rangeSize(double minX, double maxX, 
                            double minY, double maxY){

	Point sw = new Point(minX, minY);
	Point ne = new Point(maxX, maxY);

	return rangeSize(sw, ne);
  }

}
