package ch.fhnw.christian.glatthard.algd2;

import java.util.LinkedList;

public class Stack<T> {

	private LinkedList<T> content;
	
	
	public Stack() {
		this.content = new LinkedList<T>();
	}
	
	public T top() {
		
		return this.content.getLast();
	}
	
	public T pop() {
		T last = this.content.getLast();
		this.content.remove(last);
		return last;
	}
	
	public void push (T data) {
		this.content.add(data);
	}
	
	public boolean isEmpty() {
		if (this.content.size() == 0) return true;
		else return false;
	}

}