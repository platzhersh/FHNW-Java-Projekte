package ch.fhnw.christian.glatthard.algd2;

public class Tree<T> {

	private Node<T> root;
	
	
	public Tree() {
		
	}
	public Tree(T value) {
		this.root = new Node(value);
	}

}
