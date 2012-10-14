package ch.fhnw.christian.glatthard.algd2;

import java.util.LinkedList;

@SuppressWarnings("serial")
public class ComparableList<T> extends LinkedList<T> {
	
	int size;
	Element head;
	Element tail;
	
	public ComparableList() {
		this.size = 0;
		this.head = null;
		this.tail = null;
	}
	
	
	public int size() {
		
		return size;
	}
	
	public void addHead(T value) {
		if ( head == null) {
			head = new Element(value, null, null);
			tail = head;
		} else {
			Element newElement = new Element(value, head, null);
			head.prev = newElement;
			head = newElement;
		}
		size++;
	}
	
	public void addTail(T value) {
		if ( tail == null) {
			tail = new Element(value, null, null);
			head = tail;
		} else {
			Element newElement = new Element(value, null, tail);
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
	
	

	public class Element {
		Element next;
		Element prev;
		T value;
		
		public Element(T value, Element next, Element prev) {
			this.next = next;
			this.prev = prev;
			this.value = value;
		}
	}
	
}
