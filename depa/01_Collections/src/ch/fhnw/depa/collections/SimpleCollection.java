package ch.fhnw.depa.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This is a very simple implementation of the Collection interface. A
 * SimpleCollection may contain the same element more than once and it does
 * explicitly not guarantee any order of traversal over its elements.
 * 
 * Moreover it does not support removal of elements.
 * 
 * @param <E>
 *            Element type of this collection
 */
public class SimpleCollection<E> extends AbstractCollection<E> {
	private Node<E> root = null;
	private int version = 0;

	@Override
	public boolean add(E e) {
		root = new Node<E>(e, root);
		version++;
		return true;
	}

	@Override
	public Iterator<E> iterator() {
		return new SCIterator();
	}

	private static class Node<E> {
		E val;
		Node<E> next;

		Node(E val, Node<E> next) {
			this.val = val;
			this.next = next;
		}
	}

	@Override
	public int size() {
		// this optimization is possible as SimpleCollection does not support
		// removal of objects and has only an empty constructor.
		return version;
	}

	private class SCIterator implements Iterator<E> {
		private Node<E> current; // current refers next element to be returned
		private int version;

		SCIterator() {
			current = root;
			version = SimpleCollection.this.version;
		}

		@Override
		public boolean hasNext() {
			if (version != SimpleCollection.this.version) {
				throw new ConcurrentModificationException();
			}
			return current != null;
		}

		@Override
		public E next() {
			if (version != SimpleCollection.this.version) {
				throw new ConcurrentModificationException();
			}
			if (current == null) { // end of collection reached
				throw new NoSuchElementException();
			}
			E result = current.val;
			current = current.next;
			return result;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

}
