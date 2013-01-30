package ch.fhnw.christian.glatthard.algd2;

import ch.fhnw.christian.glatthard.algd2.DoubleLinkedList.DLLIterator;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		DoubleLinkedList<Integer> list = new DoubleLinkedList<Integer>();
		
		list.addHead(1);
		System.out.println(list.size());
		System.out.println("Head: "+list.getHead()+". Tail: "+list.getTail()+".");
		list.addHead(2);
		System.out.println(list.size());
		System.out.println("Head: "+list.getHead()+". Tail: "+list.getTail()+".");
		list.addHead(3);
		System.out.println(list.size());
		System.out.println("Head: "+list.getHead()+". Tail: "+list.getTail()+".");
		list.addHead(4);
		System.out.println(list.size());
		System.out.println("Head: "+list.getHead()+". Tail: "+list.getTail()+".");

		
		DLLIterator itr = list.new DLLIterator<Integer>(list);
		
		while (itr.hasNext()) {
			System.out.println(itr.next());
		}
		
		
	}

}
