package ch.fhnw.christian.glatthard.algd2;

import java.util.ListIterator;

public class DoubleLinkedList<T> {

	private Element<T> head;
	private Element<T> tail;
	private Integer size;
	
	public T getHead() {
		return this.head.getValue();
	}
	public T getTail() {
		return this.tail.getValue();
	}
	
	public Integer size() {
		return this.size;
	}
	public void addHead(T value) {
		Element<T> n = new Element(value);
		if (this.head == null) this.head = this.tail = n;
		else { n.next = this.head;}
		this.head = n;
		this.size++;
	}
	public void addTail(T value) {
		Element<T> n = new Element(value);
		if (this.tail == null) this.head = this.tail = n;
		else n.prev = this.tail;
		this.tail = n;
		this.size++;
	}
	public void removeHead() {
		Element<T> n = this.head;
		if (n.next != null) this.head = n.next;
		if (this.size == 1) this.head = this.tail = null;
		this.size--;
	}
	public void removeTail() {
		Element<T> n = this.tail;
		if (n.prev != null) this.tail = n.prev;
		if (this.size == 1) this.head = this.tail = null;
		this.size--;
	}
	
	/* Constructor */
	public DoubleLinkedList() {
		this.size = 0;
		this.head = null;
		this.tail = null;
	}
	
	class DLLIterator<T> implements ListIterator<T>{
		
		DoubleLinkedList<T> list;
		Element<T> returned, next, prev;
		
		public DLLIterator(DoubleLinkedList<T> list){
			this.list = list;
			this.next = list.head;
			this.prev = null;
		}

		@Override
		public void add(T e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean hasNext() {
			return (this.next != null);
		}

		@Override
		public boolean hasPrevious() {
			return (this.prev != null);
		}

		@Override
		public T next() {
			this.returned = this.prev = this.next;
			this.next = this.next.next;
			return this.returned.getValue();
		}

		@Override
		public int nextIndex() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public T previous() {
			this.returned = this.next = this.prev;
			this.prev = this.prev.prev;
			return this.returned.getValue();
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
