/**
 * 
 * @author Chris Dargis
 * @version 2.14.12
 * CS 202
 * Spring 2012
 */

public class TreeNode {
	Point p;
	TreeNode parent;
	TreeNode left;
	TreeNode right;
	int height;
	boolean cd;
	
	// Each node contains max and min pointers for it's sub-tree
	TreeNode maxX;
	TreeNode maxY;
	TreeNode minX;
	TreeNode minY;
	
	// Basic constructor
	public TreeNode(Point p) {
		this.p = p;
		maxX = this;
		maxY = this;
		minX = this;
		minY = this;
		left = null;
		right = null;
		parent = null;
		height = 0;
	}
	
	//------------------------------------------------
	
} // end class
