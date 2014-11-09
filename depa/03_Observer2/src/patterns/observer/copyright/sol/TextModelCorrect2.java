package patterns.observer.copyright.sol;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TextModelCorrect2 implements TextModel {
	
	private static abstract class TextCommand {
		abstract void execute();
	}

	private List<TextCommand> queue = new LinkedList<TextCommand>();

	private int notifyLevel = 0;

	private void handle(TextCommand c) {		
		notifyLevel++;
		if(notifyLevel > 1){
			queue.add(c);
		}
		else {
			c.execute();
			while(!queue.isEmpty()){
				queue.remove(0).execute();
			}
		}
		notifyLevel--;
	}

	
	private final StringBuilder text = new StringBuilder();
	private List<Listener> listeners = new ArrayList<Listener>();

	public void addListener(Listener l) {
		listeners.add(l);
	}

	public void insert(final int pos, final char ch) {
		handle(new TextCommand() {
			@Override
			void execute() {
				if (pos < 0 || pos > text.length())
					throw new IllegalArgumentException();
				text.insert(pos, ch);
				for (Listener l : listeners) {
					l.notifyInsert(pos, ch);
				}
			}
		});
	}

	public void delete(final int from, final int len) {
		handle(new TextCommand() {
			@Override
			void execute() {
				if (from < 0 || len < 0 || from + len > text.length())
					throw new IllegalArgumentException();
				text.delete(from, from + len);
				for (Listener l : listeners) {
					l.notifyDelete(from, len);
				}
			}
		});
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
