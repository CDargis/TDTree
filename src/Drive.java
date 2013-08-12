import java.util.Collection;
import java.util.Random;


public class Drive {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Drive d = new Drive();
		d.driveIt();
	}

	public void driveIt() {		
		TDTree t = new TDTree();
		for(int i = 0; i < 500; i++) {
			t.insert(Math.random(), Math.random());
		}
		System.out.println(t.size());
		t.printPreOrder();
		t.draw();
	}
}
