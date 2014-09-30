package patterns.observer.copyright;

import java.util.ArrayList;
import java.util.List;

public class TextModel {
	private final StringBuilder text = new StringBuilder();
	private List<Listener> listeners = new ArrayList<Listener>();

	public void addListener(Listener l) {
		listeners.add(l);
	}

	public void insert(int pos, char ch) {
		if (pos < 0 || pos > text.length()) throw new IllegalArgumentException();
		text.insert(pos, ch);
		for (Listener l : listeners) {
			l.notifyInsert(pos, ch);
		}
	}

	public void delete(int from, int len) {
		if (from < 0 || len < 0 || from + len > text.length()) throw new IllegalArgumentException();
		text.delete(from, from + len);
		for (Listener l : listeners) {
			l.notifyDelete(from, len);
		}
	}

	public String getSubstring(int from, int len) {
		if (from < 0 || len < 0 || from + len > text.length()) throw new IllegalArgumentException();
		return text.substring(from, from + len);
	}

	@Override
	public String toString() {
		return text.toString();
	}
}
