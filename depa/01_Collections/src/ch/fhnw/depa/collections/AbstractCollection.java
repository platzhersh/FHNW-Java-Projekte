package ch.fhnw.depa.collections;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;

/**
 * A convenience implementation of the java.util.Collection interface. It allows
 * to store <code>null<code> elements and supports modifiable
 * collections.
 * 
 * @param <E>
 *            the element type of this collection.
 */
public abstract class AbstractCollection<E> implements Collection<E> {
	// to implement
	// boolean add(Object x);
	// Iterator iterator();
	public boolean isEmpty() {
		return !iterator().hasNext();
	}

	@Override
	public int size() {
		int n = 0;
		for (@SuppressWarnings("unused")
		E e : this) {
			n++;
		}
		return n;
	}

	/**
	 * Will never throw a ClassCastException, instead will return false.
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public boolean contains(Object o) {
		for (E e : this) {
			if (e == o || (o != null && o.equals(e))) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Object[] toArray() {
		Object[] result = new Object[size()];
		int i = 0;
		for (E e : this) {
			result[i++] = e;
		}
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T[] toArray(T[] arg) {
		if (arg.length < size()) {
			arg = (T[]) Array.newInstance(arg.getClass().getComponentType(),
					size());
		}
		int i = 0;
		for (E e : this) {
			arg[i++] = (T) e;
		}
		if (arg.length > size()) {
			arg[i] = null;
		}
		return arg;
	}

	@Override
	public boolean remove(Object o) {
		Iterator<E> it = iterator();
		while (it.hasNext()) {
			Object x = it.next();
			if (x == o || (o != null && o.equals(x))) {
				it.remove();
				return true;
			}
		}
		return false;
	}

	@Override
	public void clear() {
		Iterator<E> it = iterator();
		while (it.hasNext()) {
			it.next();
			it.remove();
		}
	}

	/**
	 * Will never throw a ClassCastException, instead will return false.
	 * {@inheritDoc}
	 */
	@Override
	public boolean containsAll(Collection<?> c) {
		for (Object x : c) {
			if (!contains(x)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		boolean modified = false;
		for (E e : c) {
			if (add(e)) {
				modified = true;
			}
		}
		return modified;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean modified = false;
		Iterator<?> it = iterator();
		while (it.hasNext()) {
			if (c.contains(it.next())) {
				it.remove();
				modified = true;
			}
		}
		return modified;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		boolean modified = false;
		Iterator<?> it = iterator();
		while (it.hasNext()) {
			if (!c.contains(it.next())) {
				it.remove();
				modified = true;
			}
		}
		return modified;
	}
}