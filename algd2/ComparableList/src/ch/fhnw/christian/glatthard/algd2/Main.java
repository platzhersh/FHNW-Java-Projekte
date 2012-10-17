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
		list.addHead("5");
		list.addHead("4");
		list.addHead("3");
		list.addHead("2");
		list.addHead("1");
		list.addTail("6");
		System.out.println(list.size());
		System.out.println(list.getHead().compareTo(list.getTail()));
		
		
		CListIterator<String> iterator = list.iterator();
		iterator.add("dini mueter");
		while (iterator.hasNext()) System.out.println(iterator.next());
		
		
		
		System.out.println("list size: "+list.size());
	}

}
