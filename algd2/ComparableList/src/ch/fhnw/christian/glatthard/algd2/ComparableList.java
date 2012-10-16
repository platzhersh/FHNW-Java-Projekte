package ch.fhnw.christian.glatthard.algd2;

import java.util.ListIterator;


public class ComparableList<T extends Comparable<T>> {
	
	int size;
	public Element<T> head;
	public Element<T> tail;
	
	public ComparableList() {
		this.size = 0;
		this.head = null;
		this.tail = null;
	}
	
	
	public int size() {
		
		return size;
	}
	
	public Element<T> getHead() {
		return this.head;
	}
	
	public Element<T> getTail() {
		return this.tail;
	}
	
	public void addHead(T value) {
		if ( head == null) {
			head = new Element<T>(value, null, null);
			tail = head;
		} else {
			Element<T> newElement = new Element<T>(value, head, null);
			head.prev = newElement;
			head = newElement;
		}
		size++;
	}
	
	public void addTail(T value) {
		if ( tail == null) {
			tail = new Element<T>(value, null, null);
			head = tail;
		} else {
			Element<T> newElement = new Element<T>(value, null, tail);
			tail.next = newElement;
			tail = newElement;
		}
		size++;
	}
	
	public void removeHead() {
		if ( size == 0) {
			/* do nothing */
		} else {
			head = head.next;
			if (head != null) head.prev = null;
		}
		size--;
		
	}
	
	public void removeTail() {
		if ( size == 0) {
			/* do nothing */
		} else {
			tail = tail.prev;
			if (tail != null) tail.next = null;
		}
		size--;
	}
	
	

	@SuppressWarnings("hiding")
	public class Element<T extends Comparable<T>> implements Comparable<T> {
		Element<T> next;
		Element<T> prev;
		T value;
		
		public Element(T value, Element<T> next, Element<T> prev) {
			this.next = next;
			this.prev = prev;
			this.value = value;
		}
		// Compare value of two Elements
		public int compareTo(T value){
			return this.value.compareTo(value);
		}
		// Compare value of two Elements
		public int compareTo(Element<T> element){
			return this.value.compareTo(element.value);
		}
		
	}
	
	public CListIterator<T> iterator() {
		return new CListIterator<T>(this);
	}
	
	public void iterator(int index) {
		
	}
	
	static class CListIterator<T extends Comparable<T>> implements ListIterator<T> {
		private Element<T> current;
		private ComparableList<T> itlist;
		
		public CListIterator(ComparableList<T> list) {
			itlist = list;
			current = (Element<T>)itlist.getHead();
		}
		
		
		@Override
		public void add(T e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean hasNext() {
			return (this.current.next != null);
		}

		@Override
		public boolean hasPrevious() {
			return (this.current.prev != null);
		}

		@Override
		public T next() {
			this.current = this.current.next;
			return this.current.value;
		}

		@Override
		public int nextIndex() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public T previous() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int previousIndex() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void set(T e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}
