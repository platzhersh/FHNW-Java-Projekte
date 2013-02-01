package ch.fhnw.christian.glatthard.algd2;

public class BinaryTree {
		public enum Order { PreOrder, InOrder, PostOrder };
		
		public static class Node {
			private Comparable m_key; // Suchschlüssel
			private Node m_left, m_right; // linker und rechter Sohn
			private Node(Comparable key) {
			m_key = key;
		}
		
			// Einfügen eines neuen Knotens (rekursiver Aufbau des Baumes)
		private void insert(Comparable key) {
		int c = key.compareTo(m_key);
		if (c == 0) return; // found, do nothing
		if (c < 0) {
		if (m_left == null) {
		m_left = new Node(key);
		} else {
		m_left.insert(key);
		}
		} else {
		if (m_right == null) {
		m_right = new Node(key);
		} else {
		m_right.insert(key);
		}
		}
		}
		// Einrücken (nur für Darstellungszwecke)
		private void indent(int level) {
		while(level > 0) {
		System.out.print(" ");
		level--;
		}
		}
		// Baum darstellen
		private void show(Order order, int level) {
		if (order == Order.PreOrder) {
		indent(level); System.out.println(m_key);
		}
		if (m_left != null) {
		m_left.show(order, level + 1);
		}
		if (order == Order.InOrder) {
			indent(level); System.out.println(m_key);
			}
			if (m_right != null) {
			m_right.show(order, level + 1);
			}
			if (order == Order.PostOrder) {
			indent(level); System.out.println(m_key);
			}
			}
			}
			private Node m_root; // Wurzel des Baumes
			// Einfügen eines neuen Schlüssels
			public void insert(Comparable key) {
			if (m_root == null)
			m_root = new Node(key);
			else
			m_root.insert(key);
			}
			// Baum darstellen
			public void show(Order order) {
			if (m_root == null)
			System.out.println("tree is empty");
			else
			switch(order) {
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
