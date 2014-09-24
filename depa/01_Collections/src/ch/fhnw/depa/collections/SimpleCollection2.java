package ch.fhnw.depa.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * This is a very simple implementation of the Collection interface. A
 * SimpleCollection may contain the same element more than once and it does
 * explicitly not guarantee any order of traversal over its elements.
 * 
 * In contrast to the class SimpleCollection, this class supports removal of
 * elements.
 * 
 * @param <E>
 *            Element type of this collection
 */
public class SimpleCollection2<E> extends AbstractCollection<E> {
	private Node<E> root = null;
	private int version = 0;
	private int size = 0;

	@Override
	public boolean add(E e) {
		root = new Node<E>(e, root);
		version++;
		size++;
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
		return size;
	}

	private class SCIterator implements Iterator<E> {
		private Node<E> current; // current refers next element to be returned
		private int version;
		private Node<E> last;
		
		SCIterator() {
			current = root;
			version = SimpleCollection2.this.version;
		}

		@Override
		public boolean hasNext() {
			if (version != SimpleCollection2.this.version) {
				throw new ConcurrentModificationException();
			}
			return current != null;
		}

		@Override
		public E next() {
			if (version != SimpleCollection2.this.version) {
				throw new ConcurrentModificationException();
			}

			if (current == null) { // end of collection reached
				throw new NoSuchElementException();
			}
			
			E result = current.val;
			last = current;
			current = current.next;
			return result;
		}

		@Override
		public void remove() {
			if (version != SimpleCollection2.this.version) {
				throw new ConcurrentModificationException();
			}
			if (last == null) {
				throw new IllegalStateException();
			}
			
			if(root == last) {
				root = root.next;
			} else {
				Node<E> p = root;
				while(p.next != last) p = p.next;
				p.next = p.next.next;
			}
			SimpleCollection2.this.version++;
			version++;
			SimpleCollection2.this.size--;
			last = null;
		}
	}

}
