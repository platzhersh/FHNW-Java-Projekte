package patterns.clone.alias;

import java.io.Serializable;

// This program demonstrates, that the Java-Cloning mechanism does not work
// for diamond structures, i.e. for alias references
public class TestDiamond {

	static class Node implements Cloneable, Serializable {
		private Node left, right;
		private int val;

		public Node(int val, Node left, Node right) {
			this.val = val;
			this.left = left;
			this.right = right;
		}

		public Object clone() {
			try {
				Node c = (Node) super.clone();
				if (c.left != null)
					c.left = (Node) c.left.clone();
				if (c.right != null)
					c.right = (Node) c.right.clone();
				return c;
			} catch (CloneNotSupportedException e) {
				throw new InternalError();
			}
		}

		public Node getLeft() {  return left; }
		public Node getRight() { return right; }
		public int getVal() { return val; }
	}

	public static void main(String[] args) {
		Node n4 = new Node(4, null, null);
		Node n2 = new Node(2, null, n4);
		Node n3 = new Node(3, n4, null);
		Node n1 = new Node(1, n2, n3);

		Node c = (Node) n1.clone();

		System.out.println(c.getLeft().getRight());
		System.out.println(c.getRight().getLeft());
	}
}
