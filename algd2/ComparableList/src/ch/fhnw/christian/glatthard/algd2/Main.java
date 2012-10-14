package ch.fhnw.christian.glatthard.algd2;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ComparableList<String> list = new ComparableList<String>();
		
		System.out.println(list.size());
		list.addHead("Head");
		list.addTail("Tail");
		System.out.println(list.size());
		list.removeHead();
		list.removeHead();
		
		System.out.println(list.size());
	}

}
