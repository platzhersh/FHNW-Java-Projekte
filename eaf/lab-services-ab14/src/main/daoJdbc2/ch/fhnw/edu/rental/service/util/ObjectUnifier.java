package ch.fhnw.edu.rental.service.util;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class ObjectUnifier<T> {
	private Map<Long, WeakReference<T>> cache = new HashMap<>();

	public T getObject(Long id) {
		if (cache.get(id) != null) {
			return cache.get(id).get();
		} else {
			return null;
		}
	}

	public void putObject(Long id, T obj) {
		cache.put(id, new WeakReference<T>(obj));
	}

	public void remove(Long id) {
		cache.remove(id);
	}

	public void clear() {
		cache.clear();
	}
}
