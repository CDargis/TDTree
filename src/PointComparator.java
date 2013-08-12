import java.util.Comparator;

/**
 * @author Chris Dargis
 * @version 2.22.12
 * Comparator class for comparing points
 * Constructor for object defines whether we are comparing on x or y
 */

public class PointComparator implements Comparator<Point> {
	
	boolean even;
	
	public PointComparator(boolean even) {
		this.even = even;
	}

	@Override
	public int compare(Point p1, Point p2) {
		if(even) {
			if(p1.x() < p2.x()) return -1;
			else if(p1.x() > p2.x()) return 1;
			else {
				if(p1.y() < p2.y()) return -1;
				else if(p1.y() > p2.y()) return 1;
			}
		}
		else {
			if(p1.y() < p2.y()) return -1;
			else if(p1.y() > p2.y()) return 1;
			else {
				if(p1.x() < p2.x()) return -1;
				else if(p1.x() > p2.x()) return 1;
			}
		}
		return 0;          // Will return 0 if points are even, however should not happen
	}
}
