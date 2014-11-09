package patterns.observer.copyright.sol;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class TextModelCorrect1 implements TextModel {
	
	private static abstract class TextCommand {
		abstract void execute();
	}
	
	private Deque<TextCommand> queue = new LinkedList<TextCommand>();

	private int notifyLevel = 0;

	private void processQueue() {
		if (notifyLevel == 0) {
			TextCommand cmd = queue.peekLast();
			if (cmd != null) {
				queue.pollLast();
				cmd.execute();
			}
		}
	}

	private final StringBuilder text = new StringBuilder();
	private List<Listener> listeners = new ArrayList<Listener>();

	public void addListener(Listener l) {
		listeners.add(l);
	}

	public void insert(final int pos, final char ch) {
		queue.addFirst(new TextCommand() {
			@Override
			void execute() {
				if (pos < 0 || pos > text.length())
					throw new IllegalArgumentException();
				text.insert(pos, ch);
				notifyLevel++;
				for (Listener l : listeners) {
					l.notifyInsert(pos, ch);
				}
				notifyLevel--;
				processQueue(); // invokes the next command in the queue
			}
		});
		processQueue();
	}

	public void delete(final int from, final int len) {
		queue.addFirst(new TextCommand() {
			@Override
			void execute() {
				if (from < 0 || len < 0 || from + len > text.length())
					throw new IllegalArgumentException();
				text.delete(from, from + len);
				notifyLevel++;
				for (Listener l : listeners) {
					l.notifyDelete(from, len);
				}
				notifyLevel--;
				processQueue();
			}
		});
		processQueue();
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
