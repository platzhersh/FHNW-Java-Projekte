package ch.fhnw.christian.glatthard.algd2;

import ch.fhnw.christian.glatthard.algd2.BinaryTree.Order;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BinaryTree tree = new BinaryTree();
		tree.insert(2);
		tree.insert(1);
		tree.insert(3);
		tree.insert(4);
		tree.insert(5);
		tree.insert(6);
		tree.insert(7);
		tree.insert(8);
		tree.show(Order.PreOrder);
		tree.show(Order.InOrder);
		tree.show(Order.PostOrder);

	}

}
