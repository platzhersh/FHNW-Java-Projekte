import java.util.Iterator;

public interface CoWList<E> extends Iterator<E> {
	Iterator<E> iterator();
	void addFirst(E e);
	void removeFirst();
	int size();
}