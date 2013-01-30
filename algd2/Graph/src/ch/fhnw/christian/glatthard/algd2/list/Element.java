package ch.fhnw.christian.glatthard.algd2.list;

public class Element<T> {
	
	private T value;
	public Element<T> next;
	public Element<T> prev;
	
	
	public void setValue(T val) {
		this.value = val;
	}
	
	public T getValue() {
		return this.value;
	}
	
	// Constructor
	public Element(T val) {
		this.value = val;
		this.next = null;
		this.prev = null;
	}
	
}
