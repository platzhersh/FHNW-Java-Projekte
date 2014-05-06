package ch.fhnw.swent;

/*
 * Created on Mar 17, 2009
 */
/**
 * @author Wolfgang Weck
 */
public final class ObserverTest {
	public static void main(String[] args) {
		Object[] s = new Object[3];
		for (int i = 0; i < s.length; i++) {
			s[i] = new Object();
			Observers.register(s[i]);
		}
		for (int i = 0; i < 8; i++) {
			Observers.subscribe(newObserver(i), s[i % s.length]);
		}
		for (int i = 0; i < s.length; i++) {
			Observers.sendUpdates(s[i], new Message(i));
		}
	}

	private static Observers.Observer newObserver(final int i) {
		return new Observers.Observer() {
			public void update(Observers.UpdateMessage msg) {
				System.out.println("Observer " + i + " called on Subject "
						+ ((Message)msg).i);
				if (i == 1) {
					throw new RuntimeException("Bäh!");
				}
			}
		};
	}

	private static class Message implements Observers.UpdateMessage {
		private final int i;

		private Message(int i) {
			this.i = i;
		}
	}
}
