import java.util.Collection;
import java.util.LinkedList;



public class SimpleFilter {

	/**
	 * An interface to work around the lack of closures.
	 */
	static interface Predicate<T> {
		boolean apply(T t);
	}
	/** provide filtering capabilities based on a predicate */
	static <T> Collection<T> filter(Collection<T> src, Predicate<T> p) {
		return filter(src,new LinkedList<T>(),p);
	}
	/** provide filtering capabilities based on a predicate */
	static <T> Collection<T> filter(Collection<T> src, Collection<T> target, Predicate<T> p) {
		for (T t : src) {
			if (p.apply(t)) target.add(t);
		}
		return target;
	}
	
}
