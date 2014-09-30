package patterns.observer.copyright;

public interface Listener {
	void notifyInsert(int pos, char ch);
	void notifyDelete(int from, int len);
}
