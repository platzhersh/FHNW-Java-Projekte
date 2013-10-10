package ch.fhnw.christian.glatthard.algd2;

public class Node<T> {

	private Node<T> left;
	private Node<T> right;
	private T value;
	
	/***
	 * Node Constructor
	 * @param value value to store in the node
	 */
	public Node(T val) {
		this.value = val;
	}
	
	
	/* Getter & Setter */
	public T getValue() {
		return this.value;
	}
	public Node<T> getLeftChild() {
		return left;
	}
	
	public Node<T> getRightChild() {
		return right;
	}
	
	public void setValue(T value) {
		this.value = value;
	}
	
	public void setLeftChild(Node<T> child) {
		this.left = child;
	}
	
	public void setRightChild(Node<T> child) {
		this.right = child;
	}

}
