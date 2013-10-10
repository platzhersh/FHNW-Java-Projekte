package ch.fhnw.christian.glatthard.algd2;

/***
 * Implementation of a BinaryTree
 * @author chregi
 *
 * @param <T>
 */
public class Tree<T> {

	private Node<T> root;
	
	public Tree() {
		
	}
	public Tree(T value) {
		this.root = new Node<T>(value);
	}
	
	public boolean isRoot(Node<T> n) {
		return n.getValue() == root;
	}

}
