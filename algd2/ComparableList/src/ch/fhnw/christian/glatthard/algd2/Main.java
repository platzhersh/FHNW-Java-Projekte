package ch.fhnw.christian.glatthard.algd2;

import ch.fhnw.christian.glatthard.algd2.ComparableList.CListIterator;

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
		System.out.println(list.getHead().compareTo(list.getTail()));
		CListIterator<String> iterator = list.iterator();
		
		System.out.println(list.size());
	}

}
