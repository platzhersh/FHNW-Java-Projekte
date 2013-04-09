package ch.fhnw.algd1;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		City c = new City(14);
		
		System.out.println(c.toString());
		
		int pos = 5;
		
		System.out.println("You can see " + c.countBuildingsNORTH(pos) + " buildings from pos "+pos+" in direction NORTH.");

	}

}
