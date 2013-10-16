package ch.fhnw.christian.glatthard.algd2;

public class BinaryTree {
	public enum Order {
		PreOrder, InOrder, PostOrder
	};



	private BinaryTreeNode m_root; // Wurzel des Baumes

	// Einfügen eines neuen Schlüssels
	public void insert(Comparable key) {
		if (m_root == null)
			m_root = new BinaryTreeNode(key);
		else
			m_root.insert(key);
	}

	// Baum darstellen
	public void show(Order order) {
		if (m_root == null)
			System.out.println("tree is empty");
		else
			switch (order) {
			case PreOrder:
				System.out.println("pre-order");
				m_root.show(order, 0);
				break;
			case InOrder:
				System.out.println("in-order");
				m_root.show(order, 0);
				break;
			case PostOrder:
				System.out.println("post-order");
				m_root.show(order, 0);
				break;
			default:
				assert false : "wrong traverse order";
			}
	}

}
