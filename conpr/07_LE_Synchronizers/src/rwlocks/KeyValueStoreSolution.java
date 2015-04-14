package rwlocks;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class KeyValueStoreSolution implements KeyValueStore {
	private final ReadWriteLock rw = new ReentrantReadWriteLock();
	private final Lock r = rw.readLock();
	private final Lock w = rw.writeLock();

	private final Map<String, Object> store = new HashMap<String, Object>();

	public void put(String key, Object value) {
		w.lock();
		try {
			store.put(key, value);
		} finally {
			w.unlock();
		}
	}

	public void clearAll() {
		w.lock();
		try {
			store.clear();
		} finally {
			w.unlock();
		}
	}

	public Set<String> getAllKeys() {
		r.lock();
		try {
			return new HashSet<String>(store.keySet());
		} finally {
			r.unlock();
		}
	}

	public Object get(String key) {
		r.lock();
		try {
			return store.get(key);
		} finally {
			r.unlock();
		}
	}
}