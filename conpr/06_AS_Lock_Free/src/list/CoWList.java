package list;

import java.util.Iterator;

public interface CoWList<E> extends Iterable<E> {
	Iterator<E> iterator();
	void addFirst(E e);
	void removeFirst();
	int size();
}
