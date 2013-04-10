package ch.fhnw.algd1;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		City c = new City(14);
		c.randomize();
		
		System.out.println(c.toString());
		
		int pos = 13;
		
		System.out.println("You can see " + c.countBuildingsNORTH(pos) + " buildings from pos "+pos+" in direction NORTH.");
		
		System.out.println("You can see " + c.countBuildingsEAST(pos) + " buildings from pos "+pos+" in direction EAST.");
		
		System.out.println("You can see " + c.countBuildingsSOUTH(pos) + " buildings from pos "+pos+" in direction SOUTH.");
		
		System.out.println("You can see " + c.countBuildingsWEST(pos) + " buildings from pos "+pos+" in direction WEST.");

	}

}
