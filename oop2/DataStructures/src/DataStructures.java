import java.util.ArrayList;


public class DataStructures {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LinkedList linkedList 	= new LinkedList(42);
		ArrayList<String> cars = new ArrayList<String>();
		
		linkedList.add(12);
		linkedList.add(33);
		
		System.out.println("Initial Size: "+cars.size());
		cars.add("dini fetti muetter");
		cars.add("sie isch so fett");
		System.out.println("End Size: "+cars.size());
		
		for (String s : cars) {
			System.out.println(s);
		}
		
		System.out.println(linkedList.length());

	}

}
