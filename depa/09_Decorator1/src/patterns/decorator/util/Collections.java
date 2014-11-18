package patterns.decorator.util;

import java.util.Collection;
import java.util.Iterator;

public class Collections {
	
	public static <T> Collection<T> unmodifiableCollection(Collection<T> c) {
		return new UnmodifiableCollectionDecorator(c);
	}
	
	static class UnmodifiableCollectionDecorator<E> implements Collection<E> {
		private Collection<E> inner;

		public UnmodifiableCollectionDecorator(Collection <E> i) {
			super();
			inner = i;
		}
		
		@Override
		public int size() {
			return inner.size();
		}

		@Override
		public boolean isEmpty() {
			return inner.isEmpty();
		}

		@Override
		public boolean contains(Object o) {
			return inner.contains(o);
		}

		@Override
		public Iterator<E> iterator() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Object[] toArray() {
			return inner.toArray();
		}

		@Override
		public <T> T[] toArray(T[] a) {
			return inner.toArray(a);
		}

		@Override
		public boolean add(E e) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean remove(Object o) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean containsAll(Collection<?> c) {
			return inner.containsAll(c);
		}

		@Override
		public boolean addAll(Collection<? extends E> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean removeAll(Collection<?> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean retainAll(Collection<?> c) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void clear() {
			throw new UnsupportedOperationException();
		}		
	}

}
