package ch.fhnw.swent;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/*
 * Created on Mar 2, 2010
 */
/**
 * @author Wolfgang Weck
 */
public class Observers {
	interface UpdateMessage {}

	interface Observer {
		void update(UpdateMessage msg);
	}
	static Map<Object, List<Observer>> subjects = Collections
			.synchronizedMap(new HashMap<Object, List<Observer>>());

	static void register(Object subject) {
		if (!subjects.containsKey(subject)) {
			subjects.put(subject, Collections
					.synchronizedList(new LinkedList<Observer>()));
		}
	}

	static boolean subscribe(Observer obs, Object to) {
		List<Observer> l = subjects.get(to);
		if (l != null && !l.contains(obs)) {
			l.add(obs);
			return true;
		} else {
			return false;
		}
	}

	static boolean sendUpdates(Object subject, UpdateMessage msg) {
		List<Observer> l = subjects.get(subject);
		if (l != null) {
			for (Observer o : l) {
				try {
					o.update(msg);
				} catch (Exception e) {
					System.out.println("Error updating "+o.toString()+", continue.");
					continue;
				}
			}
			return true;
		} else {
			return false;
		}
	}
}
