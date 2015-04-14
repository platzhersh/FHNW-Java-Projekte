package rwlocks;

import java.util.Set;

public interface KeyValueStore {
	void put(String key, Object value);
	void clearAll();
	Set<String> getAllKeys();
	Object get(String key);
}


