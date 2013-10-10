package ch.fhnw.christian.glatthard.algd2;

import ch.fhnw.christian.glatthard.algd2.BinaryTree.Order;

public class BinaryTreeNode {
		private Comparable m_key; // Suchschlüssel
		private BinaryTreeNode m_left, m_right; // linker und rechter Sohn

		public BinaryTreeNode(Comparable key) {
			m_key = key;
		}

		// Einfügen eines neuen Knotens (rekursiver Aufbau des Baumes)
		public void insert(Comparable key) {
			int c = key.compareTo(m_key);
			if (c == 0)
				return; // found, do nothing
			if (c < 0) {
				if (m_left == null) {
					m_left = new BinaryTreeNode(key);
				} else {
					m_left.insert(key);
				}
			} else {
				if (m_right == null) {
					m_right = new BinaryTreeNode(key);
				} else {
					m_right.insert(key);
				}
			}
		}
		
		/// TODO: remove function
		public void remove(BinaryTreeNode remove) {};

		// Einrücken (nur für Darstellungszwecke)
		private void indent(int level) {
			while (level > 0) {
				System.out.print(" ");
				level--;
			}
		}

		// Baum darstellen
		public void show(Order order, int level) {
			if (order == Order.PreOrder) {
				indent(level);
				System.out.println(m_key);
			}
			if (m_left != null) {
				m_left.show(order, level + 1);
			}
			if (order == Order.InOrder) {
				indent(level);
				System.out.println(m_key);
			}
			if (m_right != null) {
				m_right.show(order, level + 1);
			}
			if (order == Order.PostOrder) {
				indent(level);
				System.out.println(m_key);
			}
		}
}
