package ch.fhnw.christian.glatthard.algd2;

public class Element<T> {
	
	private T value;
	public Element next;
	public Element prev;
	
	
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
