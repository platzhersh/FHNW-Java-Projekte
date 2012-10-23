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
		list.removeTail();
		
		System.out.println("compareto: "+list.getHead().compareTo(list.getTail()));
		
		
		
		CListIterator<String> iterator = list.iterator();
		iterator.add("dini mueter");
		
		System.out.println("list size: "+list.size());
		
		int i = 1;
		while (iterator.hasNext()) {
			System.out.println(i +" "+iterator.next());
			if (i == list.size()) iterator.remove();
			i++;
		}
		
		System.out.println("list size: "+list.size());
		
		CListIterator<String> iterator2 = list.iterator();
		while (iterator2.hasNext()) {
			System.out.println(i +" "+iterator2.next());
			i++;
		}
		System.out.println("list size: "+list.size());
	}

}
